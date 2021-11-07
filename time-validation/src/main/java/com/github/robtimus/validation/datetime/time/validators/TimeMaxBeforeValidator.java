/*
 * TimeMaxBeforeValidator.java
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

package com.github.robtimus.validation.datetime.time.validators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.time.TimeMaxBefore;
import com.github.robtimus.validation.datetime.validators.CalendarPartValidator;
import com.github.robtimus.validation.datetime.validators.DatePartValidator;
import com.github.robtimus.validation.datetime.validators.InstantPartValidator;
import com.github.robtimus.validation.datetime.validators.MaxBeforeValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;

/**
 * Container class for constraint validators for {@link TimeMaxBefore}.
 *
 * @author Rob Spoor
 */
public final class TimeMaxBeforeValidator {

    private TimeMaxBeforeValidator() {
    }

    /**
     * A constraint validator for {@link TimeMaxBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<TimeMaxBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeMaxBefore::moment, TimeMaxBefore::duration, nonProvidedZoneId(TimeMaxBefore::zoneId), ZonedDateTime::toLocalTime,
                    new MaxBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<TimeMaxBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeMaxBefore::moment, TimeMaxBefore::duration, TimeMaxBefore::zoneId, ZonedDateTime::toLocalTime,
                    new MaxBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<TimeMaxBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeMaxBefore::moment, TimeMaxBefore::duration, nonProvidedZoneId(TimeMaxBefore::zoneId), ZonedDateTime::toLocalTime,
                    new MaxBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<TimeMaxBefore, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeMaxBefore::moment, TimeMaxBefore::duration, systemOnlyZoneId(TimeMaxBefore::zoneId), LocalDateTime::toLocalTime,
                    new MaxBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<TimeMaxBefore, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeMaxBefore::moment, TimeMaxBefore::duration, TimeMaxBefore::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    new MaxBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<TimeMaxBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeMaxBefore::moment, TimeMaxBefore::duration, TimeMaxBefore::zoneId, ZonedDateTime::toLocalTime,
                    new MaxBeforeValidator.ForLocalTime());
        }
    }
}