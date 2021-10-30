/*
 * MonthIsValidator.java
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
import com.github.robtimus.validation.time.MonthIs;

/**
 * Container class for constraint validators for {@link MonthIs}.
 *
 * @author Rob Spoor
 */
public final class MonthIsValidator {

    private MonthIsValidator() {
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateMonthValidator<MonthIs> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarMonthValidator<MonthIs> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorMonthValidator<MonthIs, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, DateTimeValidator::toMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorMonthValidator<MonthIs, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorMonthValidator<MonthIs, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends AbstractTemporalAccessorMonthValidator<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.MonthIs.message}", MonthIs::message,
                    "{com.github.robtimus.validation.time.MonthIs.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends AbstractTemporalAccessorMonthValidator<MonthIs, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorMonthValidator<MonthIs, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, DateTimeValidator::toMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorMonthValidator<MonthIs, YearMonth> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorMonthValidator<MonthIs, ZonedDateTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, DateTimeValidator::toMonth);
        }
    }

    static <A extends Annotation, E extends Enum<E>> Function<A, Set<E>> allowedMonths(Function<A, E> valueExtractor) {
        return constraint -> EnumSet.of(valueExtractor.apply(constraint));
    }
}
