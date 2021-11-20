/*
 * DateNotBeforeValidator.java
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
import com.github.robtimus.validation.date.DateNotBefore;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentPartValidator;

/**
 * Container class for constraint validators for {@link DateNotBefore}.
 *
 * @author Rob Spoor
 */
public final class DateNotBeforeValidator {

    private DateNotBeforeValidator() {
    }

    /**
     * A constraint validator for {@link DateNotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DateNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DateNotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DateNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DateNotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<DateNotBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateNotBefore::moment, LocalDate::parse, LocalDate::now, DateNotBefore::zoneId, ZonedDateTime::toLocalDate,
                    not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link DateNotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<DateNotBefore, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateNotBefore::moment, LocalDate::parse, LocalDate::now, DateNotBefore::zoneId, LocalDateTime::toLocalDate,
                    not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link DateNotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<DateNotBefore, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateNotBefore::moment, LocalDate::parse, LocalDate::now, DateNotBefore::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link DateNotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<DateNotBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateNotBefore::moment, LocalDate::parse, LocalDate::now, DateNotBefore::zoneId, ZonedDateTime::toLocalDate,
                    not(LocalDate::isBefore));
        }
    }
}
