/*
 * MonthNotInValidator.java
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
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.PartValidator;
import com.github.robtimus.validation.month.MonthNotIn;

/**
 * Container class for constraint validators for {@link MonthNotIn}.
 *
 * @author Rob Spoor
 */
public final class MonthNotInValidator {

    private MonthNotInValidator() {
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MonthNotIn> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MonthNotIn> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<MonthNotIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MonthNotIn::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<MonthNotIn, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MonthNotIn::zoneId, LocalDate::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<MonthNotIn, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MonthNotIn::zoneId, LocalDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends PartValidator.WithoutZoneId<MonthNotIn, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(MonthNotIn::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(MonthNotIn::message,
                    "{com.github.robtimus.validation.month.MonthNotIn.message}",
                    "{com.github.robtimus.validation.month.MonthNotIn.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends PartValidator.WithoutZoneId<MonthNotIn, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(MonthNotIn::zoneId, MonthDay::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<MonthNotIn, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MonthNotIn::zoneId, OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends PartValidator.WithoutZoneId<MonthNotIn, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MonthNotIn::zoneId, YearMonth::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthNotIn} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<MonthNotIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MonthNotIn::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    private static Function<MonthNotIn, BiPredicate<Month, ClockProvider>> predicate() {
        return annotation -> {
            Set<Month> disallowedValues = asSet(annotation.value());
            return (value, context) -> !disallowedValues.contains(value);
        };
    }

    private static Set<Month> asSet(Month[] values) {
        return values.length == 0 ? Collections.emptySet() : EnumSet.of(values[0], values);
    }
}
