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
import jakarta.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.PartValidator;
import com.github.robtimus.validation.month.MonthIs;

/**
 * Container class for constraint validators for {@link MonthIs}.
 *
 * @author Rob Spoor
 */
public final class MonthIsValidator {

    private static final Function<MonthIs, BiPredicate<Month, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> {
        Month allowedValue = annotation.value();
        return (value, provider) -> value == allowedValue;
    };

    private MonthIsValidator() {
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MonthIs> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MonthIs> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MonthIs::zoneId, ZonedDateTime::getMonth, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<MonthIs, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MonthIs::zoneId, LocalDate::getMonth, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<MonthIs, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MonthIs::zoneId, LocalDateTime::getMonth, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends PartValidator.WithoutZoneId<MonthIs, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(MonthIs::zoneId, Function.identity(), PREDICATE_EXTRACTOR);
            useReplacementMessageTemplate(MonthIs::message,
                    "{com.github.robtimus.validation.month.MonthIs.message}",
                    "{com.github.robtimus.validation.month.MonthIs.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends PartValidator.WithoutZoneId<MonthIs, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(MonthIs::zoneId, MonthDay::getMonth, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<MonthIs, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MonthIs::zoneId, OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends PartValidator.WithoutZoneId<MonthIs, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MonthIs::zoneId, YearMonth::getMonth, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MonthIs::zoneId, ZonedDateTime::getMonth, PREDICATE_EXTRACTOR);
        }
    }
}
