/*
 * TemporalAccessorEnumValidator.java
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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate only an enumerated part like the month.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 * @param <E> The enumerated part type.
 */
public abstract class TemporalAccessorEnumValidator<A extends Annotation, T extends TemporalAccessor, E extends Enum<E>>
        extends DateTimeValidator<A, T> {

    private final Function<A, Set<E>> allowedValuesExtractor;
    private final Function<A, String> zoneIdExtractor;
    private final BiFunction<T, ZoneId, E> partExtractor;

    private Set<E> allowedValues;
    private ZoneId zoneId;

    private String defaultMessage;
    private Function<A, String> messageExtractor;
    private String message;
    private String replacementMessage;

    /**
     * Creates a new validator that validates enumerated {@link TemporalAccessor} parts against a set of allowed values.
     * <p>
     * This constructor is a specialization of {@link #TemporalAccessorEnumValidator(Function, Function, BiFunction)} that uses a part extractor that
     * ignores the zone id. This can be used for types that don't use zones like {@link LocalDate}.
     *
     * @param allowedValuesExtractor A function that extracts the allowed values from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts the enumerated part from a {@link TemporalAccessor}.
     */
    protected TemporalAccessorEnumValidator(Function<A, Set<E>> allowedValuesExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, E> partExtractor) {

        this(allowedValuesExtractor,
                zoneIdExtractor,
                (t, z) -> partExtractor.apply(t));
    }

    /**
     * Creates a new validator that validates enumerated {@link TemporalAccessor} parts against a set of allowed values.
     * <p>
     * This constructor is a specialization of {@link #TemporalAccessorEnumValidator(Function, Function, BiFunction)} that uses a part extractor that
     * does the following:
     * <pre><code>
     * return zoneId == null
     *         ? partExtractor.apply(value)
     *         : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(value, zoneId))
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ZonedDateTimeEnumValidator} for {@link ZonedDateTime},
     *
     * @param allowedValuesExtractor A function that extracts the allowed values from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts the enumerated part from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a zoned date/time.
     *                          The result should represent the same instant.
     * @param zonedDateTimePartExtractor A function that extracts the enumerated part from a zoned date/time.
     */
    protected TemporalAccessorEnumValidator(Function<A, Set<E>> allowedValuesExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, E> partExtractor,
            BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier,
            Function<ZonedDateTime, E> zonedDateTimePartExtractor) {

        this(allowedValuesExtractor,
                zoneIdExtractor,
                (t, z) -> z == null ? partExtractor.apply(t) : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(t, z)));
    }

    /**
     * Creates a new validator that validates dates against a set of allowed values.
     *
     * @param allowedValuesExtractor A function that extracts the allowed values from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts the enumerated part from a {@link TemporalAccessor}.
     */
    protected TemporalAccessorEnumValidator(Function<A, Set<E>> allowedValuesExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, E> partExtractor) {

        this.allowedValuesExtractor = allowedValuesExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
        this.partExtractor = partExtractor;
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
        initializeAllowedValues(constraintAnnotation);
        initializeZoneId(constraintAnnotation);
        initializeMessage(constraintAnnotation);
    }

    private void initializeAllowedValues(A constraintAnnotation) {
        allowedValues = allowedValuesExtractor.apply(constraintAnnotation);
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

        E part = partExtractor.apply(value, zoneId);
        boolean valid = allowedValues.contains(part);
        if (!valid && message != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        return valid;
    }
}
