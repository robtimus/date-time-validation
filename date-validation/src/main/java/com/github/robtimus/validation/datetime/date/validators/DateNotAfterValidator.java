/*
 * DateNotAfterValidator.java
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
import com.github.robtimus.validation.datetime.date.DateNotAfter;
import com.github.robtimus.validation.datetime.validators.CalendarPartValidator;
import com.github.robtimus.validation.datetime.validators.DatePartValidator;
import com.github.robtimus.validation.datetime.validators.InstantPartValidator;
import com.github.robtimus.validation.datetime.validators.NotAfterValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;

/**
 * Container class for constraint validators for {@link DateNotAfter}.
 *
 * @author Rob Spoor
 */
public final class DateNotAfterValidator {

    private DateNotAfterValidator() {
    }

    /**
     * A constraint validator for {@link DateNotAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<DateNotAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(DateNotAfter::moment, nonProvidedZoneId(DateNotAfter::zoneId), ZonedDateTime::toLocalDate, new NotAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateNotAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<DateNotAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(DateNotAfter::moment, DateNotAfter::zoneId, ZonedDateTime::toLocalDate, new NotAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateNotAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<DateNotAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DateNotAfter::moment, nonProvidedZoneId(DateNotAfter::zoneId), ZonedDateTime::toLocalDate, new NotAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateNotAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<DateNotAfter, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DateNotAfter::moment, systemOnlyZoneId(DateNotAfter::zoneId), LocalDateTime::toLocalDate, new NotAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateNotAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<DateNotAfter, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DateNotAfter::moment, DateNotAfter::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    new NotAfterValidator.ForLocalDate());
        }
    }

    /**
     * A constraint validator for {@link DateNotAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<DateNotAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DateNotAfter::moment, DateNotAfter::zoneId, ZonedDateTime::toLocalDate, new NotAfterValidator.ForLocalDate());
        }
    }
}
