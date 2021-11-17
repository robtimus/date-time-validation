/*
 * YearMonthMinBeforeValidator.java
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
import com.github.robtimus.validation.datetime.validators.MinBeforeValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorPartValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimePartValidator;
import com.github.robtimus.validation.datetime.yearmonth.YearMonthMinBefore;

/**
 * Container class for constraint validators for {@link YearMonthMinBefore}.
 *
 * @author Rob Spoor
 */
public final class YearMonthMinBeforeValidator {

    private YearMonthMinBeforeValidator() {
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DatePartValidator<YearMonthMinBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, nonProvidedZoneId(YearMonthMinBefore::zoneId), YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarPartValidator<YearMonthMinBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, YearMonthMinBefore::zoneId, YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantPartValidator<YearMonthMinBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, nonProvidedZoneId(YearMonthMinBefore::zoneId), YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorPartValidator<YearMonthMinBefore, LocalDate, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, systemOnlyZoneId(YearMonthMinBefore::zoneId), YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorPartValidator<YearMonthMinBefore, LocalDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, systemOnlyZoneId(YearMonthMinBefore::zoneId), YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorPartValidator<YearMonthMinBefore, OffsetDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, YearMonthMinBefore::zoneId,
                    YearMonth::from, OffsetDateTime::atZoneSameInstant, YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }

    /**
     * A constraint validator for {@link YearMonthMinBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimePartValidator<YearMonthMinBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearMonthMinBefore::moment, YearMonthMinBefore::duration, YearMonthMinBefore::zoneId, YearMonth::from,
                    new MinBeforeValidator.ForYearMonth());
        }
    }
}
