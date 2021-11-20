/*
 * YearAfterValidator.java
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
import com.github.robtimus.validation.year.YearAfter;

/**
 * Container class for constraint validators for {@link YearAfter}.
 *
 * @author Rob Spoor
 */
public final class YearAfterValidator {

    private YearAfterValidator() {
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<YearAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<YearAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<YearAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearAfter::moment, Year::parse, Year::now, YearAfter::zoneId, Year::from, Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentPartValidator.WithoutZoneId<YearAfter, LocalDate, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearAfter::moment, Year::parse, Year::now, YearAfter::zoneId, Year::from, Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<YearAfter, LocalDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearAfter::moment, Year::parse, Year::now, YearAfter::zoneId, Year::from, Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<YearAfter, OffsetDateTime, Year> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearAfter::moment, Year::parse, Year::now, YearAfter::zoneId,
                    Year::from, OffsetDateTime::atZoneSameInstant, Year::from,
                    Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentPartValidator.WithoutZoneId<YearAfter, YearMonth, Year> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(YearAfter::moment, Year::parse, Year::now, YearAfter::zoneId, Year::from, Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link YearAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<YearAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearAfter::moment, Year::parse, Year::now, YearAfter::zoneId, Year::from, Year::isAfter);
        }
    }
}
