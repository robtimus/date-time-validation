/*
 * MaxBeforeValidator.java
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
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.time.MaxBefore;

/**
 * Container class for constraint validators for {@link MaxBefore}.
 *
 * @author Rob Spoor
 */
public final class MaxBeforeValidator {

    private MaxBeforeValidator() {
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateValidator<MaxBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(MaxBefore::moment, MaxBefore::duration, ISODuration::minus, not(Instant::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarValidator<MaxBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(MaxBefore::moment, MaxBefore::duration, ZonedDateTime::minus, not(ZonedDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorValidator<MaxBefore, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MaxBefore::moment, Instant::parse, Instant::now, MaxBefore::duration, ISODuration::minus, not(Instant::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorValidator<MaxBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MaxBefore::moment, LocalDate::parse, LocalDate::now, MaxBefore::duration, LocalDate::minus, not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorValidator<MaxBefore, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MaxBefore::moment, LocalDateTime::parse, LocalDateTime::now, MaxBefore::duration, LocalDateTime::minus,
                    not(LocalDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends AbstractTemporalAccessorValidator<MaxBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(MaxBefore::moment, LocalTime::parse, LocalTime::now, MaxBefore::duration, LocalTime::minus, not(LocalTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorValidator<MaxBefore, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MaxBefore::moment, OffsetDateTime::parse, OffsetDateTime::now, MaxBefore::duration, OffsetDateTime::minus,
                    not(OffsetDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends AbstractTemporalAccessorValidator<MaxBefore, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(MaxBefore::moment, OffsetTime::parse, OffsetTime::now, MaxBefore::duration, OffsetTime::minus, not(OffsetTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends AbstractTemporalAccessorValidator<MaxBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(MaxBefore::moment, Year::parse, Year::now, MaxBefore::duration, Year::minus, not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorValidator<MaxBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MaxBefore::moment, YearMonth::parse, YearMonth::now, MaxBefore::duration, YearMonth::minus, not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MaxBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorValidator<MaxBefore, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MaxBefore::moment, ZonedDateTime::parse, ZonedDateTime::now, MaxBefore::duration, ZonedDateTime::minus,
                    not(ZonedDateTime::isBefore));
        }
    }
}
