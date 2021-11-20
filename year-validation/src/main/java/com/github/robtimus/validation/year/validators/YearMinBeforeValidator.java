/*
 * YearMinBeforeValidator.java
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
import com.github.robtimus.validation.year.YearMinBefore;

/**
 * Container class for constraint validators for {@link YearMinBefore}.
 *
 * @author Rob Spoor
 */
public final class YearMinBeforeValidator {

    private YearMinBeforeValidator() {
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<YearMinBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<YearMinBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<YearMinBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearMinBefore::moment, Year::parse, Year::now, YearMinBefore::duration, Year::minus,
                    YearMinBefore::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentPartValidator.WithoutZoneId<YearMinBefore, LocalDate, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearMinBefore::moment, Year::parse, Year::now, YearMinBefore::duration, Year::minus,
                    YearMinBefore::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<YearMinBefore, LocalDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearMinBefore::moment, Year::parse, Year::now, YearMinBefore::duration, Year::minus,
                    YearMinBefore::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<YearMinBefore, OffsetDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearMinBefore::moment, Year::parse, Year::now, YearMinBefore::duration, Year::minus,
                    YearMinBefore::zoneId,
                    Year::from, OffsetDateTime::atZoneSameInstant, Year::from,
                    not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentPartValidator.WithoutZoneId<YearMinBefore, YearMonth, Year> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(YearMinBefore::moment, Year::parse, Year::now, YearMinBefore::duration, Year::minus,
                    YearMinBefore::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMinBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<YearMinBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearMinBefore::moment, Year::parse, Year::now, YearMinBefore::duration, Year::minus,
                    YearMinBefore::zoneId, Year::from, not(Year::isAfter));
        }
    }
}
