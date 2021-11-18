/*
 * MomentValueValidator.java
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
import java.time.Clock;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate the entire value against a specific moment in time.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 */
public abstract class MomentValueValidator<A extends Annotation, T> extends BaseValidator<A, T> {

    /** A string representing the current date/time. */
    public static final String NOW = "now"; //$NON-NLS-1$

    /**
     * Creates a new validator that only validates {@link TemporalAccessor}s against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected MomentValueValidator(Function<A, String> momentExtractor, Function<String, T> momentParser, Function<Clock, T> momentCreator,
            BiPredicate<T, T> validPredicate) {

        super(momentPredicate(momentExtractor, momentParser, momentCreator, validPredicate));
    }

    /**
     * Creates a new validator that only validates {@link TemporalAccessor}s against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param durationApplier A function that applies a duration to a {@link TemporalAccessor}.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected MomentValueValidator(Function<A, String> momentExtractor, Function<String, T> momentParser, Function<Clock, T> momentCreator,
            Function<A, String> durationExtractor, BiFunction<T, TemporalAmount, T> durationApplier,
            BiPredicate<T, T> validPredicate) {

        super(momentPredicate(momentExtractor, momentParser, momentCreator, durationExtractor, durationApplier, validPredicate));
    }

    static <A, T> Function<A, BiPredicate<T, ConstraintValidatorContext>> momentPredicate(
            Function<A, String> momentExtractor, Function<String, T> momentParser, Function<Clock, T> momentCreator,
            BiPredicate<T, T> validPredicate) {

        Objects.requireNonNull(momentExtractor);
        Objects.requireNonNull(momentParser);
        Objects.requireNonNull(momentCreator);
        Objects.requireNonNull(validPredicate);

        return annotation -> {
            T moment = extractMoment(annotation, momentExtractor, momentParser);

            return (value, context) -> {
                T temporalAccessor = moment != null ? moment : momentCreator.apply(context.getClockProvider().getClock());
                return validPredicate.test(value, temporalAccessor);
            };
        };
    }

    static <A, T> Function<A, BiPredicate<T, ConstraintValidatorContext>> momentPredicate(
            Function<A, String> momentExtractor, Function<String, T> momentParser, Function<Clock, T> momentCreator,
            Function<A, String> durationExtractor, BiFunction<T, TemporalAmount, T> durationApplier,
            BiPredicate<T, T> validPredicate) {

        Objects.requireNonNull(momentExtractor);
        Objects.requireNonNull(momentParser);
        Objects.requireNonNull(momentCreator);
        Objects.requireNonNull(durationExtractor);
        Objects.requireNonNull(durationApplier);
        Objects.requireNonNull(validPredicate);

        return annotation -> {
            T moment = extractMoment(annotation, momentExtractor, momentParser);
            TemporalAmount duration = extractDuration(annotation, momentCreator, durationExtractor, durationApplier);

            return (value, context) -> {
                T temporalAccessor = moment != null ? moment : momentCreator.apply(context.getClockProvider().getClock());
                temporalAccessor = durationApplier.apply(temporalAccessor, duration);
                return validPredicate.test(value, temporalAccessor);
            };
        };
    }

    private static <A, T> T extractMoment(A annotation,
            Function<A, String> momentExtractor, Function<String, T> momentParser) {

        String text = momentExtractor.apply(annotation);
        return NOW.equals(text) ? null : momentParser.apply(text);
    }

    private static <A, T> TemporalAmount extractDuration(A annotation,
            Function<Clock, T> momentCreator,
            Function<A, String> durationExtractor, BiFunction<T, TemporalAmount, T> durationApplier) {

        String text = durationExtractor.apply(annotation);
        TemporalAmount duration = ISODuration.parse(text);
        // apply the duration to validate it
        durationApplier.apply(momentCreator.apply(Clock.systemUTC()), duration);
        return duration;
    }
}
