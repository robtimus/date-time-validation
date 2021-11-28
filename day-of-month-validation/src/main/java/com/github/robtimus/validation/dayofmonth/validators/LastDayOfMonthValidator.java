/*
 * LastDayOfMonthValidator.java
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
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.PartValidator;
import com.github.robtimus.validation.dayofmonth.LastDayOfMonth;

/**
 * Container class for constraint validators for {@link LastDayOfMonth}.
 *
 * @author Rob Spoor
 */
public final class LastDayOfMonthValidator {

    private LastDayOfMonthValidator() {
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<LastDayOfMonth> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<LastDayOfMonth> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<LastDayOfMonth, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(LastDayOfMonth::zoneId, ZonedDateTime::toLocalDate, predicate());
        }
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<LastDayOfMonth, LocalDate, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(LastDayOfMonth::zoneId, Function.identity(), predicate());
        }
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<LastDayOfMonth, LocalDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(LastDayOfMonth::zoneId, LocalDateTime::toLocalDate, predicate());
        }
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<LastDayOfMonth, OffsetDateTime, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(LastDayOfMonth::zoneId, OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate, predicate());
        }
    }

    /**
     * A constraint validator for {@link LastDayOfMonth} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<LastDayOfMonth, LocalDate> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(LastDayOfMonth::zoneId, ZonedDateTime::toLocalDate, predicate());
        }
    }

    private static Function<LastDayOfMonth, BiPredicate<LocalDate, ClockProvider>> predicate() {
        return annotation -> (value, context) -> {
            boolean leapYear = Year.isLeap(value.getYear());
            int lastDayOfMonth = value.getMonth().length(leapYear);
            return value.getDayOfMonth() == lastDayOfMonth;
        };
    }
}
