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

package com.github.robtimus.validation.time.validators;

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
import com.github.robtimus.validation.time.DayOfWeekIs;

/**
 * Container class for constraint validators for {@link DayOfWeekIs}.
 *
 * @author Rob Spoor
 */
public final class DayOfWeekIsValidator {

    private DayOfWeekIsValidator() {
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateEnumValidator<DayOfWeekIs, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), nonProvidedZoneId(DayOfWeekIs::zoneId), ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarEnumValidator<DayOfWeekIs, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), DayOfWeekIs::zoneId, ZonedDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link DayOfWeek}.
     *
     * @author Rob Spoor
     */
    public static class ForDayOfWeek extends AbstractTemporalAccessorEnumValidator<DayOfWeekIs, DayOfWeek, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForDayOfWeek() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), systemOnlyZoneId(DayOfWeekIs::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.DayOfWeekIs.message}", DayOfWeekIs::message,
                    "{com.github.robtimus.validation.time.DayOfWeekIs.message.forDayOfWeek}");
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorEnumValidator<DayOfWeekIs, Instant, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), nonProvidedZoneId(DayOfWeekIs::zoneId), DateTimeValidator::toDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorEnumValidator<DayOfWeekIs, LocalDate, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), systemOnlyZoneId(DayOfWeekIs::zoneId), LocalDate::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorEnumValidator<DayOfWeekIs, LocalDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), systemOnlyZoneId(DayOfWeekIs::zoneId), LocalDateTime::getDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorEnumValidator<DayOfWeekIs, OffsetDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), DayOfWeekIs::zoneId, DateTimeValidator::toDayOfWeek);
        }
    }

    /**
     * A constraint validator for {@link DayOfWeekIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorEnumValidator<DayOfWeekIs, ZonedDateTime, DayOfWeek> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedDayOfWeeks(DayOfWeekIs::value), DayOfWeekIs::zoneId, DateTimeValidator::toDayOfWeek);
        }
    }

    static <A extends Annotation> Function<A, Set<DayOfWeek>> allowedDayOfWeeks(Function<A, DayOfWeek> valueExtractor) {
        return constraint -> EnumSet.of(valueExtractor.apply(constraint));
    }
}
