/*
 * FieldValidator.java
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

package com.github.robtimus.validation.datetime.base;

import static com.github.robtimus.validation.datetime.base.ZoneIdUtils.extractZoneId;
import static com.github.robtimus.validation.datetime.base.ZoneIdUtils.nonProvidedZoneId;
import static com.github.robtimus.validation.datetime.base.ZoneIdUtils.systemOnlyZoneId;
import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import jakarta.validation.ClockProvider;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate only a specific field of the value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 */
public abstract class FieldValidator<A extends Annotation, T extends TemporalAccessor> extends BaseValidator<A, T> {

    /**
     * Creates a new validator.
     * <p>
     * If a {@link ZoneId} is available, the field will be extracted from the result of applying the given {@code zoneIdApplier} function to the value
     * to validate. Otherwise it will be extracted from the value itself.
     *
     * @param fieldExtractor A function that extracts the field to validate from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a {@link TemporalAccessor} with the zone id
     *                          applied. The result is usually a {@link ZonedDateTime} but doesn't have to be.
     *                          The result should represent the same instant.
     * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
     *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
     *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned by
     *                                    {@link ConstraintValidatorContext#getClockProvider()}.
     */
    protected FieldValidator(Function<A, TemporalField> fieldExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, TemporalAccessor> zoneIdApplier,
            Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

        super(fieldPredicate(fieldExtractor, zoneIdExtractor, zoneIdApplier, fieldPredicateExtractor));
    }

    /**
     * Creates a new validator.
     * <p>
     * If a {@link ZoneId} is available, the field will be extracted from the result of applying the given {@code zoneIdApplier} function to the value
     * to validate. Otherwise it will be extracted from the value itself.
     *
     * @param field The field to validate.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a {@link TemporalAccessor} with the zone id
     *                          applied. The result is usually a {@link ZonedDateTime} but doesn't have to be.
     *                          The result should represent the same instant.
     * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
     *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
     *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned by
     *                                    {@link ConstraintValidatorContext#getClockProvider()}.
     */
    protected FieldValidator(TemporalField field,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, TemporalAccessor> zoneIdApplier,
            Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

        this(fieldExtractor(field), zoneIdExtractor, zoneIdApplier, fieldPredicateExtractor);
    }

    private static <A> Function<A, TemporalField> fieldExtractor(TemporalField field) {
        Objects.requireNonNull(field);

        return annotation -> field;
    }

    private static <A, T extends TemporalAccessor> Function<A, BiPredicate<T, ClockProvider>> fieldPredicate(
            Function<A, TemporalField> fieldExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, TemporalAccessor> zoneIdApplier,
            Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

        Objects.requireNonNull(fieldExtractor);
        Objects.requireNonNull(zoneIdExtractor);
        Objects.requireNonNull(zoneIdApplier);
        Objects.requireNonNull(fieldPredicateExtractor);

        return annotation -> {
            TemporalField field = fieldExtractor.apply(annotation);
            ZoneId zoneId = extractZoneId(annotation, zoneIdExtractor);
            BiPredicate<Integer, ClockProvider> fieldPredicate = fieldPredicateExtractor.apply(annotation);

            return (value, clockProvider) -> {
                TemporalAccessor temporalAccessor = zoneId == null ? value : zoneIdApplier.apply(value, zoneId);
                int fieldValue = temporalAccessor.get(field);
                return fieldPredicate.test(fieldValue, clockProvider);
            };
        };
    }

    /**
     * The base for all {@link TemporalAccessor} validators that validate only a specific field of the value.
     * This sub type of {@link FieldValidator} can be used for types that don't use zones like {@link LocalDate}.
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     * @param <T> The {@link TemporalAccessor} type to validate.
     */
    public abstract static class WithoutZoneId<A extends Annotation, T extends TemporalAccessor> extends FieldValidator<A, T> {

