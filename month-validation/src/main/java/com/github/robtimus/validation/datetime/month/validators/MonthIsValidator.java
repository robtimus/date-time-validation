/*
 * MonthIsValidator.java
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

package com.github.robtimus.validation.datetime.month.validators;

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
import com.github.robtimus.validation.datetime.month.MonthIs;
import com.github.robtimus.validation.datetime.validators.CalendarEnumValidator;
import com.github.robtimus.validation.datetime.validators.DateEnumValidator;
import com.github.robtimus.validation.datetime.validators.InstantEnumValidator;
import com.github.robtimus.validation.datetime.validators.TemporalAccessorEnumValidator;
import com.github.robtimus.validation.datetime.validators.ZonedDateTimeEnumValidator;

/**
 * Container class for constraint validators for {@link MonthIs}.
 *
 * @author Rob Spoor
 */
public final class MonthIsValidator {

    private MonthIsValidator() {
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateEnumValidator<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthIs::value), nonProvidedZoneId(MonthIs::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarEnumValidator<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantEnumValidator<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthIs::value), nonProvidedZoneId(MonthIs::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorEnumValidator<MonthIs, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthIs::value), systemOnlyZoneId(MonthIs::zoneId), LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorEnumValidator<MonthIs, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthIs::value), systemOnlyZoneId(MonthIs::zoneId), LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends TemporalAccessorEnumValidator<MonthIs, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthIs::value), systemOnlyZoneId(MonthIs::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.datetime.month.MonthIs.message}", MonthIs::message,
                    "{com.github.robtimus.validation.datetime.month.MonthIs.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends TemporalAccessorEnumValidator<MonthIs, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthIs::value), systemOnlyZoneId(MonthIs::zoneId), MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorEnumValidator<MonthIs, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId,
                    OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends TemporalAccessorEnumValidator<MonthIs, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthIs::value), systemOnlyZoneId(MonthIs::zoneId), YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthIs} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimeEnumValidator<MonthIs, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthIs::value), MonthIs::zoneId, ZonedDateTime::getMonth);
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month> valueExtractor) {
        return constraint -> EnumSet.of(valueExtractor.apply(constraint));
    }
}
