/*
 * MonthNotAfterValidator.java
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import com.github.robtimus.validation.time.MonthNotAfter;

/**
 * Container class for constraint validators for {@link MonthNotAfter}.
 *
 * @author Rob Spoor
 */
public final class MonthNotAfterValidator {

    private MonthNotAfterValidator() {
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDateEnumValidator<MonthNotAfter, Month> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthNotAfter::value), nonProvidedZoneId(MonthNotAfter::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarEnumValidator<MonthNotAfter, Month> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthNotAfter::value), MonthNotAfter::zoneId, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, Instant, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthNotAfter::value), nonProvidedZoneId(MonthNotAfter::zoneId), DateTimeValidator::toMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthNotAfter::value), systemOnlyZoneId(MonthNotAfter::zoneId), LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthNotAfter::value), systemOnlyZoneId(MonthNotAfter::zoneId), LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthNotAfter::value), systemOnlyZoneId(MonthNotAfter::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.MonthNotAfter.message}", MonthNotAfter::message,
                    "{com.github.robtimus.validation.time.MonthNotAfter.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthNotAfter::value), systemOnlyZoneId(MonthNotAfter::zoneId), MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthNotAfter::value), MonthNotAfter::zoneId, DateTimeValidator::toMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthNotAfter::value), systemOnlyZoneId(MonthNotAfter::zoneId), YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorEnumValidator<MonthNotAfter, ZonedDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthNotAfter::value), MonthNotAfter::zoneId, DateTimeValidator::toMonth);
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month> valueExtractor) {
        return constraint -> notAfter(valueExtractor.apply(constraint));
    }

    private static Set<Month> notAfter(Month value) {
        return EnumSet.range(Month.JANUARY, value);
    }
}
