/*
 * TemporalAccessorPartValidator.java
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

package com.github.robtimus.validation.datetime.validators;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate only part of the value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
 */
public abstract class TemporalAccessorPartValidator<A extends Annotation, T extends TemporalAccessor, P extends TemporalAccessor>
        extends DateTimeValidator<A, T> {

    private final Function<A, String> momentExtractor;
    private final Function<A, String> durationExtractor;
    private final Function<A, String> zoneIdExtractor;
    private final BiFunction<T, ZoneId, P> partExtractor;
    private final TemporalAccessorValidator<?, P> partValidator;

    private ZoneId zoneId;

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     * <p>
     * This constructor is a specialization of {@link #TemporalAccessorPartValidator(Function, Function, BiFunction, TemporalAccessorValidator)} that
     * uses a part extractor that ignores the zone id. This can be used for types that don't use zones like {@link LocalDate}.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected TemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        this(momentExtractor,
                zoneIdExtractor,
                (t, z) -> partExtractor.apply(t),
                partValidator);
    }

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     * <p>
     * This constructor is a specialization of {@link #TemporalAccessorPartValidator(Function, Function, BiFunction, TemporalAccessorValidator)} that
     * uses a part extractor that does the following:
     * <pre><code>
     * return zoneId == null
     *         ? partExtractor.apply(value)
     *         : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(value, zoneId))
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ZonedDateTimePartValidator} for {@link ZonedDateTime},
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part directly from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a zoned date/time.
     *                          The result should represent the same instant.
     * @param zonedDateTimePartExtractor A function that extracts the part from a zoned date/time.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected TemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor, BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier, Function<ZonedDateTime, P> zonedDateTimePartExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        this(momentExtractor,
                zoneIdExtractor,
                (t, z) -> z == null ? partExtractor.apply(t) : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(t, z)),
                partValidator);
    }

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected TemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        this.momentExtractor = momentExtractor;
        this.durationExtractor = null;
        this.zoneIdExtractor = zoneIdExtractor;
        this.partExtractor = partExtractor;
        this.partValidator = partValidator;
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     * <p>
     * This constructor is a specialization of
     * {@link #TemporalAccessorPartValidator(Function, Function, Function, BiFunction, TemporalAccessorValidator)} that uses a part extractor that
     * ignores the zone id. This can be used for types that don't use zones like {@link LocalDate}.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected TemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        this(momentExtractor,
                durationExtractor,
                zoneIdExtractor,
                (t, z) -> partExtractor.apply(t),
                partValidator);
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     * <p>
     * This constructor is a specialization of
     * {@link #TemporalAccessorPartValidator(Function, Function, Function, BiFunction, TemporalAccessorValidator)} that uses a part extractor that
     *  does the following:
     * <pre><code>
     * return zoneId == null
     *         ? partExtractor.apply(value)
     *         : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(value, zoneId))
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ZonedDateTimePartValidator} for {@link ZonedDateTime},
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part directly from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a zoned date/time.
     *                          The result should represent the same instant.
     * @param zonedDateTimePartExtractor A function that extracts the part from a zoned date/time.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected TemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor,
            Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor, BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier, Function<ZonedDateTime, P> zonedDateTimePartExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        this(momentExtractor,
                durationExtractor,
                zoneIdExtractor,
                (t, z) -> z == null ? partExtractor.apply(t) : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(t, z)),
                partValidator);
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected TemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        this.momentExtractor = momentExtractor;
        this.durationExtractor = durationExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
        this.partExtractor = partExtractor;
        this.partValidator = partValidator;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        String momentText = momentExtractor.apply(constraintAnnotation);
        String durationText = durationExtractor != null ? durationExtractor.apply(constraintAnnotation) : null;

        partValidator.initialize(momentText, durationText);

        initializeZoneId(constraintAnnotation);
    }

    private void initializeZoneId(A constraintAnnotation) {
        String zoneIdText = zoneIdExtractor.apply(constraintAnnotation);
        zoneId = toZoneId(zoneIdText);
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        P part = partExtractor.apply(value, zoneId);
        return partValidator.isValid(part, context);
    }
}
