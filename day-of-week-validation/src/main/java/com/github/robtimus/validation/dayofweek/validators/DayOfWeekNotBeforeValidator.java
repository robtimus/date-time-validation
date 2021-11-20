/*
 * DayOfWeekNotBeforeValidator.java
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
import com.github.robtimus.validation.dayofweek.DayOfWeekNotBefore;

/**
 * Container class for constraint validators for {@link DayOfWeekNotBefore}.
 *
 * @author Rob Spoor
 */
public final class DayOfWeekNotBeforeValidator {

    private DayOfWeekNotBeforeValidator() {
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DayOfWeekNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DayOfWeekNotBefore> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends PartValidator.WithoutZoneId<DayOfWeekNotBefore, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(DayOfWeekNotBefore::zoneId, Function.identity(), predicate());
            useReplacementMessageTemplate(DayOfWeekNotBefore::message,
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekNotBefore.message}",
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekNotBefore.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<DayOfWeekNotBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DayOfWeekNotBefore::zoneId, ZonedDateTime::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<DayOfWeekNotBefore, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(DayOfWeekNotBefore::zoneId, LocalDate::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<DayOfWeekNotBefore, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DayOfWeekNotBefore::zoneId, LocalDateTime::getDayOfWeek, predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<DayOfWeekNotBefore, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DayOfWeekNotBefore::zoneId, OffsetDateTime::getDayOfWeek, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getDayOfWeek,
                    predicate());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekNotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<DayOfWeekNotBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DayOfWeekNotBefore::zoneId, ZonedDateTime::getDayOfWeek, predicate());
        }
    }

    private static Function<DayOfWeekNotBefore, BiPredicate<DayOfWeek, ClockProvider>> predicate() {
        return annotation -> {
            DayOfWeek boundary = annotation.value();
            return (value, context) -> value.compareTo(boundary) >= 0;
        };
    }
}
