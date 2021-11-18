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

package com.github.robtimus.validation.datetime.core;

import static com.github.robtimus.validation.datetime.core.ZoneIdUtils.extractZoneId;
import static com.github.robtimus.validation.datetime.core.ZoneIdUtils.nonProvidedZoneId;
import static com.github.robtimus.validation.datetime.core.ZoneIdUtils.systemOnlyZoneId;
import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

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
     *
     * @param field The field to validate.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param fieldExtractor A function that extracts a field from a {@link TemporalAccessor}.
     * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
     *                                    This predicate will be used in {@link #isValid(Object, ConstraintValidatorContext)},
     *                                    with the extracted field as argument instead of the {@link TemporalAccessor}.
     */
    protected FieldValidator(ChronoField field,
            Function<A, String> zoneIdExtractor,
            FieldExtractor<T> fieldExtractor,
            Function<A, FieldPredicate> fieldPredicateExtractor) {

        super(fieldPredicate(field, zoneIdExtractor, fieldExtractor, fieldPredicateExtractor));
    }

    /**
     * Creates a new validator.
     * <p>
     * This constructor is a specialization of {@link #FieldValidator(ChronoField, Function, FieldExtractor, Function)} that uses a field extractor
     * that does the following:
     * <pre><code>
     * return zoneId == null
     *         ? fieldExtractor.extract(value, field)
     *         : zonedDateTimeFieldExtractor.extract(zoneIdApplier.apply(value, zoneId), field)
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ForZonedDateTime} for {@link ZonedDateTime},
     *
     * @param field The field to validate.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param fieldExtractor A function that extracts a field directly from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a {@link ZonedDateTime}.
     *                          The result should represent the same instant.
     * @param zonedDateTimeFieldExtractor A function that extracts the field from a {@link ZonedDateTime}.
     * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
     *                                    This predicate will be used in {@link #isValid(Object, ConstraintValidatorContext)},
     *                                    with the extracted field as argument instead of the {@link TemporalAccessor}.
     */
    protected FieldValidator(ChronoField field,
            Function<A, String> zoneIdExtractor,
            NoZoneIdFieldExtractor<T> fieldExtractor, BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier,
            NoZoneIdFieldExtractor<ZonedDateTime> zonedDateTimeFieldExtractor,
            Function<A, FieldPredicate> fieldPredicateExtractor) {

        super(fieldPredicate(field, zoneIdExtractor, fieldExtractor(fieldExtractor, zoneIdApplier, zonedDateTimeFieldExtractor),
                fieldPredicateExtractor));
    }

    private static <A, T extends TemporalAccessor> Function<A, BiPredicate<T, ConstraintValidatorContext>> fieldPredicate(
            ChronoField field,
            Function<A, String> zoneIdExtractor,
            FieldExtractor<T> fieldExtractor,
            Function<A, FieldPredicate> fieldPredicateExtractor) {

        Objects.requireNonNull(zoneIdExtractor);
        Objects.requireNonNull(field);
        Objects.requireNonNull(fieldExtractor);
        Objects.requireNonNull(fieldPredicateExtractor);

        return annotation -> {
            ZoneId zoneId = extractZoneId(annotation, zoneIdExtractor);
            FieldPredicate fieldPredicate = fieldPredicateExtractor.apply(annotation);

            return (value, context) -> {
                int fieldValue = fieldExtractor.extract(value, zoneId, field);
                return fieldPredicate.test(fieldValue, context);
            };
        };
    }

    private static <T extends TemporalAccessor> FieldExtractor<T> fieldExtractor(NoZoneIdFieldExtractor<T> fieldExtractor,
            BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier,
            NoZoneIdFieldExtractor<ZonedDateTime> zonedDateTimeFieldExtractor) {

        Objects.requireNonNull(fieldExtractor);
        Objects.requireNonNull(zoneIdApplier);
        Objects.requireNonNull(zonedDateTimeFieldExtractor);

        return (t, z, f) -> z == null ? fieldExtractor.extract(t, f) : zonedDateTimeFieldExtractor.extract(zoneIdApplier.apply(t, z), f);
    }

    /**
     * Represents a function that takes a {@link TemporalAccessor}, a {@link ZoneId} and a {@link ChronoField}, and returns the value for the given
     * field for the given {@link TemporalAccessor} at the given {@link ZoneId}.
     *
     * @author Rob Spoor
     * @param <T> The temporal accessor type.
     */
    public interface FieldExtractor<T extends TemporalAccessor> {

        /**
         * Extracts the value for a field from a {@link TemporalAccessor} at a given {@link ZoneId}.
         *
         * @param value The {@link TemporalAccessor} to extract the field value from.
         * @param zoneId The {@link ZoneId} to use, or {@code null} to use the value's current {@link ZoneId} (if applicable).
         * @param field The field for which to return the value.
         * @return The value for the given field.
         */
        int extract(T value, ZoneId zoneId, ChronoField field);
    }

    /**
     * Represents a function that takes a {@link TemporalAccessor} and a {@link ChronoField}, and returns the value for the given field for the given
     * {@link TemporalAccessor}. This is a specialization of {@link FieldExtractor} for types that don't use zones like {@link LocalDate}.
     *
     * @author Rob Spoor
     * @param <T> The temporal accessor type.
     */
    public interface NoZoneIdFieldExtractor<T extends TemporalAccessor> {

        /**
         * Extracts the value for a field from a {@link TemporalAccessor}.
         *
         * @param value The {@link TemporalAccessor} to extract the field value from.
         * @param field The field for which to return the value.
         * @return The value for the given field.
         */
        int extract(T value, ChronoField field);
    }

    /**
     * Represents a predicate of a field value and a {@link ConstraintValidatorContext}.
     * This is a specialization of {@link BiPredicate}.
     *
     * @author Rob Spoor
     */
    public interface FieldPredicate {

        /**
         * Evaluates this predicate on the given arguments.
         *
         * @param field The field value.
         * @param context The {@link ConstraintValidatorContext}.
         * @return {@code true} if the field and context match the predicate, or {@code false} otherwise.
         */
        boolean test(int field, ConstraintValidatorContext context);
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
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#systemOnlyZoneId(Function)}.
         * @param fieldExtractor A function that extracts a field from a {@link TemporalAccessor}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be used in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with the extracted field as argument instead of the {@link TemporalAccessor}.
         */
        protected WithoutZoneId(ChronoField field,
                Function<A, String> zoneIdExtractor,
                NoZoneIdFieldExtractor<T> fieldExtractor,
                Function<A, FieldPredicate> fieldPredicateExtractor) {

            super(field, systemOnlyZoneId(zoneIdExtractor), fieldExtractor(fieldExtractor), fieldPredicateExtractor);
        }

        private static <T extends TemporalAccessor> FieldExtractor<T> fieldExtractor(NoZoneIdFieldExtractor<T> fieldExtractor) {
            Objects.requireNonNull(fieldExtractor);

            return (t, z, f) -> fieldExtractor.extract(t, f);
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
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#nonProvidedZoneId(Function)}.
         * @param fieldExtractor A function that extracts a field from a {@link ZonedDateTime}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be used in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with the extracted field as argument instead of the {@link Instant}.
         */
        protected ForInstant(ChronoField field,
                Function<A, String> zoneIdExtractor,
                NoZoneIdFieldExtractor<ZonedDateTime> fieldExtractor,
                Function<A, FieldPredicate> fieldPredicateExtractor) {

            super(field, nonProvidedZoneId(zoneIdExtractor), fieldExtractor(fieldExtractor), fieldPredicateExtractor);
        }

        private static FieldExtractor<Instant> fieldExtractor(NoZoneIdFieldExtractor<ZonedDateTime> fieldExtractor) {
            Objects.requireNonNull(fieldExtractor);

            return (t, z, f) -> fieldExtractor.extract(t.atZone(z), f);
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
         * @param field The field to validate.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param fieldExtractor A function that extracts a field from a {@link TemporalAccessor}.
         * @param fieldPredicateExtractor A function that extracts a field predicate from a constraint annotation.
         *                                    This predicate will be used in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                    with the extracted field as argument instead of the {@link TemporalAccessor}.
         */
        protected ForZonedDateTime(ChronoField field,
                Function<A, String> zoneIdExtractor,
                NoZoneIdFieldExtractor<ZonedDateTime> fieldExtractor,
                Function<A, FieldPredicate> fieldPredicateExtractor) {

            super(field, zoneIdExtractor, fieldExtractor, ZonedDateTime::withZoneSameInstant, fieldExtractor, fieldPredicateExtractor);
        }
    }
}
