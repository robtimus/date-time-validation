/*
 * DayOfWeekInValidator.java
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
import com.github.robtimus.validation.dayofweek.DayOfWeekIn;

/**
 * Container class for constraint validators for {@link DayOfWeekIn}.
 *
 * @author Rob Spoor
 */
public final class DayOfWeekInValidator {

    private static final Function<DayOfWeekIn, BiPredicate<DayOfWeek, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> {
        Set<DayOfWeek> allowedValues = asSet(annotation.value());
        return (value, provider) -> allowedValues.contains(value);
    };

    private DayOfWeekInValidator() {
    }

    private static Set<DayOfWeek> asSet(DayOfWeek[] values) {
        return values.length == 0 ? Collections.emptySet() : EnumSet.of(values[0], values);
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<DayOfWeekIn> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<DayOfWeekIn> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends PartValidator.WithoutZoneId<DayOfWeekIn, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(DayOfWeekIn::zoneId, Function.identity(), PREDICATE_EXTRACTOR);
            useReplacementMessageTemplate(DayOfWeekIn::message,
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekIn.message}",
                    "{com.github.robtimus.validation.dayofweek.DayOfWeekIn.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends PartValidator.ForInstant<DayOfWeekIn, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(DayOfWeekIn::zoneId, ZonedDateTime::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends PartValidator.WithoutZoneId<DayOfWeekIn, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(DayOfWeekIn::zoneId, LocalDate::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends PartValidator.WithoutZoneId<DayOfWeekIn, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(DayOfWeekIn::zoneId, LocalDateTime::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends PartValidator<DayOfWeekIn, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(DayOfWeekIn::zoneId, OffsetDateTime::getDayOfWeek, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getDayOfWeek,
                    PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIn} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends PartValidator.ForZonedDateTime<DayOfWeekIn, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(DayOfWeekIn::zoneId, ZonedDateTime::getDayOfWeek, PREDICATE_EXTRACTOR);
        }
    }
}
