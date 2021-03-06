/*
 * BeforeValidator.java
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

package com.github.robtimus.validation.datetime.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.Before;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentValueValidator;

/**
 * Container class for constraint validators for {@link Before}.
 *
 * @author Rob Spoor
 */
public final class BeforeValidator {

    private BeforeValidator() {
    }

    /**
     * A constraint validator for {@link Before} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<Before> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<Before> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentValueValidator<Before, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(Before::moment, Instant::parse, Instant::now, Instant::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentValueValidator<Before, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(Before::moment, LocalDate::parse, LocalDate::now, LocalDate::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentValueValidator<Before, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(Before::moment, LocalDateTime::parse, LocalDateTime::now, LocalDateTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends MomentValueValidator<Before, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(Before::moment, LocalTime::parse, LocalTime::now, LocalTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends MomentValueValidator<Before, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(Before::moment, MonthDay::parse, MonthDay::now, MonthDay::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentValueValidator<Before, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(Before::moment, OffsetDateTime::parse, OffsetDateTime::now, OffsetDateTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends MomentValueValidator<Before, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(Before::moment, OffsetTime::parse, OffsetTime::now, OffsetTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends MomentValueValidator<Before, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(Before::moment, Year::parse, Year::now, Year::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentValueValidator<Before, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(Before::moment, YearMonth::parse, YearMonth::now, YearMonth::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentValueValidator<Before, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(Before::moment, ZonedDateTime::parse, ZonedDateTime::now, ZonedDateTime::isBefore);
        }
    }
}
