/*
 * MonthNotAfterValidator.java
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

package com.github.robtimus.validation.datetime.month.validators;

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
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.core.CalendarValidator;
import com.github.robtimus.validation.datetime.core.DateValidator;
import com.github.robtimus.validation.datetime.core.PartValidator;
import com.github.robtimus.validation.datetime.month.MonthNotAfter;

/**
 * Container class for constraint validators for {@link MonthNotAfter}.
 *
 * @author Rob Spoor
 */
public final class MonthNotAfterValidator {

    private MonthNotAfterValidator() {
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MonthNotAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MonthNotAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<MonthNotAfter, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MonthNotAfter::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<MonthNotAfter, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MonthNotAfter::zoneId, LocalDate::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<MonthNotAfter, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MonthNotAfter::zoneId, LocalDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends PartValidator.WithoutZoneId<MonthNotAfter, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(MonthNotAfter::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(MonthNotAfter::message,
                    "{com.github.robtimus.validation.datetime.month.MonthNotAfter.message}",
                    "{com.github.robtimus.validation.datetime.month.MonthNotAfter.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends PartValidator.WithoutZoneId<MonthNotAfter, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(MonthNotAfter::zoneId, MonthDay::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<MonthNotAfter, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MonthNotAfter::zoneId, OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends PartValidator.WithoutZoneId<MonthNotAfter, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MonthNotAfter::zoneId, YearMonth::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<MonthNotAfter, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MonthNotAfter::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month> valueExtractor) {
        return constraint -> notAfter(valueExtractor.apply(constraint));
    }

    private static Set<Month> notAfter(Month value) {
        return EnumSet.range(Month.JANUARY, value);
    }

    private static Function<MonthNotAfter, BiPredicate<Month, ClockProvider>> predicate() {
        return annotation -> {
            Month boundary = annotation.value();
            return (value, context) -> value.compareTo(boundary) <= 0;
        };
    }
}
