/*
 * DateMaxAfterValidator.java
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
import com.github.robtimus.validation.time.DateMaxAfter;

/**
 * Container class for constraint validators for {@link DateMaxAfter}.
 *
 * @author Rob Spoor
 */
public final class DateMaxAfterValidator {

    private DateMaxAfterValidator() {
    }

    /**
     * A constraint validator for {@link DateMaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<DateMaxAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateMaxAfter::moment, DateMaxAfter::duration, DateMaxAfter::zoneId, DateTimeValidator::toLocalDate,
                    new MaxAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<DateMaxAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateMaxAfter::moment, DateMaxAfter::duration, DateMaxAfter::zoneId, ZonedDateTime::toLocalDate,
                    new MaxAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<DateMaxAfter, Instant, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateMaxAfter::moment, DateMaxAfter::duration, DateMaxAfter::zoneId, DateTimeValidator::toLocalDate,
                    new MaxAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<DateMaxAfter, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateMaxAfter::moment, DateMaxAfter::duration, DateMaxAfter::zoneId, AbstractTemporalAccessorPartValidator::toLocalDate,
                    new MaxAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<DateMaxAfter, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateMaxAfter::moment, DateMaxAfter::duration, DateMaxAfter::zoneId, AbstractTemporalAccessorPartValidator::toLocalDate,
                    new MaxAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<DateMaxAfter, ZonedDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateMaxAfter::moment, DateMaxAfter::duration, DateMaxAfter::zoneId, AbstractTemporalAccessorPartValidator::toLocalDate,
                    new MaxAfterValidator.ForLocalDate());
        }
    }
}
