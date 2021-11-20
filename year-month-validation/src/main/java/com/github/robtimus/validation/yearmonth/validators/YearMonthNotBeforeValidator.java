/*
 * YearMonthNotBeforeValidator.java
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

package com.github.robtimus.validation.yearmonth.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentPartValidator;
import com.github.robtimus.validation.yearmonth.YearMonthNotBefore;

/**
 * Container class for constraint validators for {@link YearMonthNotBefore}.
 *
 * @author Rob Spoor
 */
public final class YearMonthNotBeforeValidator {

    private YearMonthNotBeforeValidator() {
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<YearMonthNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<YearMonthNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentPartValidator.ForInstant<YearMonthNotBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(YearMonthNotBefore::moment, YearMonth::parse, YearMonth::now, YearMonthNotBefore::zoneId, YearMonth::from,
                    not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentPartValidator.WithoutZoneId<YearMonthNotBefore, LocalDate, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(YearMonthNotBefore::moment, YearMonth::parse, YearMonth::now, YearMonthNotBefore::zoneId, YearMonth::from,
                    not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentPartValidator.WithoutZoneId<YearMonthNotBefore, LocalDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(YearMonthNotBefore::moment, YearMonth::parse, YearMonth::now, YearMonthNotBefore::zoneId, YearMonth::from,
                    not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentPartValidator<YearMonthNotBefore, OffsetDateTime, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(YearMonthNotBefore::moment, YearMonth::parse, YearMonth::now, YearMonthNotBefore::zoneId,
                    YearMonth::from, OffsetDateTime::atZoneSameInstant, YearMonth::from,
                    not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link YearMonthNotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentPartValidator.ForZonedDateTime<YearMonthNotBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(YearMonthNotBefore::moment, YearMonth::parse, YearMonth::now, YearMonthNotBefore::zoneId, YearMonth::from,
                    not(YearMonth::isBefore));
        }
    }
}
