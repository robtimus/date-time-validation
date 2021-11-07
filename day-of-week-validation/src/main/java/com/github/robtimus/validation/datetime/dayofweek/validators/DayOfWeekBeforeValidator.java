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

package com.github.robtimus.validation.datetime.dayofweek.validators;

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
import com.github.robtimus.validation.datetime.dayofweek.DayOfWeekBefore;
import com.github.robtimus.validation.datetime.validators.CalendarEnumValidator;
import com.github.robtimus.validation.datetime.validators.DateEnumValidator;
import com.github.robtimus.validation.datetime.validators.InstantEnumValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorEnumValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimeEnumValidator;

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
    public static class ForDate extends DateEnumValidator<DayOfWeekBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), nonProvidedZoneId(DayOfWeekBefore::zoneId), ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarEnumValidator<DayOfWeekBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), DayOfWeekBefore::zoneId, ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends TemporalAccessorEnumValidator<DayOfWeekBefore, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), systemOnlyZoneId(DayOfWeekBefore::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.datetime.dayofweek.DayOfWeekBefore.message}", DayOfWeekBefore::message,
                    "{com.github.robtimus.validation.datetime.dayofweek.DayOfWeekBefore.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantEnumValidator<DayOfWeekBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), nonProvidedZoneId(DayOfWeekBefore::zoneId), ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorEnumValidator<DayOfWeekBefore, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), systemOnlyZoneId(DayOfWeekBefore::zoneId), LocalDate::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorEnumValidator<DayOfWeekBefore, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), systemOnlyZoneId(DayOfWeekBefore::zoneId), LocalDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorEnumValidator<DayOfWeekBefore, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), DayOfWeekBefore::zoneId,
                    OffsetDateTime::getDayOfWeek, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimeEnumValidator<DayOfWeekBefore, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedDayOfWeeks(DayOfWeekBefore::value), DayOfWeekBefore::zoneId, ZonedDateTime::getDayOfWeek);
        }
    }

    static <A extends Annotation> Function<A, Set<DayOfWeek>> allowedDayOfWeeks(Function<A, DayOfWeek> valueExtractor) {
        return constraint -> before(valueExtractor.apply(constraint));
    }

    private static Set<DayOfWeek> before(DayOfWeek value) {
        return EnumSet.complementOf(EnumSet.range(value, DayOfWeek.SUNDAY));
    }
}