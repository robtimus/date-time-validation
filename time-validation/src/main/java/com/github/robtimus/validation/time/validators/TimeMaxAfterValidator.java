/*
 * TimeMaxAfterValidator.java
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
import com.github.robtimus.validation.datetime.core.CalendarValidator;
import com.github.robtimus.validation.datetime.core.DateValidator;
import com.github.robtimus.validation.datetime.core.MomentPartValidator;
import com.github.robtimus.validation.time.TimeMaxAfter;

/**
 * Container class for constraint validators for {@link TimeMaxAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeMaxAfterValidator {

    private TimeMaxAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<TimeMaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<TimeMaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<TimeMaxAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeMaxAfter::moment, LocalTime::parse, LocalTime::now, TimeMaxAfter::duration, LocalTime::plus, TimeMaxAfter::zoneId,
                    ZonedDateTime::toLocalTime, not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<TimeMaxAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeMaxAfter::moment, LocalTime::parse, LocalTime::now, TimeMaxAfter::duration, LocalTime::plus, TimeMaxAfter::zoneId,
                    LocalDateTime::toLocalTime, not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<TimeMaxAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeMaxAfter::moment, LocalTime::parse, LocalTime::now, TimeMaxAfter::duration, LocalTime::plus, TimeMaxAfter::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<TimeMaxAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeMaxAfter::moment, LocalTime::parse, LocalTime::now, TimeMaxAfter::duration, LocalTime::plus, TimeMaxAfter::zoneId,
                    ZonedDateTime::toLocalTime, not(LocalTime::isAfter));
        }
    }
}
