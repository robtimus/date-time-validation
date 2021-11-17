/*
 * YearMonthBeforeValidator.java
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
import com.github.robtimus.validation.datetime.validators.BeforeValidator;
import com.github.robtimus.validation.datetime.validators.CalendarPartValidator;
import com.github.robtimus.validation.datetime.validators.DatePartValidator;
import com.github.robtimus.validation.datetime.validators.InstantPartValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;
import com.github.robtimus.validation.datetime.yearmonth.YearMonthBefore;

/**
 * Container class for constraint validators for {@link YearMonthBefore}.
 *
 * @author Rob Spoor
 */
public final class YearMonthBeforeValidator {

    private YearMonthBeforeValidator() {
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<YearMonthBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(YearMonthBefore::moment, nonProvidedZoneId(YearMonthBefore::zoneId), YearMonth::from, new BeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<YearMonthBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(YearMonthBefore::moment, YearMonthBefore::zoneId, YearMonth::from, new BeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<YearMonthBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearMonthBefore::moment, nonProvidedZoneId(YearMonthBefore::zoneId), YearMonth::from, new BeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorPartValidator<YearMonthBefore, LocalDate, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearMonthBefore::moment, systemOnlyZoneId(YearMonthBefore::zoneId), YearMonth::from, new BeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<YearMonthBefore, LocalDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearMonthBefore::moment, systemOnlyZoneId(YearMonthBefore::zoneId), YearMonth::from, new BeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<YearMonthBefore, OffsetDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearMonthBefore::moment, YearMonthBefore::zoneId,
                    YearMonth::from, OffsetDateTime::atZoneSameInstant, YearMonth::from,
                    new BeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<YearMonthBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearMonthBefore::moment, YearMonthBefore::zoneId, YearMonth::from, new BeforeValidator.ForYearMonth());
        }
    }
}
