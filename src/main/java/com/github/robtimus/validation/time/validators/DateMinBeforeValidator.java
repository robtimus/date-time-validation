/*
 * DateMinBeforeValidator.java
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
import com.github.robtimus.validation.time.DateMinBefore;

/**
 * Container class for constraint validators for {@link DateMinBefore}.
 *
 * @author Rob Spoor
 */
public final class DateMinBeforeValidator {

    private DateMinBeforeValidator() {
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<DateMinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateMinBefore::moment, DateMinBefore::duration, DateTimeValidator::toLocalDate, new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<DateMinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateMinBefore::moment, DateMinBefore::duration, ZonedDateTime::toLocalDate, new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<DateMinBefore, Instant, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateMinBefore::moment, DateMinBefore::duration, DateTimeValidator::toLocalDate, new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<DateMinBefore, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateMinBefore::moment, DateMinBefore::duration, LocalDateTime::toLocalDate, new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<DateMinBefore, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateMinBefore::moment, DateMinBefore::duration, OffsetDateTime::toLocalDate, new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<DateMinBefore, ZonedDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateMinBefore::moment, DateMinBefore::duration, ZonedDateTime::toLocalDate, new MinBeforeValidator.ForLocalDate());
        }
    }
}
