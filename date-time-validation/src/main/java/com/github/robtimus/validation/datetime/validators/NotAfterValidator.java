/*
 * NotAfterValidator.java
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
import com.github.robtimus.validation.datetime.NotAfter;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentValueValidator;

/**
 * Container class for constraint validators for {@link NotAfter}.
 *
 * @author Rob Spoor
 */
public final class NotAfterValidator {

    private NotAfterValidator() {
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<NotAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<NotAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentValueValidator<NotAfter, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(NotAfter::moment, Instant::parse, Instant::now, not(Instant::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentValueValidator<NotAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(NotAfter::moment, LocalDate::parse, LocalDate::now, not(LocalDate::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentValueValidator<NotAfter, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(NotAfter::moment, LocalDateTime::parse, LocalDateTime::now, not(LocalDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends MomentValueValidator<NotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(NotAfter::moment, LocalTime::parse, LocalTime::now, not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends MomentValueValidator<NotAfter, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(NotAfter::moment, MonthDay::parse, MonthDay::now, not(MonthDay::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentValueValidator<NotAfter, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(NotAfter::moment, OffsetDateTime::parse, OffsetDateTime::now, not(OffsetDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends MomentValueValidator<NotAfter, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(NotAfter::moment, OffsetTime::parse, OffsetTime::now, not(OffsetTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends MomentValueValidator<NotAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(NotAfter::moment, Year::parse, Year::now, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentValueValidator<NotAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(NotAfter::moment, YearMonth::parse, YearMonth::now, not(YearMonth::isAfter));
        }
    }

    /**
     * A constraint validator for {@link NotAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentValueValidator<NotAfter, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(NotAfter::moment, ZonedDateTime::parse, ZonedDateTime::now, not(ZonedDateTime::isAfter));
        }
    }
}
