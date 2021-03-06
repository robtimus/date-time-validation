/*
 * MaxAfterValidator.java
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
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.datetime.MaxAfter;
import com.github.robtimus.validation.datetime.base.BaseValidator;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.MomentValueValidator;

/**
 * Container class for constraint validators for {@link MaxAfter}.
 *
 * @author Rob Spoor
 */
public final class MaxAfterValidator {

    private MaxAfterValidator() {
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends MomentValueValidator<MaxAfter, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MaxAfter::moment, Instant::parse, Instant::now, MaxAfter::duration, BaseValidator::plus, not(Instant::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends MomentValueValidator<MaxAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MaxAfter::moment, LocalDate::parse, LocalDate::now, MaxAfter::duration, LocalDate::plus, not(LocalDate::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends MomentValueValidator<MaxAfter, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MaxAfter::moment, LocalDateTime::parse, LocalDateTime::now, MaxAfter::duration, LocalDateTime::plus, not(LocalDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends MomentValueValidator<MaxAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(MaxAfter::moment, LocalTime::parse, LocalTime::now, MaxAfter::duration, LocalTime::plus, not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends MomentValueValidator<MaxAfter, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MaxAfter::moment, OffsetDateTime::parse, OffsetDateTime::now, MaxAfter::duration, OffsetDateTime::plus,
                    not(OffsetDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends MomentValueValidator<MaxAfter, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(MaxAfter::moment, OffsetTime::parse, OffsetTime::now, MaxAfter::duration, OffsetTime::plus, not(OffsetTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends MomentValueValidator<MaxAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(MaxAfter::moment, Year::parse, Year::now, MaxAfter::duration, Year::plus, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends MomentValueValidator<MaxAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MaxAfter::moment, YearMonth::parse, YearMonth::now, MaxAfter::duration, YearMonth::plus, not(YearMonth::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends MomentValueValidator<MaxAfter, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MaxAfter::moment, ZonedDateTime::parse, ZonedDateTime::now, MaxAfter::duration, ZonedDateTime::plus, not(ZonedDateTime::isAfter));
        }
    }
}
