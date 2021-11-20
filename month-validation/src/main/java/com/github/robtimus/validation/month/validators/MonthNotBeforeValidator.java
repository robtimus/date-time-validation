/*
 * MonthNotBeforeValidator.java
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

package com.github.robtimus.validation.month.validators;

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
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.PartValidator;
import com.github.robtimus.validation.month.MonthNotBefore;

/**
 * Container class for constraint validators for {@link MonthNotBefore}.
 *
 * @author Rob Spoor
 */
public final class MonthNotBeforeValidator {

    private MonthNotBeforeValidator() {
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MonthNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MonthNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<MonthNotBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MonthNotBefore::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<MonthNotBefore, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MonthNotBefore::zoneId, LocalDate::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<MonthNotBefore, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MonthNotBefore::zoneId, LocalDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends PartValidator.WithoutZoneId<MonthNotBefore, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(MonthNotBefore::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(MonthNotBefore::message,
                    "{com.github.robtimus.validation.month.MonthNotBefore.message}",
                    "{com.github.robtimus.validation.month.MonthNotBefore.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends PartValidator.WithoutZoneId<MonthNotBefore, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(MonthNotBefore::zoneId, MonthDay::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<MonthNotBefore, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MonthNotBefore::zoneId, OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends PartValidator.WithoutZoneId<MonthNotBefore, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MonthNotBefore::zoneId, YearMonth::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<MonthNotBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MonthNotBefore::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    private static Function<MonthNotBefore, BiPredicate<Month, ClockProvider>> predicate() {
        return annotation -> {
            Month boundary = annotation.value();
            return (value, context) -> value.compareTo(boundary) >= 0;
        };
    }
}
