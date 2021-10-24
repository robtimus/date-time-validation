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

    private final Function<A, String> momentExtractor;
    private final Function<String, T> momentParser;
    private final Function<Clock, T> momentCreator;
    private final Function<A, String> durationExtractor;
    private final BiFunction<T, TemporalAmount, T> durationApplier;
    private final BiPredicate<T, T> validPredicate;

    private T moment;
    private TemporalAmount duration;

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractTemporalAccessorValidator(Function<A, String> momentExtractor, Function<String, T> momentParser,
            Function<Clock, T> momentCreator, BiPredicate<T, T> validPredicate) {

        this.momentExtractor = momentExtractor;
        this.momentParser = momentParser;
        this.momentCreator = momentCreator;
        this.durationExtractor = null;
        this.durationApplier = null;
        this.validPredicate = validPredicate;
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param momentParser A function that parses a moment value into a {@link TemporalAccessor}.
     * @param momentCreator A function that creates a new moment for a given clock.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param durationApplier A function that applies a duration to an instant.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractTemporalAccessorValidator(Function<A, String> momentExtractor, Function<String, T> momentParser,
            Function<Clock, T> momentCreator,
            Function<A, String> durationExtractor, BiFunction<T, TemporalAmount, T> durationApplier,
            BiPredicate<T, T> validPredicate) {

        this.momentExtractor = momentExtractor;
        this.momentParser = momentParser;
        this.momentCreator = momentCreator;
        this.durationExtractor = durationExtractor;
        this.durationApplier = durationApplier;
        this.validPredicate = validPredicate;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        initializeMoment(constraintAnnotation);
        initializeDuration(constraintAnnotation);

        if (moment != null && duration != null) {
            // apply the duration at this time, as the result will be the same for every validation
            moment = durationApplier.apply(moment, duration);
            duration = null;
        }
    }

    private void initializeMoment(A constraintAnnotation) {
        String value = momentExtractor.apply(constraintAnnotation);
        moment = NOW.equals(value) ? null : momentParser.apply(value);
    }

    private void initializeDuration(A constraintAnnotation) {
        if (durationExtractor != null) {
            String value = durationExtractor.apply(constraintAnnotation);
            duration = ISODuration.parse(value);
            // apply the duration to validate it
            durationApplier.apply(momentCreator.apply(Clock.systemUTC()), duration);
        } else {
            duration = null;
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
}
