/*
 * MomentPartValidator.java
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

import static com.github.robtimus.validation.datetime.base.MomentValueValidator.momentPredicate;
import static com.github.robtimus.validation.datetime.base.ZoneIdUtils.nonProvidedZoneId;
import static com.github.robtimus.validation.datetime.base.ZoneIdUtils.systemOnlyZoneId;
import java.lang.annotation.Annotation;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * The base for all {@link TemporalAccessor} validators that validate only a specific part of the value against a specific moment in time.
 * This class combined the functionality of both {@link PartValidator} and {@link MomentValueValidator}.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
 */
public abstract class MomentPartValidator<A extends Annotation, T extends TemporalAccessor, P extends TemporalAccessor>
        extends PartValidator<A, T, P> {

    /**
     * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                           (the second argument).
     */
    protected MomentPartValidator(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            BiPredicate<P, P> validPredicate) {

        super(zoneIdExtractor, partExtractor, momentPredicate(momentExtractor, momentParser, momentCreator, validPredicate));
    }

    /**
     * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific duration before or after a specific moment in
     * time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param durationApplier A function that applies a duration to a {@link TemporalAccessor}.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                           (the second argument).
     */
    protected MomentPartValidator(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
            Function<A, String> durationExtractor, BiFunction<P, TemporalAmount, P> durationApplier,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            BiPredicate<P, P> validPredicate) {

        super(zoneIdExtractor, partExtractor,
                momentPredicate(momentExtractor, momentParser, momentCreator, durationExtractor, durationApplier, validPredicate));
    }

    /**
     * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific moment in time.
     * <p>
     * This constructor is a specialization of {@link #MomentPartValidator(Function, Function, Function, Function, BiFunction, BiPredicate)} that uses
     * a part extractor that does the following:
     * <pre><code>
     * return zoneId == null
     *         ? partExtractor.apply(value)
     *         : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(value, zoneId))
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ForZonedDateTime} for {@link ZonedDateTime},
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part directly from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a {@link ZonedDateTime}.
     *                          The result should represent the same instant.
     * @param zonedDateTimePartExtractor A function that extracts the part from a {@link ZonedDateTime}.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                           (the second argument).
     */
    protected MomentPartValidator(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
            Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor, BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier, Function<ZonedDateTime, P> zonedDateTimePartExtractor,
            BiPredicate<P, P> validPredicate) {

        super(zoneIdExtractor, partExtractor, zoneIdApplier, zonedDateTimePartExtractor,
                momentPredicate(momentExtractor, momentParser, momentCreator, validPredicate));
    }

    /**
     * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific duration before or after a specific moment in
     * time.
     * <p>
     * This constructor is a specialization of
     * {@link #MomentPartValidator(Function, Function, Function, Function, BiFunction, Function, BiFunction, BiPredicate)} that uses a part extractor
     * that does the following:
     * <pre><code>
     * return zoneId == null
     *         ? partExtractor.apply(value)
     *         : zonedDateTimePartExtractor.apply(zoneIdApplier.apply(value, zoneId))
     * </code></pre>
     * This can be used for types that use zones like {@link OffsetDateTime}.
     * <p>
     * Note: to prevent duplicating arguments, use {@link ForZonedDateTime} for {@link ZonedDateTime},
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param durationApplier A function that applies a duration to a {@link TemporalAccessor}.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part directly from a {@link TemporalAccessor}.
     * @param zoneIdApplier A function that applies a zone id  to a {@link TemporalAccessor}, resulting in a {@link ZonedDateTime}.
     *                          The result should represent the same instant.
     * @param zonedDateTimePartExtractor A function that extracts the part from a {@link ZonedDateTime}.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                           (the second argument).
     */
    protected MomentPartValidator(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
            Function<A, String> durationExtractor, BiFunction<P, TemporalAmount, P> durationApplier,
            Function<A, String> zoneIdExtractor,
            Function<T, P> partExtractor, BiFunction<T, ZoneId, ZonedDateTime> zoneIdApplier, Function<ZonedDateTime, P> zonedDateTimePartExtractor,
            BiPredicate<P, P> validPredicate) {

        super(zoneIdExtractor, partExtractor, zoneIdApplier, zonedDateTimePartExtractor,
                momentPredicate(momentExtractor, momentParser, momentCreator, durationExtractor, durationApplier, validPredicate));
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
            extends MomentPartValidator<A, T, P> {

        /**
         * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific moment in time.
         *
         * @param momentExtractor A function that extracts the moment value from a constraint annotation.
         * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
         * @param momentCreator A function that creates a new moment for a given clock.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
         * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
         *                           (the second argument).
         */
        protected WithoutZoneId(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
                Function<A, String> zoneIdExtractor,
                Function<T, P> partExtractor,
                BiPredicate<P, P> validPredicate) {

            super(momentExtractor, momentParser, momentCreator, systemOnlyZoneId(zoneIdExtractor), partExtractor(partExtractor), validPredicate);
        }

        /**
         * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific duration before or after a specific moment in
         * time.
         *
         * @param momentExtractor A function that extracts the moment value from a constraint annotation.
         * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
         * @param momentCreator A function that creates a new moment for a given clock.
         * @param durationExtractor A function that extracts the duration value from a constraint annotation.
         * @param durationApplier A function that applies a duration to a {@link TemporalAccessor}.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
         * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
         *                           (the second argument).
         */
        protected WithoutZoneId(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
                Function<A, String> durationExtractor, BiFunction<P, TemporalAmount, P> durationApplier,
                Function<A, String> zoneIdExtractor,
                Function<T, P> partExtractor,
                BiPredicate<P, P> validPredicate) {

            super(momentExtractor, momentParser, momentCreator, durationExtractor, durationApplier, systemOnlyZoneId(zoneIdExtractor),
                    partExtractor(partExtractor), validPredicate);
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
    public abstract static class ForInstant<A extends Annotation, P extends TemporalAccessor> extends MomentPartValidator<A, Instant, P> {

        /**
         * Creates a new validator that only validates {@link TemporalAccessor} parts against a specific moment in time.
         *
         * @param momentExtractor A function that extracts the moment value from a constraint annotation.
         * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
         * @param momentCreator A function that creates a new moment for a given clock.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part from a {@link ZonedDateTime}.
         * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
         *                           (the second argument).
         */
        protected ForInstant(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
                Function<A, String> zoneIdExtractor,
                Function<ZonedDateTime, P> partExtractor,
                BiPredicate<P, P> validPredicate) {

            super(momentExtractor, momentParser, momentCreator, nonProvidedZoneId(zoneIdExtractor), partExtractor(partExtractor), validPredicate);
        }

        /**
         * Creates a new validator that only validates {@link Instant} parts against a specific duration before or after a specific moment in time.
         *
         * @param momentExtractor A function that extracts the moment value from a constraint annotation.
         * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
         * @param momentCreator A function that creates a new moment for a given clock.
         * @param durationExtractor A function that extracts the duration value from a constraint annotation.
         * @param durationApplier A function that applies a duration to a {@link TemporalAccessor}.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part from a {@link ZonedDateTime}.
         * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
         *                           (the second argument).
         */
        protected ForInstant(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
                Function<A, String> durationExtractor, BiFunction<P, TemporalAmount, P> durationApplier,
                Function<A, String> zoneIdExtractor,
                Function<ZonedDateTime, P> partExtractor,
                BiPredicate<P, P> validPredicate) {

            super(momentExtractor, momentParser, momentCreator, durationExtractor, durationApplier, nonProvidedZoneId(zoneIdExtractor),
                    partExtractor(partExtractor), validPredicate);
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
    public abstract static class ForZonedDateTime<A extends Annotation, P extends TemporalAccessor> extends MomentPartValidator<A, ZonedDateTime, P> {

        /**
         * Creates a new validator that only validates {@link ZonedDateTime} parts against a specific moment in time.
         *
         * @param momentExtractor A function that extracts the moment value from a constraint annotation.
         * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
         * @param momentCreator A function that creates a new moment for a given clock.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part directly from a {@link ZonedDateTime}.
         * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
         *                           (the second argument).
         */
        protected ForZonedDateTime(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
                Function<A, String> zoneIdExtractor,
                Function<ZonedDateTime, P> partExtractor,
                BiPredicate<P, P> validPredicate) {

            super(momentExtractor, momentParser, momentCreator, zoneIdExtractor, partExtractor, ZonedDateTime::withZoneSameInstant, partExtractor,
                    validPredicate);
        }

        /**
         * Creates a new validator that only validates {@link ZonedDateTime} parts against a specific duration before or after a specific moment in
         * time.
         *
         * @param momentExtractor A function that extracts the moment value from a constraint annotation.
         * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
         * @param momentCreator A function that creates a new moment for a given clock.
         * @param durationExtractor A function that extracts the duration value from a constraint annotation.
         * @param durationApplier A function that applies a duration to a {@link TemporalAccessor}.
         * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
         * @param partExtractor A function that extracts a part directly from a {@link ZonedDateTime}.
         * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
         *                           (the second argument).
         */
        protected ForZonedDateTime(Function<A, String> momentExtractor, Function<String, P> momentParser, Function<Clock, P> momentCreator,
                Function<A, String> durationExtractor, BiFunction<P, TemporalAmount, P> durationApplier,
                Function<A, String> zoneIdExtractor,
                Function<ZonedDateTime, P> partExtractor,
                BiPredicate<P, P> validPredicate) {

            super(momentExtractor, momentParser, momentCreator, durationExtractor, durationApplier, zoneIdExtractor,
                    partExtractor, ZonedDateTime::withZoneSameInstant, partExtractor, validPredicate);
        }
    }
}
