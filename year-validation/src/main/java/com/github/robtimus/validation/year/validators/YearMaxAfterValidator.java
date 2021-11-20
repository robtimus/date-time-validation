/*
 * YearMaxAfterValidator.java
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
import com.github.robtimus.validation.year.YearMaxAfter;

/**
 * Container class for constraint validators for {@link YearMaxAfter}.
 *
 * @author Rob Spoor
 */
public final class YearMaxAfterValidator {

    private YearMaxAfterValidator() {
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<YearMaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<YearMaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<YearMaxAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearMaxAfter::moment, Year::parse, Year::now, YearMaxAfter::duration, Year::plus,
                    YearMaxAfter::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentPartValidator.WithoutZoneId<YearMaxAfter, LocalDate, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearMaxAfter::moment, Year::parse, Year::now, YearMaxAfter::duration, Year::plus,
                    YearMaxAfter::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<YearMaxAfter, LocalDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearMaxAfter::moment, Year::parse, Year::now, YearMaxAfter::duration, Year::plus,
                    YearMaxAfter::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<YearMaxAfter, OffsetDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearMaxAfter::moment, Year::parse, Year::now, YearMaxAfter::duration, Year::plus,
                    YearMaxAfter::zoneId,
                    Year::from, OffsetDateTime::atZoneSameInstant, Year::from,
                    not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentPartValidator.WithoutZoneId<YearMaxAfter, YearMonth, Year> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(YearMaxAfter::moment, Year::parse, Year::now, YearMaxAfter::duration, Year::plus,
                    YearMaxAfter::zoneId, Year::from, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link YearMaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<YearMaxAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearMaxAfter::moment, Year::parse, Year::now, YearMaxAfter::duration, Year::plus,
                    YearMaxAfter::zoneId, Year::from, not(Year::isAfter));
        }
    }
}
