/*
 * MinuteIsValidator.java
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

package com.github.robtimus.validation.minute.validators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Function;
import jakarta.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.FieldValidator;
import com.github.robtimus.validation.minute.MinuteIs;

/**
 * Container class for constraint validators for {@link MinuteIs}.
 *
 * @author Rob Spoor
 */
public final class MinuteIsValidator {

    private static final Function<MinuteIs, BiPredicate<Integer, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> {
        int allowedValue = annotation.value();
        return (value, provider) -> value == allowedValue;
    };

    private MinuteIsValidator() {
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MinuteIs> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MinuteIs> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends FieldValidator.ForInstant<MinuteIs> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(ChronoField.MINUTE_OF_HOUR, MinuteIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends FieldValidator.WithoutZoneId<MinuteIs, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(ChronoField.MINUTE_OF_HOUR, MinuteIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends FieldValidator.WithoutZoneId<MinuteIs, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(ChronoField.MINUTE_OF_HOUR, MinuteIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends FieldValidator<MinuteIs, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(ChronoField.MINUTE_OF_HOUR, MinuteIs::zoneId, OffsetDateTime::atZoneSameInstant, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends FieldValidator.ForOffsetTime<MinuteIs> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(ChronoField.MINUTE_OF_HOUR, MinuteIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MinuteIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends FieldValidator.ForZonedDateTime<MinuteIs> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(ChronoField.MINUTE_OF_HOUR, MinuteIs::zoneId, PREDICATE_EXTRACTOR);
        }
    }
}
