/*
 * DayOfWeekAfterValidator.java
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

package com.github.robtimus.validation.time.dayofweek.validators;

import java.lang.annotation.Annotation;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import com.github.robtimus.validation.time.dayofweek.DayOfWeekAfter;
import com.github.robtimus.validation.time.validators.CalendarEnumValidator;
import com.github.robtimus.validation.time.validators.DateEnumValidator;
import com.github.robtimus.validation.time.validators.InstantEnumValidator;
import com.github.robtimus.validation.time.validators.TemporalAccessorEnumValidator;
import com.github.robtimus.validation.time.validators.ZonedDateTimeEnumValidator;

/**
 * Container class for constraint validators for {@link DayOfWeekAfter}.
 *
 * @author Rob Spoor
 */
public final class DayOfWeekAfterValidator {

    private DayOfWeekAfterValidator() {
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateEnumValidator<DayOfWeekAfter, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), nonProvidedZoneId(DayOfWeekAfter::zoneId), ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarEnumValidator<DayOfWeekAfter, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), DayOfWeekAfter::zoneId, ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends TemporalAccessorEnumValidator<DayOfWeekAfter, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), systemOnlyZoneId(DayOfWeekAfter::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.DayOfWeekAfter.message}", DayOfWeekAfter::message,
                    "{com.github.robtimus.validation.time.DayOfWeekAfter.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantEnumValidator<DayOfWeekAfter, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), nonProvidedZoneId(DayOfWeekAfter::zoneId), ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorEnumValidator<DayOfWeekAfter, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), systemOnlyZoneId(DayOfWeekAfter::zoneId), LocalDate::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorEnumValidator<DayOfWeekAfter, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), systemOnlyZoneId(DayOfWeekAfter::zoneId), LocalDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorEnumValidator<DayOfWeekAfter, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), DayOfWeekAfter::zoneId,
                    OffsetDateTime::getDayOfWeek, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimeEnumValidator<DayOfWeekAfter, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedDayOfWeeks(DayOfWeekAfter::value), DayOfWeekAfter::zoneId, ZonedDateTime::getDayOfWeek);
        }
    }

    static <A extends Annotation> Function<A, Set<DayOfWeek>> allowedDayOfWeeks(Function<A, DayOfWeek> valueExtractor) {
        return constraint -> after(valueExtractor.apply(constraint));
    }

    private static Set<DayOfWeek> after(DayOfWeek value) {
        return EnumSet.complementOf(EnumSet.range(DayOfWeek.MONDAY, value));
    }
}
