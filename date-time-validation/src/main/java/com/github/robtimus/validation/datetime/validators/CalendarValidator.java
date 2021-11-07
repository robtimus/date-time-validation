/*
 * CalendarValidator.java
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Calendar} validators that validate the entire value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 */
public abstract class CalendarValidator<A extends Annotation> extends DateTimeValidator<A, Calendar> {

    private final Function<A, String> momentExtractor;
    private final Function<A, String> durationExtractor;
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
    protected CalendarValidator(Function<A, String> momentExtractor,
            BiPredicate<ZonedDateTime, ZonedDateTime> validPredicate) {

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
     * @param durationApplier A function that applies a duration to a zoned date/time.
     * @param validPredicate A predicate that determines whether or not a value (the first argument) is valid compared to a specific moment
     *                          (the second argument).
     */
    protected CalendarValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor, BiFunction<ZonedDateTime, TemporalAmount, ZonedDateTime> durationApplier,
            BiPredicate<ZonedDateTime, ZonedDateTime> validPredicate) {

        this.momentExtractor = momentExtractor;
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
        moment = NOW.equals(value) ? null : ZonedDateTime.parse(value);
    }

    private void initializeDuration(A constraintAnnotation) {
        if (durationExtractor != null) {
            String value = durationExtractor.apply(constraintAnnotation);
            duration = ISODuration.parse(value);
            // apply the duration to validate it
            durationApplier.apply(ZonedDateTime.now(), duration);
        } else {
            duration = null;
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
