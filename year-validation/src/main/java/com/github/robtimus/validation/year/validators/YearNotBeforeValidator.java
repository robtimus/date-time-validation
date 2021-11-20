/*
 * YearNotBeforeValidator.java
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

package com.github.robtimus.validation.year.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentPartValidator;
import com.github.robtimus.validation.year.YearNotBefore;

/**
 * Container class for constraint validators for {@link YearNotBefore}.
 *
 * @author Rob Spoor
 */
public final class YearNotBeforeValidator {

    private YearNotBeforeValidator() {
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<YearNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<YearNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<YearNotBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearNotBefore::moment, Year::parse, Year::now, YearNotBefore::zoneId, Year::from,
                    not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentPartValidator.WithoutZoneId<YearNotBefore, LocalDate, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearNotBefore::moment, Year::parse, Year::now, YearNotBefore::zoneId, Year::from,
                    not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<YearNotBefore, LocalDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearNotBefore::moment, Year::parse, Year::now, YearNotBefore::zoneId, Year::from,
                    not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<YearNotBefore, OffsetDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearNotBefore::moment, Year::parse, Year::now, YearNotBefore::zoneId,
                    Year::from, OffsetDateTime::atZoneSameInstant, Year::from,
                    not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentPartValidator.WithoutZoneId<YearNotBefore, YearMonth, Year> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(YearNotBefore::moment, Year::parse, Year::now, YearNotBefore::zoneId, Year::from, not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearNotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<YearNotBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearNotBefore::moment, Year::parse, Year::now, YearNotBefore::zoneId, Year::from,
                    not(Year::isBefore));
        }
    }
}
