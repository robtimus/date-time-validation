/*
 * TimeAfterValidator.java
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
import com.github.robtimus.validation.datetime.time.TimeAfter;
import com.github.robtimus.validation.datetime.validators.AfterValidator;
import com.github.robtimus.validation.datetime.validators.CalendarPartValidator;
import com.github.robtimus.validation.datetime.validators.DatePartValidator;
import com.github.robtimus.validation.datetime.validators.InstantPartValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;

/**
 * Container class for constraint validators for {@link TimeAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeAfterValidator {

    private TimeAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeAfter::moment, nonProvidedZoneId(TimeAfter::zoneId), ZonedDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeAfter::moment, TimeAfter::zoneId, ZonedDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeAfter::moment, nonProvidedZoneId(TimeAfter::zoneId), ZonedDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<TimeAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeAfter::moment, systemOnlyZoneId(TimeAfter::zoneId), LocalDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<TimeAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeAfter::moment, TimeAfter::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeAfter::moment, TimeAfter::zoneId, ZonedDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }
}
