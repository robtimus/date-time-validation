/*
 * DateBeforeValidator.java
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
import com.github.robtimus.validation.time.DateBefore;

/**
 * Container class for constraint validators for {@link DateBefore}.
 *
 * @author Rob Spoor
 */
public final class DateBeforeValidator {

    private DateBeforeValidator() {
    }

    /**
     * A constraint validator for {@link DateBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<DateBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateBefore::moment, nonProvidedZoneId(DateBefore::zoneId), ZonedDateTime::toLocalDate, new BeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<DateBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateBefore::moment, DateBefore::zoneId, ZonedDateTime::toLocalDate, new BeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<DateBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateBefore::moment, nonProvidedZoneId(DateBefore::zoneId), ZonedDateTime::toLocalDate, new BeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<DateBefore, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateBefore::moment, systemOnlyZoneId(DateBefore::zoneId), LocalDateTime::toLocalDate, new BeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<DateBefore, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateBefore::moment, DateBefore::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    new BeforeValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<DateBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateBefore::moment, DateBefore::zoneId, ZonedDateTime::toLocalDate, new BeforeValidator.ForLocalDate());
        }
    }
}
