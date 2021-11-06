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

package com.github.robtimus.validation.datetime.date.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.date.DateMinBefore;
import com.github.robtimus.validation.datetime.validators.CalendarPartValidator;
import com.github.robtimus.validation.datetime.validators.DatePartValidator;
import com.github.robtimus.validation.datetime.validators.InstantPartValidator;
import com.github.robtimus.validation.datetime.validators.MinBeforeValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;

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
    public static class ForDate extends DatePartValidator<DateMinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateMinBefore::moment, DateMinBefore::duration, nonProvidedZoneId(DateMinBefore::zoneId), ZonedDateTime::toLocalDate,
                    new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<DateMinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateMinBefore::moment, DateMinBefore::duration, DateMinBefore::zoneId, ZonedDateTime::toLocalDate,
                    new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<DateMinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateMinBefore::moment, DateMinBefore::duration, nonProvidedZoneId(DateMinBefore::zoneId), ZonedDateTime::toLocalDate,
                    new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<DateMinBefore, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateMinBefore::moment, DateMinBefore::duration, systemOnlyZoneId(DateMinBefore::zoneId), LocalDateTime::toLocalDate,
                    new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<DateMinBefore, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateMinBefore::moment, DateMinBefore::duration, DateMinBefore::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    new MinBeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateMinBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<DateMinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateMinBefore::moment, DateMinBefore::duration, DateMinBefore::zoneId, ZonedDateTime::toLocalDate,
                    new MinBeforeValidator.ForLocalDate());
        }
    }
}
