/*
 * AbstractCalendarValidator.java
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Calendar} validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 */
public abstract class AbstractCalendarValidator<A extends Annotation> extends DateTimeValidator<A, Calendar> {

    private final Function<A, String> momentExtractor;
    private final Function<A, TemporalAmount> durationExtractor;
    private final BiFunction<ZonedDateTime, TemporalAmount, ZonedDateTime> durationApplier;
    private final BiPredicate<ZonedDateTime, ZonedDateTime> validPredicate;

    private ZonedDateTime moment;
    private TemporalAmount duration;

    /**
     * Creates a new validator that only validates calendars against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractCalendarValidator(Function<A, String> momentExtractor, BiPredicate<ZonedDateTime, ZonedDateTime> validPredicate) {
        this.momentExtractor = momentExtractor;
        this.durationExtractor = null;
        this.durationApplier = null;
        this.validPredicate = validPredicate;
    }

    /**
     * Creates a new validator that only validates calendars against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param durationExtractor A function that extracts the duration from a constraint annotation.
     * @param durationParser A function that parses text into a duration.
     * @param durationApplier A function that applies a duration to a zoned date/time.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractCalendarValidator(Function<A, String> momentExtractor, Function<A, String> durationExtractor,
            Function<String, TemporalAmount> durationParser, BiFunction<ZonedDateTime, TemporalAmount, ZonedDateTime> durationApplier,
            BiPredicate<ZonedDateTime, ZonedDateTime> validPredicate) {

        this(momentExtractor, annotation -> durationParser.apply(durationExtractor.apply(annotation)), durationApplier, validPredicate);
    }

    /**
     * Creates a new validator that only validates calendars against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param durationExtractor A function that extracts the duration from a constraint annotation.
     * @param durationApplier A function that applies a duration to a zoned date/time.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected AbstractCalendarValidator(Function<A, String> momentExtractor, Function<A, TemporalAmount> durationExtractor,
            BiFunction<ZonedDateTime, TemporalAmount, ZonedDateTime> durationApplier, BiPredicate<ZonedDateTime, ZonedDateTime> validPredicate) {

        this.momentExtractor = momentExtractor;
        this.durationExtractor = durationExtractor;
        this.durationApplier = durationApplier;
        this.validPredicate = validPredicate;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        String momentValue = momentExtractor.apply(constraintAnnotation);
        moment = NOW.equals(momentValue) ? null : ZonedDateTime.parse(momentValue);

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
            durationApplier.apply(ZonedDateTime.now(), duration);
        }
    }

    @Override
    public boolean isValid(Calendar value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        ZonedDateTime zonedDateTime = moment != null ? moment : ZonedDateTime.now(context.getClockProvider().getClock());
        if (duration != null) {
            zonedDateTime = durationApplier.apply(zonedDateTime, duration);
        }
        return validPredicate.test(toZonedDateTime(value), zonedDateTime);
    }

    private ZonedDateTime toZonedDateTime(Calendar calendar) {
        // This is exactly what GregorianCalendar does
        ZoneId zoneId = calendar.getTimeZone().toZoneId();
        return ZonedDateTime.ofInstant(calendar.toInstant(), zoneId);
    }
}
