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

package com.github.robtimus.validation.time.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.time.DateMinAfter;

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
    public static class ForDate extends AbstractDatePartValidator<DateMinAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateMinAfter::moment, DateMinAfter::duration, DateMinAfter::zoneId, DateTimeValidator::toLocalDate,
                    new MinAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<DateMinAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateMinAfter::moment, DateMinAfter::duration, DateMinAfter::zoneId, ZonedDateTime::toLocalDate,
                    new MinAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<DateMinAfter, Instant, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateMinAfter::moment, DateMinAfter::duration, DateMinAfter::zoneId, DateTimeValidator::toLocalDate,
                    new MinAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<DateMinAfter, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateMinAfter::moment, DateMinAfter::duration, DateMinAfter::zoneId, LocalDateTime::toLocalDate,
                    new MinAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<DateMinAfter, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateMinAfter::moment, DateMinAfter::duration, DateMinAfter::zoneId, DateTimeValidator::toLocalDate,
                    new MinAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<DateMinAfter, ZonedDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateMinAfter::moment, DateMinAfter::duration, DateMinAfter::zoneId, DateTimeValidator::toLocalDate,
                    new MinAfterValidator.ForLocalDate());
        }
    }
}
