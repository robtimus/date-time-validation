/*
 * DateAfterValidator.java
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
import com.github.robtimus.validation.time.DateAfter;

/**
 * Container class for constraint validators for {@link DateAfter}.
 *
 * @author Rob Spoor
 */
public final class DateAfterValidator {

    private DateAfterValidator() {
    }

    /**
     * A constraint validator for {@link DateAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<DateAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateAfter::moment, nonProvidedZoneId(DateAfter::zoneId), ZonedDateTime::toLocalDate, new AfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<DateAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateAfter::moment, DateAfter::zoneId, ZonedDateTime::toLocalDate, new AfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<DateAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateAfter::moment, nonProvidedZoneId(DateAfter::zoneId), ZonedDateTime::toLocalDate, new AfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<DateAfter, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateAfter::moment, systemOnlyZoneId(DateAfter::zoneId), LocalDateTime::toLocalDate, new AfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<DateAfter, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateAfter::moment, DateAfter::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    new AfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<DateAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateAfter::moment, DateAfter::zoneId, ZonedDateTime::toLocalDate, new AfterValidator.ForLocalDate());
        }
    }
}
