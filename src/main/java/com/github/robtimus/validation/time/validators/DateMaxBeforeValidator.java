/*
 * DateMaxBeforeValidator.java
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
import com.github.robtimus.validation.time.DateMaxBefore;

/**
 * Container class for constraint validators for {@link DateMaxBefore}.
 *
 * @author Rob Spoor
 */
public final class DateMaxBeforeValidator {

    private DateMaxBeforeValidator() {
    }

    /**
     * A constraint validator for {@link DateMaxBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<DateMaxBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateMaxBefore::moment, DateMaxBefore::duration, DateTimeValidator::toLocalDate, new MaxBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<DateMaxBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateMaxBefore::moment, DateMaxBefore::duration, ZonedDateTime::toLocalDate, new MaxBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<DateMaxBefore, Instant, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateMaxBefore::moment, DateMaxBefore::duration, DateTimeValidator::toLocalDate, new MaxBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<DateMaxBefore, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateMaxBefore::moment, DateMaxBefore::duration, LocalDateTime::toLocalDate, new MaxBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<DateMaxBefore, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateMaxBefore::moment, DateMaxBefore::duration, OffsetDateTime::toLocalDate, new MaxBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMaxBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<DateMaxBefore, ZonedDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateMaxBefore::moment, DateMaxBefore::duration, ZonedDateTime::toLocalDate, new MaxBeforeValidator.ForLocalDate());
        }
    }
}