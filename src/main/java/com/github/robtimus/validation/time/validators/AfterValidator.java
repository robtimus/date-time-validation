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

package com.github.robtimus.validation.time.validators;

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
import com.github.robtimus.validation.time.After;

/**
 * Container class for constraint validators for {@link After}.
 *
 * @author Rob Spoor
 */
public final class AfterValidator {

    private AfterValidator() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    /**
     * A constraint validator for {@link After} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateValidator<After> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(After::moment, Instant::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarValidator<After> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(After::moment, ZonedDateTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorValidator<After, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(momentExtractor(After::moment, Instant::parse), Instant::now, Instant::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorValidator<After, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(momentExtractor(After::moment, LocalDate::parse), LocalDate::now, LocalDate::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorValidator<After, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(momentExtractor(After::moment, LocalDateTime::parse), LocalDateTime::now, LocalDateTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends AbstractTemporalAccessorValidator<After, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(momentExtractor(After::moment, LocalTime::parse), LocalTime::now, LocalTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends AbstractTemporalAccessorValidator<After, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(momentExtractor(After::moment, MonthDay::parse), MonthDay::now, MonthDay::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorValidator<After, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(momentExtractor(After::moment, OffsetDateTime::parse), OffsetDateTime::now, OffsetDateTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends AbstractTemporalAccessorValidator<After, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(momentExtractor(After::moment, OffsetTime::parse), OffsetTime::now, OffsetTime::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends AbstractTemporalAccessorValidator<After, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(momentExtractor(After::moment, Year::parse), Year::now, Year::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorValidator<After, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(momentExtractor(After::moment, YearMonth::parse), YearMonth::now, YearMonth::isAfter);
        }
    }

    /**
     * A constraint validator for {@link After} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorValidator<After, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(momentExtractor(After::moment, ZonedDateTime::parse), ZonedDateTime::now, ZonedDateTime::isAfter);
        }
    }
}
