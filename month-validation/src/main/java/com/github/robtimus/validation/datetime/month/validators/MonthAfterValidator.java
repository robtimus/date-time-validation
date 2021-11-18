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

package com.github.robtimus.validation.datetime.month.validators;

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
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;
import com.github.robtimus.validation.datetime.core.CalendarValidator;
import com.github.robtimus.validation.datetime.core.DateValidator;
import com.github.robtimus.validation.datetime.core.PartValidator;
import com.github.robtimus.validation.datetime.month.MonthAfter;

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
    public static class ForDate extends DateValidator<MonthAfter> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MonthAfter> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<MonthAfter, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MonthAfter::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<MonthAfter, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MonthAfter::zoneId, LocalDate::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<MonthAfter, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MonthAfter::zoneId, LocalDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends PartValidator.WithoutZoneId<MonthAfter, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(MonthAfter::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(MonthAfter::message,
                    "{com.github.robtimus.validation.datetime.month.MonthAfter.message}",
                    "{com.github.robtimus.validation.datetime.month.MonthAfter.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends PartValidator.WithoutZoneId<MonthAfter, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(MonthAfter::zoneId, MonthDay::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<MonthAfter, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MonthAfter::zoneId, OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends PartValidator.WithoutZoneId<MonthAfter, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MonthAfter::zoneId, YearMonth::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<MonthAfter, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MonthAfter::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    private static Function<MonthAfter, BiPredicate<Month, ConstraintValidatorContext>> predicate() {
        return annotation -> {
            Month boundary = annotation.value();
            return (value, context) -> value.compareTo(boundary) > 0;
        };
    }
}
