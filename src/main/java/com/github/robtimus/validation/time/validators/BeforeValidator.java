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
import com.github.robtimus.validation.time.Before;

/**
 * Container class for constraint validators for {@link Before}.
 *
 * @author Rob Spoor
 */
public final class BeforeValidator {

    private BeforeValidator() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    /**
     * A constraint validator for {@link Before} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateValidator<Before> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(Before::moment, Instant::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarValidator<Before> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(Before::moment, ZonedDateTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorValidator<Before, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(momentExtractor(Before::moment, Instant::parse), Instant::now, Instant::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorValidator<Before, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(momentExtractor(Before::moment, LocalDate::parse), LocalDate::now, LocalDate::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorValidator<Before, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(momentExtractor(Before::moment, LocalDateTime::parse), LocalDateTime::now, LocalDateTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends AbstractTemporalAccessorValidator<Before, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(momentExtractor(Before::moment, LocalTime::parse), LocalTime::now, LocalTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends AbstractTemporalAccessorValidator<Before, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(momentExtractor(Before::moment, MonthDay::parse), MonthDay::now, MonthDay::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorValidator<Before, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(momentExtractor(Before::moment, OffsetDateTime::parse), OffsetDateTime::now, OffsetDateTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends AbstractTemporalAccessorValidator<Before, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(momentExtractor(Before::moment, OffsetTime::parse), OffsetTime::now, OffsetTime::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends AbstractTemporalAccessorValidator<Before, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(momentExtractor(Before::moment, Year::parse), Year::now, Year::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorValidator<Before, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(momentExtractor(Before::moment, YearMonth::parse), YearMonth::now, YearMonth::isBefore);
        }
    }

    /**
     * A constraint validator for {@link Before} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorValidator<Before, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(momentExtractor(Before::moment, ZonedDateTime::parse), ZonedDateTime::now, ZonedDateTime::isBefore);
        }
    }
}
