/*
 * TimeBeforeValidator.java
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.time.TimeBefore;

/**
 * Container class for constraint validators for {@link TimeBefore}.
 *
 * @author Rob Spoor
 */
public final class TimeBeforeValidator {

    private TimeBeforeValidator() {
    }

    /**
     * A constraint validator for {@link TimeBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<TimeBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeBefore::moment, nonProvidedZoneId(TimeBefore::zoneId), ZonedDateTime::toLocalTime, new BeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<TimeBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeBefore::moment, TimeBefore::zoneId, ZonedDateTime::toLocalTime, new BeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<TimeBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeBefore::moment, nonProvidedZoneId(TimeBefore::zoneId), ZonedDateTime::toLocalTime, new BeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<TimeBefore, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeBefore::moment, systemOnlyZoneId(TimeBefore::zoneId), LocalDateTime::toLocalTime, new BeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<TimeBefore, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeBefore::moment, TimeBefore::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    new BeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<TimeBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeBefore::moment, TimeBefore::zoneId, ZonedDateTime::toLocalTime, new BeforeValidator.ForLocalTime());
        }
    }
}
