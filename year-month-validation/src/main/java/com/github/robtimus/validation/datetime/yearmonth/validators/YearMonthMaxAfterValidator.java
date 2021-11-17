/*
 * YearMonthMaxAfterValidator.java
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

package com.github.robtimus.validation.datetime.yearmonth.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.validators.CalendarPartValidator;
import com.github.robtimus.validation.datetime.validators.DatePartValidator;
import com.github.robtimus.validation.datetime.validators.InstantPartValidator;
import com.github.robtimus.validation.datetime.validators.MaxAfterValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;
import com.github.robtimus.validation.datetime.yearmonth.YearMonthMaxAfter;

/**
 * Container class for constraint validators for {@link YearMonthMaxAfter}.
 *
 * @author Rob Spoor
 */
public final class YearMonthMaxAfterValidator {

    private YearMonthMaxAfterValidator() {
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<YearMonthMaxAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, nonProvidedZoneId(YearMonthMaxAfter::zoneId), YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<YearMonthMaxAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, YearMonthMaxAfter::zoneId, YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<YearMonthMaxAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, nonProvidedZoneId(YearMonthMaxAfter::zoneId), YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorPartValidator<YearMonthMaxAfter, LocalDate, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, systemOnlyZoneId(YearMonthMaxAfter::zoneId), YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<YearMonthMaxAfter, LocalDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, systemOnlyZoneId(YearMonthMaxAfter::zoneId), YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<YearMonthMaxAfter, OffsetDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, YearMonthMaxAfter::zoneId,
                    YearMonth::from, OffsetDateTime::atZoneSameInstant, YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<YearMonthMaxAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearMonthMaxAfter::moment, YearMonthMaxAfter::duration, YearMonthMaxAfter::zoneId, YearMonth::from,
                    new MaxAfterValidator.ForYearMonth());
        }
    }
}
