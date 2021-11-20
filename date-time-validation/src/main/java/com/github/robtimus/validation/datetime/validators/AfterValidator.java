/*
 * AfterValidator.java
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
import com.github.robtimus.validation.datetime.After;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentValueValidator;

/**
 * Container class for constraint validators for {@link After}.
 *
 * @author Rob Spoor
 */
public final class AfterValidator {

    private AfterValidator() {
    }

    /**
     * A constraint validator for {@link After} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<After> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link After} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<After> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link After} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentValueValidator<After, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(After::moment, Instant::parse, Instant::now, Instant::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentValueValidator<After, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(After::moment, LocalDate::parse, LocalDate::now, LocalDate::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentValueValidator<After, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(After::moment, LocalDateTime::parse, LocalDateTime::now, LocalDateTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends MomentValueValidator<After, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(After::moment, LocalTime::parse, LocalTime::now, LocalTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends MomentValueValidator<After, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(After::moment, MonthDay::parse, MonthDay::now, MonthDay::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentValueValidator<After, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(After::moment, OffsetDateTime::parse, OffsetDateTime::now, OffsetDateTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends MomentValueValidator<After, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(After::moment, OffsetTime::parse, OffsetTime::now, OffsetTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends MomentValueValidator<After, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(After::moment, Year::parse, Year::now, Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentValueValidator<After, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(After::moment, YearMonth::parse, YearMonth::now, YearMonth::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentValueValidator<After, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(After::moment, ZonedDateTime::parse, ZonedDateTime::now, ZonedDateTime::isAfter);
        }
    }
}
