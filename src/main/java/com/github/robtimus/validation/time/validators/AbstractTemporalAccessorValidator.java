/*
 * AbstractTemporalAccessorValidator.java
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
import java.time.Clock;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 */
public abstract class AbstractTemporalAccessorValidator<A extends Annotation, T extends TemporalAccessor> extends DateTimeValidator<A, T> {

    private final Function<A, T> momentExtractor;
    private final Function<Clock, T> momentCreator;
    private final Function<A, TemporalAmount> durationExtractor;
    private final BiFunction<T, TemporalAmount, T> durationApplier;
    private final BiPredicate<T, T> validPredicate;

    private T moment;
    private TemporalAmount duration;

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractTemporalAccessorValidator(Function<A, T> momentExtractor, Function<Clock, T> momentCreator, BiPredicate<T, T> validPredicate) {
        this.momentExtractor = momentExtractor;
        this.momentCreator = momentCreator;
        this.durationExtractor = null;
        this.durationApplier = null;
        this.validPredicate = validPredicate;
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param durationExtractor A function that extracts the duration from a constraint annotation.
     * @param durationParser A function that parses text into a duration.
     * @param durationApplier A function that applies a duration to an instant.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractTemporalAccessorValidator(Function<A, T> momentExtractor, Function<Clock, T> momentCreator,
            Function<A, String> durationExtractor, Function<String, TemporalAmount> durationParser, BiFunction<T, TemporalAmount, T> durationApplier,
            BiPredicate<T, T> validPredicate) {

        this(momentExtractor, momentCreator, annotation -> durationParser.apply(durationExtractor.apply(annotation)), durationApplier,
                validPredicate);
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param durationExtractor A function that extracts the duration from a constraint annotation.
     * @param durationApplier A function that applies a duration to an instant.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractTemporalAccessorValidator(Function<A, T> momentExtractor, Function<Clock, T> momentCreator,
            Function<A, TemporalAmount> durationExtractor, BiFunction<T, TemporalAmount, T> durationApplier,
            BiPredicate<T, T> validPredicate) {

        this.momentExtractor = momentExtractor;
        this.momentCreator = momentCreator;
        this.durationExtractor = durationExtractor;
        this.durationApplier = durationApplier;
        this.validPredicate = validPredicate;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        moment = momentExtractor.apply(constraintAnnotation);

        duration = durationExtractor != null ? durationExtractor.apply(constraintAnnotation) : null;
        validateDuration();

        if (moment != null && duration != null) {
            // apply the duration at this time, as the result will be the same for every validation
            moment = durationApplier.apply(moment, duration);
            duration = null;
        }
    }

    private void validateDuration() {
        if (duration != null) {
            // apply the duration to validate it
            durationApplier.apply(momentCreator.apply(Clock.systemUTC()), duration);
        }
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        T temporalAccessor = moment != null ? moment : momentCreator.apply(context.getClockProvider().getClock());
        if (duration != null) {
            temporalAccessor = durationApplier.apply(temporalAccessor, duration);
        }
        return validPredicate.test(value, temporalAccessor);
    }

    /**
     * Returns a function that extracts the moment value from a constraint annotation; then if it's {@link DateTimeValidator#NOW}, the function
     * returns {@code null}, otherwise it returns the result of parsing the value.
     *
     * @param <A> The constraint annotation type.
     * @param <T> The {@link TemporalAccessor} type to validate.
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param parser A function that parses text into a {@link TemporalAccessor}.
     * @return A function that works as described.
     */
    protected static <A extends Annotation, T extends TemporalAccessor> Function<A, T> momentExtractor(Function<A, String> momentExtractor,
            Function<String, T> parser) {

        return annotation -> {
            String momentValue = momentExtractor.apply(annotation);
            return NOW.equals(momentValue) ? null : parser.apply(momentValue);
        };
    }
}
