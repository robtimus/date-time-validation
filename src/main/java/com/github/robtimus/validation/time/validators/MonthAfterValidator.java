/*
 * MonthAfterValidator.java
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

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import com.github.robtimus.validation.time.MonthAfter;

/**
 * Container class for constraint validators for {@link MonthAfter}.
 *
 * @author Rob Spoor
 */
public final class MonthAfterValidator {

    private MonthAfterValidator() {
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateMonthValidator<MonthAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarMonthValidator<MonthAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorMonthValidator<MonthAfter, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, DateTimeValidator::toMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorMonthValidator<MonthAfter, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorMonthValidator<MonthAfter, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends AbstractTemporalAccessorMonthValidator<MonthAfter, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.MonthAfter.message}", MonthAfter::message,
                    "{com.github.robtimus.validation.time.MonthAfter.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends AbstractTemporalAccessorMonthValidator<MonthAfter, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorMonthValidator<MonthAfter, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, DateTimeValidator::toMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorMonthValidator<MonthAfter, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorMonthValidator<MonthAfter, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthAfter::value), MonthAfter::zoneId, DateTimeValidator::toMonth);
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month> valueExtractor) {
        return constraint -> after(valueExtractor.apply(constraint));
    }

    private static Set<Month> after(Month value) {
        return EnumSet.complementOf(EnumSet.range(Month.JANUARY, value));
    }
}
