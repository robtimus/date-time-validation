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

package com.github.robtimus.validation.time.validators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentPartValidator;
import com.github.robtimus.validation.time.TimeAfter;

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
    public static class ForDate extends DateValidator<TimeAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<TimeAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeAfter::moment, LocalTime::parse, LocalTime::now, TimeAfter::zoneId, ZonedDateTime::toLocalTime, LocalTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<TimeAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeAfter::moment, LocalTime::parse, LocalTime::now, TimeAfter::zoneId, LocalDateTime::toLocalTime, LocalTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<TimeAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeAfter::moment, LocalTime::parse, LocalTime::now, TimeAfter::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    LocalTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeAfter::moment, LocalTime::parse, LocalTime::now, TimeAfter::zoneId, ZonedDateTime::toLocalTime, LocalTime::isAfter);
        }
    }
}
