/*
 * NotBeforeValidator.java
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
import java.util.function.BiPredicate;
import com.github.robtimus.validation.time.NotBefore;

/**
 * Container class for constraint validators for {@link NotBefore}.
 *
 * @author Rob Spoor
 */
public final class NotBeforeValidator {

    private NotBeforeValidator() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateValidator<NotBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(NotBefore::moment, not(Instant::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarValidator<NotBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(NotBefore::moment, not(ZonedDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorValidator<NotBefore, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(momentExtractor(NotBefore::moment, Instant::parse), Instant::now, not(Instant::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorValidator<NotBefore, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(momentExtractor(NotBefore::moment, LocalDate::parse), LocalDate::now, not(LocalDate::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorValidator<NotBefore, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(momentExtractor(NotBefore::moment, LocalDateTime::parse), LocalDateTime::now, not(LocalDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends AbstractTemporalAccessorValidator<NotBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(momentExtractor(NotBefore::moment, LocalTime::parse), LocalTime::now, not(LocalTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends AbstractTemporalAccessorValidator<NotBefore, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(momentExtractor(NotBefore::moment, MonthDay::parse), MonthDay::now, not(MonthDay::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorValidator<NotBefore, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(momentExtractor(NotBefore::moment, OffsetDateTime::parse), OffsetDateTime::now, not(OffsetDateTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends AbstractTemporalAccessorValidator<NotBefore, OffsetTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(momentExtractor(NotBefore::moment, OffsetTime::parse), OffsetTime::now, not(OffsetTime::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link Year}.
     *
     * @author Rob Spoor
     */
    public static class ForYear extends AbstractTemporalAccessorValidator<NotBefore, Year> {

        /**
         * Creates a new validator.
         */
        public ForYear() {
            super(momentExtractor(NotBefore::moment, Year::parse), Year::now, not(Year::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorValidator<NotBefore, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(momentExtractor(NotBefore::moment, YearMonth::parse), YearMonth::now, not(YearMonth::isBefore));
        }
    }

    /**
     * A constraint validator for {@link NotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorValidator<NotBefore, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(momentExtractor(NotBefore::moment, ZonedDateTime::parse), ZonedDateTime::now, not(ZonedDateTime::isBefore));
        }
    }

    private static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }
}
