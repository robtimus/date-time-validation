/*
 * MonthInValidator.java
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
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;
import com.github.robtimus.validation.datetime.core.CalendarValidator;
import com.github.robtimus.validation.datetime.core.DateValidator;
import com.github.robtimus.validation.datetime.core.PartValidator;
import com.github.robtimus.validation.datetime.month.MonthIn;

/**
 * Container class for constraint validators for {@link MonthIn}.
 *
 * @author Rob Spoor
 */
public final class MonthInValidator {

    private MonthInValidator() {
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MonthIn> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MonthIn> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<MonthIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(MonthIn::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<MonthIn, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(MonthIn::zoneId, LocalDate::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<MonthIn, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(MonthIn::zoneId, LocalDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends PartValidator.WithoutZoneId<MonthIn, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(MonthIn::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(MonthIn::message,
                    "{com.github.robtimus.validation.datetime.month.MonthIn.message}",
                    "{com.github.robtimus.validation.datetime.month.MonthIn.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends PartValidator.WithoutZoneId<MonthIn, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(MonthIn::zoneId, MonthDay::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<MonthIn, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(MonthIn::zoneId, OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends PartValidator.WithoutZoneId<MonthIn, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(MonthIn::zoneId, YearMonth::getMonth, predicate());
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<MonthIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(MonthIn::zoneId, ZonedDateTime::getMonth, predicate());
        }
    }

    private static Function<MonthIn, BiPredicate<Month, ConstraintValidatorContext>> predicate() {
        return annotation -> {
            Set<Month> allowedValues = asSet(annotation.value());
            return (value, context) -> allowedValues.contains(value);
        };
    }

    private static Set<Month> asSet(Month[] values) {
        return values.length == 0 ? Collections.emptySet() : EnumSet.of(values[0], values);
    }
}
