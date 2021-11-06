/*
 * TimeNotAfterValidator.java
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

package com.github.robtimus.validation.time.time.validators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.time.time.TimeNotAfter;
import com.github.robtimus.validation.time.validators.CalendarPartValidator;
import com.github.robtimus.validation.time.validators.DatePartValidator;
import com.github.robtimus.validation.time.validators.InstantPartValidator;
import com.github.robtimus.validation.time.validators.NotAfterValidator;
import com.github.robtimus.validation.time.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.time.validators.ZonedDateTimePartValidator;

/**
 * Container class for constraint validators for {@link TimeNotAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeNotAfterValidator {

    private TimeNotAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<TimeNotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeNotAfter::moment, nonProvidedZoneId(TimeNotAfter::zoneId), ZonedDateTime::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<TimeNotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, ZonedDateTime::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<TimeNotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeNotAfter::moment, nonProvidedZoneId(TimeNotAfter::zoneId), ZonedDateTime::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<TimeNotAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeNotAfter::moment, systemOnlyZoneId(TimeNotAfter::zoneId), LocalDateTime::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<TimeNotAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<TimeNotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, ZonedDateTime::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }
}
