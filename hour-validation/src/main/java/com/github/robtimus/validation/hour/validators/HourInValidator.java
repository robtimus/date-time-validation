/*
 * HourInValidator.java
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

package com.github.robtimus.validation.hour.validators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Function;
import jakarta.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.FieldValidator;
import com.github.robtimus.validation.hour.HourIn;

/**
 * Container class for constraint validators for {@link HourIn}.
 *
 * @author Rob Spoor
 */
public final class HourInValidator {

    private static final Function<HourIn, BiPredicate<Integer, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> {
        int[] allowedValues = annotation.value();
        Arrays.sort(allowedValues);
        return (value, provider) -> Arrays.binarySearch(allowedValues, value) >= 0;
    };

    private HourInValidator() {
    }

    /**
     * A constraint validator for {@link HourIn} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<HourIn> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<HourIn> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForZonedDateTime());
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends FieldValidator.ForInstant<HourIn> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(ChronoField.HOUR_OF_DAY, HourIn::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends FieldValidator.WithoutZoneId<HourIn, LocalDateTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(ChronoField.HOUR_OF_DAY, HourIn::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link LocalTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalTime extends FieldValidator.WithoutZoneId<HourIn, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalTime() {
            super(ChronoField.HOUR_OF_DAY, HourIn::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends FieldValidator<HourIn, OffsetDateTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(ChronoField.HOUR_OF_DAY, HourIn::zoneId, OffsetDateTime::atZoneSameInstant, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link OffsetTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetTime extends FieldValidator.ForOffsetTime<HourIn> {

        /**
         * Creates a new validator.
         */
        public ForOffsetTime() {
            super(ChronoField.HOUR_OF_DAY, HourIn::zoneId, PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link HourIn} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends FieldValidator.ForZonedDateTime<HourIn> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(ChronoField.HOUR_OF_DAY, HourIn::zoneId, PREDICATE_EXTRACTOR);
        }
    }
}
