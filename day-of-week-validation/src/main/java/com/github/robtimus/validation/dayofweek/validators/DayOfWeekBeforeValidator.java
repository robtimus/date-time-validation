/*
 * DayOfWeekBeforeValidator.java
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

package com.github.robtimus.validation.dayofweek.validators;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.core.CalendarValidator;
import com.github.robtimus.validation.datetime.core.DateValidator;
import com.github.robtimus.validation.datetime.core.PartValidator;
import com.github.robtimus.validation.dayofweek.DayOfWeekBefore;

/**
 * Container class for constraint validators for {@link DayOfWeekBefore}.
 *
 * @author Rob Spoor
 */
public final class DayOfWeekBeforeValidator {

    private DayOfWeekBeforeValidator() {
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DayOfWeekBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DayOfWeekBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends PartValidator.WithoutZoneId<DayOfWeekBefore, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(DayOfWeekBefore::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(DayOfWeekBefore::message,
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekBefore.message}",
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekBefore.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<DayOfWeekBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DayOfWeekBefore::zoneId, ZonedDateTime::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<DayOfWeekBefore, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(DayOfWeekBefore::zoneId, LocalDate::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<DayOfWeekBefore, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DayOfWeekBefore::zoneId, LocalDateTime::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<DayOfWeekBefore, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DayOfWeekBefore::zoneId, OffsetDateTime::getDayOfWeek, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<DayOfWeekBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DayOfWeekBefore::zoneId, ZonedDateTime::getDayOfWeek, predicate());
        }
    }

    private static Function<DayOfWeekBefore, BiPredicate<DayOfWeek, ClockProvider>> predicate() {
        return annotation -> {
            DayOfWeek boundary = annotation.value();
            return (value, context) -> value.compareTo(boundary) < 0;
        };
    }
}
