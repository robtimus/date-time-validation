/*
 * DateMinAfterValidator.java
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

package com.github.robtimus.validation.date.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.date.DateMinAfter;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentPartValidator;

/**
 * Container class for constraint validators for {@link DateMinAfter}.
 *
 * @author Rob Spoor
 */
public final class DateMinAfterValidator {

    private DateMinAfterValidator() {
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DateMinAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DateMinAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<DateMinAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateMinAfter::moment, LocalDate::parse, LocalDate::now, DateMinAfter::duration, LocalDate::plus, DateMinAfter::zoneId,
                    ZonedDateTime::toLocalDate, not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<DateMinAfter, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateMinAfter::moment, LocalDate::parse, LocalDate::now, DateMinAfter::duration, LocalDate::plus, DateMinAfter::zoneId,
                    LocalDateTime::toLocalDate, not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<DateMinAfter, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateMinAfter::moment, LocalDate::parse, LocalDate::now, DateMinAfter::duration, LocalDate::plus, DateMinAfter::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<DateMinAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateMinAfter::moment, LocalDate::parse, LocalDate::now, DateMinAfter::duration, LocalDate::plus, DateMinAfter::zoneId,
                    ZonedDateTime::toLocalDate, not(LocalDate::isBefore));
        }
    }
}
