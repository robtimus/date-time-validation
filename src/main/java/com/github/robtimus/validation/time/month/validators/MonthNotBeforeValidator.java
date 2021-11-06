/*
 * MonthNotBeforeValidator.java
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

package com.github.robtimus.validation.time.month.validators;

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
import com.github.robtimus.validation.time.month.MonthNotBefore;
import com.github.robtimus.validation.time.validators.CalendarEnumValidator;
import com.github.robtimus.validation.time.validators.DateEnumValidator;
import com.github.robtimus.validation.time.validators.InstantEnumValidator;
import com.github.robtimus.validation.time.validators.TemporalAccessorEnumValidator;
import com.github.robtimus.validation.time.validators.ZonedDateTimeEnumValidator;

/**
 * Container class for constraint validators for {@link MonthNotBefore}.
 *
 * @author Rob Spoor
 */
public final class MonthNotBeforeValidator {

    private MonthNotBeforeValidator() {
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateEnumValidator<MonthNotBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(allowedMonths(MonthNotBefore::value), nonProvidedZoneId(MonthNotBefore::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarEnumValidator<MonthNotBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(allowedMonths(MonthNotBefore::value), MonthNotBefore::zoneId, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends InstantEnumValidator<MonthNotBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(allowedMonths(MonthNotBefore::value), nonProvidedZoneId(MonthNotBefore::zoneId), ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link LocalDate}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDate extends TemporalAccessorEnumValidator<MonthNotBefore, LocalDate, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDate() {
            super(allowedMonths(MonthNotBefore::value), systemOnlyZoneId(MonthNotBefore::zoneId), LocalDate::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends TemporalAccessorEnumValidator<MonthNotBefore, LocalDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(allowedMonths(MonthNotBefore::value), systemOnlyZoneId(MonthNotBefore::zoneId), LocalDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link Month}.
     *
     * @author Rob Spoor
     */
    public static class ForMonth extends TemporalAccessorEnumValidator<MonthNotBefore, Month, Month> {

        /**
         * Creates a new validator.
         */
        @SuppressWarnings("nls")
        public ForMonth() {
            super(allowedMonths(MonthNotBefore::value), systemOnlyZoneId(MonthNotBefore::zoneId), Function.identity());
            useReplacementMessage("{com.github.robtimus.validation.time.MonthNotBefore.message}", MonthNotBefore::message,
                    "{com.github.robtimus.validation.time.MonthNotBefore.message.forMonth}");
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link MonthDay}.
     *
     * @author Rob Spoor
     */
    public static class ForMonthDay extends TemporalAccessorEnumValidator<MonthNotBefore, MonthDay, Month> {

        /**
         * Creates a new validator.
         */
        public ForMonthDay() {
            super(allowedMonths(MonthNotBefore::value), systemOnlyZoneId(MonthNotBefore::zoneId), MonthDay::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends TemporalAccessorEnumValidator<MonthNotBefore, OffsetDateTime, Month> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(allowedMonths(MonthNotBefore::value), MonthNotBefore::zoneId,
                    OffsetDateTime::getMonth, OffsetDateTime::atZoneSameInstant, ZonedDateTime::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link YearMonth}.
     *
     * @author Rob Spoor
     */
    public static class ForYearMonth extends TemporalAccessorEnumValidator<MonthNotBefore, YearMonth, Month> {

        /**
         * Creates a new validator.
         */
        public ForYearMonth() {
            super(allowedMonths(MonthNotBefore::value), systemOnlyZoneId(MonthNotBefore::zoneId), YearMonth::getMonth);
        }
    }

    /**
     * A constraint validator for {@link MonthNotBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends ZonedDateTimeEnumValidator<MonthNotBefore, Month> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(allowedMonths(MonthNotBefore::value), MonthNotBefore::zoneId, ZonedDateTime::getMonth);
        }
    }

    static <A extends Annotation> Function<A, Set<Month>> allowedMonths(Function<A, Month> valueExtractor) {
        return constraint -> notBefore(valueExtractor.apply(constraint));
    }

    private static Set<Month> notBefore(Month value) {
        return EnumSet.range(value, Month.DECEMBER);
    }
}
