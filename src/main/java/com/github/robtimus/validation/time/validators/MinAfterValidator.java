/*
 * MinAfterValidator.java
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
import com.github.robtimus.validation.time.MinAfter;

/**
 * Container class for constraint validators for {@link MinAfter}.
 *
 * @author Rob Spoor
 */
public final class MinAfterValidator {

    private MinAfterValidator() {
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MinAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(MinAfter::moment, MinAfter::duration, ISODuration::plus, not(Instant::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MinAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(MinAfter::moment, MinAfter::duration, ZonedDateTime::plus, not(ZonedDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends TemporalAccessorValidator<MinAfter, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MinAfter::moment, Instant::parse, Instant::now, MinAfter::duration, ISODuration::plus, not(Instant::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorValidator<MinAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MinAfter::moment, LocalDate::parse, LocalDate::now, MinAfter::duration, LocalDate::plus, not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorValidator<MinAfter, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MinAfter::moment, LocalDateTime::parse, LocalDateTime::now, MinAfter::duration, LocalDateTime::plus, not(LocalDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends TemporalAccessorValidator<MinAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(MinAfter::moment, LocalTime::parse, LocalTime::now, MinAfter::duration, LocalTime::plus, not(LocalTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorValidator<MinAfter, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MinAfter::moment, OffsetDateTime::parse, OffsetDateTime::now, MinAfter::duration, OffsetDateTime::plus,
                    not(OffsetDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends TemporalAccessorValidator<MinAfter, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(MinAfter::moment, OffsetTime::parse, OffsetTime::now, MinAfter::duration, OffsetTime::plus, not(OffsetTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends TemporalAccessorValidator<MinAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(MinAfter::moment, Year::parse, Year::now, MinAfter::duration, Year::plus, not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends TemporalAccessorValidator<MinAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MinAfter::moment, YearMonth::parse, YearMonth::now, MinAfter::duration, YearMonth::plus, not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link MinAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends TemporalAccessorValidator<MinAfter, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MinAfter::moment, ZonedDateTime::parse, ZonedDateTime::now, MinAfter::duration, ZonedDateTime::plus, not(ZonedDateTime::isBefore));
        }
    }
}
