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
import java.util.function.BiPredicate;
import com.github.robtimus.validation.time.MaxAfter;

/**
 * Container class for constraint validators for {@link MaxAfter}.
 *
 * @author Rob Spoor
 */
public final class MaxAfterValidator {

    private MaxAfterValidator() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateValidator<MaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(MaxAfter::moment, MaxAfter::duration, ISODuration::parse, ISODuration::plus, not(Instant::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarValidator<MaxAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(MaxAfter::moment, MaxAfter::duration, ISODuration::parse, ZonedDateTime::plus, not(ZonedDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorValidator<MaxAfter, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(momentExtractor(MaxAfter::moment, Instant::parse), Instant::now, MaxAfter::duration, ISODuration::parse, ISODuration::plus,
                    not(Instant::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorValidator<MaxAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(momentExtractor(MaxAfter::moment, LocalDate::parse), LocalDate::now, MaxAfter::duration, ISODuration::parse, LocalDate::plus,
                    not(LocalDate::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorValidator<MaxAfter, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(momentExtractor(MaxAfter::moment, LocalDateTime::parse), LocalDateTime::now, MaxAfter::duration, ISODuration::parse,
                    LocalDateTime::plus, not(LocalDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends AbstractTemporalAccessorValidator<MaxAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(momentExtractor(MaxAfter::moment, LocalTime::parse), LocalTime::now, MaxAfter::duration, ISODuration::parse, LocalTime::plus,
                    not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorValidator<MaxAfter, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(momentExtractor(MaxAfter::moment, OffsetDateTime::parse), OffsetDateTime::now, MaxAfter::duration, ISODuration::parse,
                    OffsetDateTime::plus, not(OffsetDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends AbstractTemporalAccessorValidator<MaxAfter, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(momentExtractor(MaxAfter::moment, OffsetTime::parse), OffsetTime::now, MaxAfter::duration, ISODuration::parse, OffsetTime::plus,
                    not(OffsetTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends AbstractTemporalAccessorValidator<MaxAfter, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(momentExtractor(MaxAfter::moment, Year::parse), Year::now, MaxAfter::duration, ISODuration::parse, Year::plus, not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorValidator<MaxAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(momentExtractor(MaxAfter::moment, YearMonth::parse), YearMonth::now, MaxAfter::duration, ISODuration::parse, YearMonth::plus,
                    not(YearMonth::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorValidator<MaxAfter, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(momentExtractor(MaxAfter::moment, ZonedDateTime::parse), ZonedDateTime::now, MaxAfter::duration, ISODuration::parse,
                    ZonedDateTime::plus, not(ZonedDateTime::isAfter));
        }
    }

    private static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }
}