        /**
         * Creates a new validator.
         *
         * @param fieldExtractor A function that extracts the field to validate from a constraint annotation.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#systemOnlyZoneId(Function)}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected WithoutZoneId(Function<A, TemporalField> fieldExtractor,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            super(fieldExtractor, systemOnlyZoneId(zoneIdExtractor), (t, z) -> t, fieldPredicateExtractor);
        }

        /**
         * Creates a new validator.
         *
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#systemOnlyZoneId(Function)}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected WithoutZoneId(TemporalField field,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            this(fieldExtractor(field), zoneIdExtractor, fieldPredicateExtractor);
        }
    }

    /**
     * The base for all {@link Instant} validators that validate only a specific field of the value.
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     */
    public abstract static class ForInstant<A extends Annotation> extends FieldValidator<A, Instant> {

        /**
         * Creates a new validator.
         *
         * @param fieldExtractor A function that extracts the field to validate from a constraint annotation.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#nonProvidedZoneId(Function)}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForInstant(Function<A, TemporalField> fieldExtractor,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            super(fieldExtractor, nonProvidedZoneId(zoneIdExtractor), Instant::atZone, fieldPredicateExtractor);
        }

        /**
         * Creates a new validator.
         *
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#nonProvidedZoneId(Function)}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForInstant(TemporalField field,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            this(fieldExtractor(field), zoneIdExtractor, fieldPredicateExtractor);
        }
    }

    /**
     * The base for all {@link OffsetTime} validators that validate only a specific field of the value.
     * <p>
     * To apply a {@link ZoneId} to an {@link OffsetTime}, the following mappings are performed:
     * <ul>
     * <li>{@link OffsetTime} to {@link OffsetDateTime}, using {@link LocalDate#now()}.</li>
     * <li>{@link OffsetDateTime} to {@link ZonedDateTime}, using the provided {@link ZoneId}.</li>
     * <li>{@link ZonedDateTime} to {@link OffsetDateTime}.</li>
     * <li>{@link OffsetDateTime} to {@link OffsetTime}.</li>
     * </ul>
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     */
    public abstract static class ForOffsetTime<A extends Annotation> extends FieldValidator<A, OffsetTime> {

        /**
         * Creates a new validator.
         *
         * @param fieldExtractor A function that extracts the field to validate from a constraint annotation.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#nonProvidedZoneId(Function)}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForOffsetTime(Function<A, TemporalField> fieldExtractor,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            super(fieldExtractor, zoneIdExtractor, ForOffsetTime::applyZoneId, fieldPredicateExtractor);
        }

        /**
         * Creates a new validator.
         *
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#nonProvidedZoneId(Function)}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForOffsetTime(TemporalField field,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            this(fieldExtractor(field), zoneIdExtractor, fieldPredicateExtractor);
        }

        private static OffsetTime applyZoneId(OffsetTime offsetTime, ZoneId zoneId) {
            return offsetTime.atDate(LocalDate.now())
                    .atZoneSameInstant(zoneId)
                    .toOffsetDateTime()
                    .toOffsetTime();
        }
    }

    /**
     * The base for all {@link ZonedDateTime} validators that validate only a specific field of the value.
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     */
    public abstract static class ForZonedDateTime<A extends Annotation> extends FieldValidator<A, ZonedDateTime> {

        /**
         * Creates a new validator.
         *
         * @param fieldExtractor A function that extracts the field to validate from a constraint annotation.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForZonedDateTime(Function<A, TemporalField> fieldExtractor,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            super(fieldExtractor, zoneIdExtractor, ZonedDateTime::withZoneSameInstant, fieldPredicateExtractor);
        }

        /**
         * Creates a new validator.
         *
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with as arguments the field extracted from the value to validate and the {@link ClockProvider} returned
         *                                    by {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForZonedDateTime(TemporalField field,
                Function<A, String> zoneIdExtractor,
                Function<A, BiPredicate<Integer, ClockProvider>> fieldPredicateExtractor) {

            this(fieldExtractor(field), zoneIdExtractor, fieldPredicateExtractor);
        }
    }
}
