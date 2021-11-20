/*
 * BaseValidator.java
 * Copyright 2021 Rob Spoor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.robtimus.validation.datetime.core;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all date/time object validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The type to validate.
 */
public abstract class BaseValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private final Function<A, BiPredicate<T, ClockProvider>> predicateExtractor;

    private Function<A, String> messageExtractor;
    private String defaultMessage;
    private String replacementMessageTemplate;

    private BiPredicate<T, ClockProvider> predicate;
    private String messageTemplate;

    /**
     * Creates a new validator.
     *
     * @param predicateExtractor A function that extracts a predicate from a constraint annotation.
     *                               This predicate will be called from {@link #isValid(Object, ConstraintValidatorContext)},
     *                               with as arguments the value to validate and the {@link ClockProvider} returned by
     *                               {@link ConstraintValidatorContext#getClockProvider()}.
     */
    protected BaseValidator(Function<A, BiPredicate<T, ClockProvider>> predicateExtractor) {
        this.predicateExtractor = Objects.requireNonNull(predicateExtractor);
    }

    /**
     * Specifies that a replacement message template should be used, but only if the default message has not been changed.
     *
     * @param messageExtractor A function that extracts the message from a constraint annotation.
     * @param defaultMessage The default message.
     * @param replacementMessageTemplate The replacement message.
     */
    protected final void useReplacementMessageTemplate(Function<A, String> messageExtractor,
            String defaultMessage,
            String replacementMessageTemplate) {

        this.defaultMessage = Objects.requireNonNull(defaultMessage);
        this.messageExtractor = Objects.requireNonNull(messageExtractor);
        this.replacementMessageTemplate = Objects.requireNonNull(replacementMessageTemplate);
    }

    @Override
    public void initialize(A constraintAnnotation) {
        initializePredicate(constraintAnnotation);
        initializeMessage(constraintAnnotation);
    }

    private void initializePredicate(A constraintAnnotation) {
        predicate = predicateExtractor.apply(constraintAnnotation);
    }

    private void initializeMessage(A constraintAnnotation) {
        if (messageExtractor != null) {
            String configuredMessage = messageExtractor.apply(constraintAnnotation);
            if (configuredMessage.equals(defaultMessage)) {
                messageTemplate = replacementMessageTemplate;
            }
        }
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean valid = predicate.test(value, context.getClockProvider());
        if (!valid && messageTemplate != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
        }
        return valid;
    }

    /**
     * Negates a predicate. This is a utility method that simply returns {@code predicate.negate()}, and is equivalent to the static method that was
     * added to {@link Predicate} in Java 11.
     * <p>
     * This method can be used with method references. Example: {@code not(LocalDate::isAfter)}.
     *
     * @param <T> The type to test.
     * @param predicate The predicate to negate.
     * @return The negated predicate.
     * @throws NullPointerException If the given predicate is {@code null}.
     */
    protected static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }

    /**
     * Adds a {@link TemporalAmount} to an {@link Instant}.
     * <p>
     * Unlike {@link Instant#plus(TemporalAmount)}, this method supports not only {@link Duration} but also other {@link TemporalAmount}s like
     * {@link Period}. If the {@link TemporalAmount} is not a {@link Duration}, this method first converts the {@link Instant} to a
     * {@link ZonedDateTime} using {@link ZoneId#systemDefault()}, then adds the {@link TemporalAmount}, then converts the {@link ZonedDateTime} back
     * to an {@link Instant}.
     *
     * @param instant The {@link Instant} to add a {@link TemporalAmount} to.
     * @param amount The {@link TemporalAmount} to add.
     * @return The result of adding the {@link TemporalAmount} to the {@link Instant}.
     */
    protected static Instant plus(Instant instant, TemporalAmount amount) {
        if (amount instanceof Duration) {
            return instant.plus(amount);
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        zonedDateTime = zonedDateTime.plus(amount);
        return zonedDateTime.toInstant();
    }

    /**
     * Subtracts a {@link TemporalAmount} from an {@link Instant}.
     * <p>
     * Unlike {@link Instant#minus(TemporalAmount)}, this method supports not only {@link Duration} but also other {@link TemporalAmount}s like
     * {@link Period}. If the {@link TemporalAmount} is not a {@link Duration}, this method first converts the {@link Instant} to a
     * {@link ZonedDateTime} using {@link ZoneId#systemDefault()}, then subtracts the {@link TemporalAmount}, then converts the {@link ZonedDateTime}
     * back to an {@link Instant}.
     *
     * @param instant The {@link Instant} to subtract a {@link TemporalAmount} from.
     * @param amount The {@link TemporalAmount} to subtract.
     * @return The result of subtracting the {@link TemporalAmount} from the {@link Instant}.
     */
    protected static Instant minus(Instant instant, TemporalAmount amount) {
        if (amount instanceof Duration) {
            return instant.minus(amount);
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        zonedDateTime = zonedDateTime.minus(amount);
        return zonedDateTime.toInstant();
    }
}
