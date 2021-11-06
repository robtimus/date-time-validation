/*
 * MonthBeforeValidator.java
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
import com.github.robtimus.validation.time.MonthBefore;

/**
 * Container class for constraint validators for {@link MonthBefore}.
 *
 * @author Rob Spoor
 */
public final class MonthBeforeValidator {

    private MonthBeforeValidator() {
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateEnumValidator<MonthBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthBefore::value), nonProvidedZoneId(MonthBefore::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarEnumValidator<MonthBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthBefore::value), MonthBefore::zoneId, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantEnumValidator<MonthBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthBefore::value), nonProvidedZoneId(MonthBefore::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorEnumValidator<MonthBefore, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthBefore::value), systemOnlyZoneId(MonthBefore::zoneId), LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorEnumValidator<MonthBefore, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthBefore::value), systemOnlyZoneId(MonthBefore::zoneId), LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends TemporalAccessorEnumValidator<MonthBefore, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthBefore::value), systemOnlyZoneId(MonthBefore::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.MonthBefore.message}", MonthBefore::message,
                    "{com.github.robtimus.validation.time.MonthBefore.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends TemporalAccessorEnumValidator<MonthBefore, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthBefore::value), systemOnlyZoneId(MonthBefore::zoneId), MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorEnumValidator<MonthBefore, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthBefore::value), MonthBefore::zoneId,
                    OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends TemporalAccessorEnumValidator<MonthBefore, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthBefore::value), systemOnlyZoneId(MonthBefore::zoneId), YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimeEnumValidator<MonthBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthBefore::value), MonthBefore::zoneId, ZonedDateTime::getMonth);
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month> valueExtractor) {
        return constraint -> before(valueExtractor.apply(constraint));
    }

    private static Set<Month> before(Month value) {
        return EnumSet.complementOf(EnumSet.range(value, Month.DECEMBER));
    }
}
