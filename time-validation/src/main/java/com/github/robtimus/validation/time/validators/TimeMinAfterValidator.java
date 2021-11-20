/*
 * TimeMinAfterValidator.java
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
import com.github.robtimus.validation.time.TimeMinAfter;

/**
 * Container class for constraint validators for {@link TimeMinAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeMinAfterValidator {

    private TimeMinAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeMinAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<TimeMinAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link TimeMinAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<TimeMinAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMinAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<TimeMinAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeMinAfter::moment, LocalTime::parse, LocalTime::now, TimeMinAfter::duration, LocalTime::plus, TimeMinAfter::zoneId,
                    ZonedDateTime::toLocalTime, not(LocalTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link TimeMinAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<TimeMinAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeMinAfter::moment, LocalTime::parse, LocalTime::now, TimeMinAfter::duration, LocalTime::plus, TimeMinAfter::zoneId,
                    LocalDateTime::toLocalTime, not(LocalTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link TimeMinAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<TimeMinAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeMinAfter::moment, LocalTime::parse, LocalTime::now, TimeMinAfter::duration, LocalTime::plus, TimeMinAfter::zoneId,
                    OffsetDateTime::toLocalTime, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalTime,
                    not(LocalTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link TimeMinAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<TimeMinAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeMinAfter::moment, LocalTime::parse, LocalTime::now, TimeMinAfter::duration, LocalTime::plus, TimeMinAfter::zoneId,
                    ZonedDateTime::toLocalTime, not(LocalTime::isBefore));
        }
    }
}
