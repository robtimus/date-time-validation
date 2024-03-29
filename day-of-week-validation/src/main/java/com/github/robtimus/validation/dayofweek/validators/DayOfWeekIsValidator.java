/*
 * DayOfWeekIsValidator.java
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
import jakarta.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.PartValidator;
import com.github.robtimus.validation.dayofweek.DayOfWeekIs;

/**
 * Container class for constraint validators for {@link DayOfWeekIs}.
 *
 * @author Rob Spoor
 */
public final class DayOfWeekIsValidator {

    private static final Function<DayOfWeekIs, BiPredicate<DayOfWeek, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> {
        DayOfWeek allowedValue = annotation.value();
        return (value, provider) -> value == allowedValue;
    };

    private DayOfWeekIsValidator() {
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DayOfWeekIs> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DayOfWeekIs> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends PartValidator.WithoutZoneId<DayOfWeekIs, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(DayOfWeekIs::zoneId, Function.identity(), PREDICATE_EXTRACTOR);
            useReplacementMessageTemplate(DayOfWeekIs::message,
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekIs.message}",
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekIs.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<DayOfWeekIs, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DayOfWeekIs::zoneId, ZonedDateTime::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<DayOfWeekIs, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(DayOfWeekIs::zoneId, LocalDate::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<DayOfWeekIs, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DayOfWeekIs::zoneId, LocalDateTime::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<DayOfWeekIs, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DayOfWeekIs::zoneId, OffsetDateTime::getDayOfWeek, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getDayOfWeek,
                    PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<DayOfWeekIs, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DayOfWeekIs::zoneId, ZonedDateTime::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }
}
