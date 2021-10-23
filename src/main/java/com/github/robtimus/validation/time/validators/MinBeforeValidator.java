/*
 * MinBeforeValidator.java
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
import com.github.robtimus.validation.time.MinBefore;

/**
 * Container class for constraint validators for {@link MinBefore}.
 *
 * @author Rob Spoor
 */
public final class MinBeforeValidator {

    private MinBeforeValidator() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateValidator<MinBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(MinBefore::moment, MinBefore::duration, ISODuration::parse, ISODuration::minus, not(Instant::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarValidator<MinBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(MinBefore::moment, MinBefore::duration, ISODuration::parse, ZonedDateTime::minus, not(ZonedDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorValidator<MinBefore, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(momentExtractor(MinBefore::moment, Instant::parse), Instant::now, MinBefore::duration, ISODuration::parse, ISODuration::minus,
                    not(Instant::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorValidator<MinBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(momentExtractor(MinBefore::moment, LocalDate::parse), LocalDate::now, MinBefore::duration, ISODuration::parse, LocalDate::minus,
                    not(LocalDate::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorValidator<MinBefore, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(momentExtractor(MinBefore::moment, LocalDateTime::parse), LocalDateTime::now, MinBefore::duration, ISODuration::parse,
                    LocalDateTime::minus, not(LocalDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends AbstractTemporalAccessorValidator<MinBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(momentExtractor(MinBefore::moment, LocalTime::parse), LocalTime::now, MinBefore::duration, ISODuration::parse, LocalTime::minus,
                    not(LocalTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorValidator<MinBefore, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(momentExtractor(MinBefore::moment, OffsetDateTime::parse), OffsetDateTime::now, MinBefore::duration, ISODuration::parse,
                    OffsetDateTime::minus, not(OffsetDateTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends AbstractTemporalAccessorValidator<MinBefore, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(momentExtractor(MinBefore::moment, OffsetTime::parse), OffsetTime::now, MinBefore::duration, ISODuration::parse, OffsetTime::minus,
                    not(OffsetTime::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends AbstractTemporalAccessorValidator<MinBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(momentExtractor(MinBefore::moment, Year::parse), Year::now, MinBefore::duration, ISODuration::parse, Year::minus,
                    not(Year::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorValidator<MinBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(momentExtractor(MinBefore::moment, YearMonth::parse), YearMonth::now, MinBefore::duration, ISODuration::parse, YearMonth::minus,
                    not(YearMonth::isAfter));
        }
    }

    /**
     * A constraint validator for {@link MinBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorValidator<MinBefore, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(momentExtractor(MinBefore::moment, ZonedDateTime::parse), ZonedDateTime::now, MinBefore::duration, ISODuration::parse,
                    ZonedDateTime::minus, not(ZonedDateTime::isAfter));
        }
    }

    private static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }
}
