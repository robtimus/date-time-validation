/*
 * MonthInValidator.java
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
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;
import com.github.robtimus.validation.time.MonthIn;

/**
 * Container class for constraint validators for {@link MonthIn}.
 *
 * @author Rob Spoor
 */
public final class MonthInValidator {

    private MonthInValidator() {
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateEnumValidator<MonthIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthIn::value), nonProvidedZoneId(MonthIn::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarEnumValidator<MonthIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthIn::value), MonthIn::zoneId, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantEnumValidator<MonthIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthIn::value), nonProvidedZoneId(MonthIn::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorEnumValidator<MonthIn, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthIn::value), systemOnlyZoneId(MonthIn::zoneId), LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorEnumValidator<MonthIn, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthIn::value), systemOnlyZoneId(MonthIn::zoneId), LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends TemporalAccessorEnumValidator<MonthIn, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthIn::value), systemOnlyZoneId(MonthIn::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.MonthIn.message}", MonthIn::message,
                    "{com.github.robtimus.validation.time.MonthIn.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends TemporalAccessorEnumValidator<MonthIn, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthIn::value), systemOnlyZoneId(MonthIn::zoneId), MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorEnumValidator<MonthIn, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthIn::value), MonthIn::zoneId,
                    OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends TemporalAccessorEnumValidator<MonthIn, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthIn::value), systemOnlyZoneId(MonthIn::zoneId), YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIn} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimeEnumValidator<MonthIn, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthIn::value), MonthIn::zoneId, ZonedDateTime::getMonth);
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month[]> valueExtractor) {
        return constraint -> asSet(valueExtractor.apply(constraint));
    }

    private static Set<Month> asSet(Month[] values) {
        return values.length == 0 ? Collections.emptySet() : EnumSet.of(values[0], values);
    }
}
