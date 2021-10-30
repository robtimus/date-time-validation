/*
 * AbstractTemporalAccessorMonthValidator.java
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

package com.github.robtimus.validation.time.validators;

import java.lang.annotation.Annotation;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate only the month.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 */
public abstract class AbstractTemporalAccessorMonthValidator<A extends Annotation, T extends TemporalAccessor> extends DateTimeValidator<A, T> {

    private final Function<A, Set<Month>> allowedMonthsExtractor;
    private final Function<A, String> zoneIdExtractor;
    private final BiFunction<T, ZoneId, Month> monthExtractor;

    private Set<Month> allowedMonths;
    private ZoneId zoneId;

    private String defaultMessage;
    private Function<A, String> messageExtractor;
    private String message;
    private String replacementMessage;

    /**
     * Creates a new validator that validates dates against a set of allowed months.
     *
     * @param allowedMonthsExtractor A function that extracts the allowed months from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param monthExtractor A function that extracts the month from a {@link TemporalAccessor}..
     */
    protected AbstractTemporalAccessorMonthValidator(Function<A, Set<Month>> allowedMonthsExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, Month> monthExtractor) {

        this.allowedMonthsExtractor = allowedMonthsExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
        this.monthExtractor = (t, z) -> monthExtractor.apply(t);
    }

    /**
     * Creates a new validator that validates dates against a set of allowed months.
     *
     * @param allowedMonthsExtractor A function that extracts the allowed months from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param monthExtractor A function that extracts the month from a {@link TemporalAccessor}..
     */
    protected AbstractTemporalAccessorMonthValidator(Function<A, Set<Month>> allowedMonthsExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, Month> monthExtractor) {

        this.allowedMonthsExtractor = allowedMonthsExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
        this.monthExtractor = monthExtractor;
    }

    /**
     * Specifies that a replacement message should be used, but only if the default message has not been changed.
     *
     * @param defaultMessage The default message.
     * @param messageExtractor A function that extracts the message from a constraint annotation.
     * @param replacementMessage The replacement message.
     */
    protected final void useReplacementMessage(String defaultMessage, Function<A, String> messageExtractor, String replacementMessage) {
        this.defaultMessage = defaultMessage;
        this.messageExtractor = messageExtractor;
        this.replacementMessage = replacementMessage;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        initializeAllowedMonths(constraintAnnotation);
        initializeZoneId(constraintAnnotation);
        initializeMessage(constraintAnnotation);
    }

    private void initializeAllowedMonths(A constraintAnnotation) {
        allowedMonths = allowedMonthsExtractor.apply(constraintAnnotation);
    }

    private void initializeZoneId(A constraintAnnotation) {
        String zoneIdText = zoneIdExtractor.apply(constraintAnnotation);
        zoneId = toZoneId(zoneIdText);
    }

    private void initializeMessage(A constraintAnnotation) {
        if (messageExtractor != null) {
            String configuredMessage = messageExtractor.apply(constraintAnnotation);
            if (configuredMessage.equals(defaultMessage)) {
                message = replacementMessage;
            }
        }
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Month month = monthExtractor.apply(value, zoneId);
        boolean valid = allowedMonths.contains(month);
        if (!valid && message != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        return valid;
    }
}
