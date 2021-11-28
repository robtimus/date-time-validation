/*
 * DayOfMonthIsValidator.java
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

package com.github.robtimus.validation.dayofmonth.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.FieldValidator;
import com.github.robtimus.validation.dayofmonth.DayOfMonthIs;

/**
 * Container class for constraint validators for {@link DayOfMonthIs}.
 *
 * @author Rob Spoor
 */
public final class DayOfMonthIsValidator {

    private static final Function<DayOfMonthIs, BiPredicate<Integer, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> {
        int allowedValue = annotation.value();
        return (value, context) -> value == allowedValue;
    };

    private DayOfMonthIsValidator() {
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DayOfMonthIs> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DayOfMonthIs> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends FieldValidator.ForInstant<DayOfMonthIs> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(ChronoField.DAY_OF_MONTH, DayOfMonthIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends FieldValidator.WithoutZoneId<DayOfMonthIs, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(ChronoField.DAY_OF_MONTH, DayOfMonthIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends FieldValidator.WithoutZoneId<DayOfMonthIs, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(ChronoField.DAY_OF_MONTH, DayOfMonthIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends FieldValidator.WithoutZoneId<DayOfMonthIs, MonthDay> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(ChronoField.DAY_OF_MONTH, DayOfMonthIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends FieldValidator<DayOfMonthIs, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(ChronoField.DAY_OF_MONTH, DayOfMonthIs::zoneId, OffsetDateTime::atZoneSameInstant, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfMonthIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends FieldValidator.ForZonedDateTime<DayOfMonthIs> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(ChronoField.DAY_OF_MONTH, DayOfMonthIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }
}
