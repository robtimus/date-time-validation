/*
 * PartValidator.java
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate only a specific part of the value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
 */
public abstract class PartValidator<A extends Annotation, T extends TemporalAccessor, P extends TemporalAccessor> extends BaseValidator<A, T> {

    /**
     * Creates a new validator.
     *
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partPredicateExtractor A function that extracts a part predicate from a constraint annotation.
     *                                   This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
     *                                   with as arguments the part extracted from the value to validate and the {@link ClockProvider} returned by
     *                                   {@link ConstraintValidatorContext#getClockProvider()}.
     */
    protected PartValidator(Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            Function<A, BiPredicate<P, ClockProvider>> partPredicateExtractor) {

        super(partPredicate(zoneIdExtractor, partExtractor, partPredicateExtractor));
    }

    /**
     * Creates a new validator.
     * <p>
     * This constructor is a specialization of {@link #PartValidator(Function, BiFunction, Function)} that uses a part extractor that does the
     * following:
     * <pre><code>
     * return zoneId == null
     *         ? partExtractor.apply(value)
     *         : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(value, zoneId))
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ForZonedDateTime} for {@link ZonedDateTime},
     *
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part directly from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a {@link ZonedDateTime}.
     *                          The result should represent the same instant.
     * @param zonedDateTimePartExtractor A function that extracts the part from a {@link ZonedDateTime}.
     * @param partPredicateExtractor A function that extracts a part predicate from a constraint annotation.
     *                                   This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
     *                                   with as arguments the part extracted from the value to validate and the {@link ClockProvider} returned by
     *                                   {@link ConstraintValidatorContext#getClockProvider()}.
     */
    protected PartValidator(Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor, BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier, Function<ZonedDateTime, P> zonedDateTimePartExtractor,
            Function<A, BiPredicate<P, ClockProvider>> partPredicateExtractor) {

        this(zoneIdExtractor, partExtractor(partExtractor, zoneIdApplier, zonedDateTimePartExtractor), partPredicateExtractor);
    }

    private static <A, T, P> Function<A, BiPredicate<T, ClockProvider>> partPredicate(
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor, Function<A, BiPredicate<P, ClockProvider>> partPredicateExtractor) {

        Objects.requireNonNull(zoneIdExtractor);
        Objects.requireNonNull(partExtractor);
        Objects.requireNonNull(partExtractor);

        return annotation -> {
            ZoneId zoneId = extractZoneId(annotation, zoneIdExtractor);
            BiPredicate<P, ClockProvider> partPredicate = partPredicateExtractor.apply(annotation);

            return (value, clockProvider) -> {
                P part = partExtractor.apply(value, zoneId);
                return partPredicate.test(part, clockProvider);
            };
        };
    }

    static <T, P> BiFunction<T, ZoneId, P> partExtractor(Function<T, P> partExtractor,
            BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier, Function<ZonedDateTime, P> zonedDateTimePartExtractor) {

        Objects.requireNonNull(partExtractor);
        Objects.requireNonNull(zoneIdApplier);
        Objects.requireNonNull(zonedDateTimePartExtractor);

        return (t, z) -> z == null ? partExtractor.apply(t) : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(t, z));
    }

    /**
     * The base for all {@link TemporalAccessor} validators that validate only a specific part of the value.
     * This sub type of {@link PartValidator} can be used for types that don't use zones like {@link LocalDate}.
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     * @param <T> The {@link TemporalAccessor} type to validate.
     * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
     */
    public abstract static class WithoutZoneId<A extends Annotation, T extends TemporalAccessor, P extends TemporalAccessor>
            extends PartValidator<A, T, P> {

        /**
         * Creates a new validator.
         *
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#systemOnlyZoneId(Function)}.
         * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
         * @param partPredicateExtractor A function that extracts a part predicate from a constraint annotation.
         *                                   This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                   with as arguments the part extracted from the value to validate and the {@link ClockProvider} returned by
         *                                   {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected WithoutZoneId(Function<A, String> zoneIdExtractor,
                Function<T, P> partExtractor,
                Function<A, BiPredicate<P, ClockProvider>> partPredicateExtractor) {

            super(systemOnlyZoneId(zoneIdExtractor), partExtractor(partExtractor), partPredicateExtractor);
        }

        private static <T, P> BiFunction<T, ZoneId, P> partExtractor(Function<T, P> partExtractor) {
            Objects.requireNonNull(partExtractor);

            return (t, z) -> partExtractor.apply(t);
        }
    }

    /**
     * The base for all {@link Instant} validators that validate only a specific part of the value.
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
     */
    public abstract static class ForInstant<A extends Annotation, P extends TemporalAccessor> extends PartValidator<A, Instant, P> {

        /**
         * Creates a new validator.
         *
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         *                            It will be wrapped using {@link ZoneIdUtils#nonProvidedZoneId(Function)}.
         * @param partExtractor A function that extracts a part from a {@link ZonedDateTime}.
         * @param partPredicateExtractor A function that extracts a part predicate from a constraint annotation.
         *                                   This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                   with as arguments the part extracted from the value to validate and the {@link ClockProvider} returned by
         *                                   {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForInstant(Function<A, String> zoneIdExtractor,
                Function<ZonedDateTime, P> partExtractor,
                Function<A, BiPredicate<P, ClockProvider>> partPredicateExtractor) {

            super(nonProvidedZoneId(zoneIdExtractor), partExtractor(partExtractor), partPredicateExtractor);
        }

        private static <P> BiFunction<Instant, ZoneId, P> partExtractor(Function<ZonedDateTime, P> partExtractor) {
            Objects.requireNonNull(partExtractor);

            return (t, z) -> partExtractor.apply(t.atZone(z));
        }
    }

    /**
     * The base for all {@link ZonedDateTime} validators that validate only a specific part of the value.
     *
     * @author Rob Spoor
     * @param <A> The constraint annotation type.
     * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
     */
    public abstract static class ForZonedDateTime<A extends Annotation, P extends TemporalAccessor> extends PartValidator<A, ZonedDateTime, P> {

        /**
         * Creates a new validator.
         *
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
         * @param partPredicateExtractor A function that extracts a part predicate from a constraint annotation.
         *                                   This predicate will be called in {@link #isValid(Object, ConstraintValidatorContext)},
         *                                   with as arguments the part extracted from the value to validate and the {@link ClockProvider} returned by
         *                                   {@link ConstraintValidatorContext#getClockProvider()}.
         */
        protected ForZonedDateTime(Function<A, String> zoneIdExtractor,
                Function<ZonedDateTime, P> partExtractor,
                Function<A, BiPredicate<P, ClockProvider>> partPredicateExtractor) {

            super(zoneIdExtractor, partExtractor, ZonedDateTime::withZoneSameInstant, partExtractor, partPredicateExtractor);
        }
    }
}
