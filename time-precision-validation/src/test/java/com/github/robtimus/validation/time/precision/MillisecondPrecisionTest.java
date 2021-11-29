/*
 * MillisecondPrecisionTest.java
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

package com.github.robtimus.validation.time.precision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.validation.ClockProvider;
import javax.validation.ConstraintViolation;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("nls")
class MillisecondPrecisionTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate extends NoValidatorTest<Date> {

        ForDate() {
            super("date", new Date());
        }
    }

    @Nested
    @DisplayName("Calendar")
    class ForCalendar extends NoValidatorTest<Calendar> {

        ForCalendar() {
            super("calendar", Calendar.getInstance());
        }
    }

    @Nested
    @DisplayName("DayOfWeek")
    class ForDayOfWeek extends NotSupportedTest<DayOfWeek> {

        ForDayOfWeek() {
            super(DayOfWeek.MONDAY);
        }
    }

    @Nested
    @DisplayName("Instant")
    class ForInstant extends ConstraintTest<Instant> {

        ForInstant() {
            super("temporalAccessor",
                    Arrays.asList(
                            Instant.parse("2007-12-03T10:45:00Z"),
                            Instant.parse("2007-12-03T10:45:00.001Z"),
                            Instant.parse("2007-12-03T10:45:00.999Z"),
                            Instant.parse("2007-12-03T10:45:01Z"),
                            Instant.parse("2007-12-03T10:45:01.001Z"),
                            Instant.parse("2007-12-03T10:45:01.999Z"),
                            Instant.parse("2007-12-03T10:45:59Z"),
                            Instant.parse("2007-12-03T10:45:59.001Z"),
                            Instant.parse("2007-12-03T10:45:59.999Z")
                    ),
                    Arrays.asList(
                            Instant.parse("2007-12-03T10:45:00.000001Z"),
                            Instant.parse("2007-12-03T10:45:00.000000001Z"),
                            Instant.parse("2007-12-03T10:45:00.999999Z"),
                            Instant.parse("2007-12-03T10:45:00.999999999Z"),
                            Instant.parse("2007-12-03T10:45:01.000001Z"),
                            Instant.parse("2007-12-03T10:45:01.000000001Z"),
                            Instant.parse("2007-12-03T10:45:01.999999Z"),
                            Instant.parse("2007-12-03T10:45:01.999999999Z"),
                            Instant.parse("2007-12-03T10:45:59.000001Z"),
                            Instant.parse("2007-12-03T10:45:59.000000001Z"),
                            Instant.parse("2007-12-03T10:45:59.999999Z"),
                            Instant.parse("2007-12-03T10:45:59.999999999Z")
                    ));
        }
    }

    @Nested
    @DisplayName("LocalDate")
    class ForLocalDate extends NotSupportedTest<LocalDate> {

        ForLocalDate() {
            super(LocalDate.now());
        }
    }

    @Nested
    @DisplayName("LocalDateTime")
    class ForLocalDateTime extends ConstraintTest<LocalDateTime> {

        ForLocalDateTime() {
            super("temporalAccessor",
                    Arrays.asList(
                            LocalDateTime.parse("2007-12-03T10:45:00"),
                            LocalDateTime.parse("2007-12-03T10:45:00.001"),
                            LocalDateTime.parse("2007-12-03T10:45:00.999"),
                            LocalDateTime.parse("2007-12-03T10:45:01"),
                            LocalDateTime.parse("2007-12-03T10:45:01.001"),
                            LocalDateTime.parse("2007-12-03T10:45:01.999"),
                            LocalDateTime.parse("2007-12-03T10:45:59"),
                            LocalDateTime.parse("2007-12-03T10:45:59.001"),
                            LocalDateTime.parse("2007-12-03T10:45:59.999")
                    ),
                    Arrays.asList(
                            LocalDateTime.parse("2007-12-03T10:45:00.000001"),
                            LocalDateTime.parse("2007-12-03T10:45:00.000000001"),
                            LocalDateTime.parse("2007-12-03T10:45:00.999999"),
                            LocalDateTime.parse("2007-12-03T10:45:00.999999999"),
                            LocalDateTime.parse("2007-12-03T10:45:01.000001"),
                            LocalDateTime.parse("2007-12-03T10:45:01.000000001"),
                            LocalDateTime.parse("2007-12-03T10:45:01.999999"),
                            LocalDateTime.parse("2007-12-03T10:45:01.999999999"),
                            LocalDateTime.parse("2007-12-03T10:45:59.000001"),
                            LocalDateTime.parse("2007-12-03T10:45:59.000000001"),
                            LocalDateTime.parse("2007-12-03T10:45:59.999999"),
                            LocalDateTime.parse("2007-12-03T10:45:59.999999999")
                    ));
        }
    }

    @Nested
    @DisplayName("LocalTime")
    class ForLocalTime extends ConstraintTest<LocalTime> {

        ForLocalTime() {
            super("temporalAccessor",
                    Arrays.asList(
                            LocalTime.parse("10:45:00"),
                            LocalTime.parse("10:45:00.001"),
                            LocalTime.parse("10:45:00.999"),
                            LocalTime.parse("10:45:01"),
                            LocalTime.parse("10:45:01.001"),
                            LocalTime.parse("10:45:01.999"),
                            LocalTime.parse("10:45:59"),
                            LocalTime.parse("10:45:59.001"),
                            LocalTime.parse("10:45:59.999")
                    ),
                    Arrays.asList(
                            LocalTime.parse("10:45:00.000001"),
                            LocalTime.parse("10:45:00.000000001"),
                            LocalTime.parse("10:45:00.999999"),
                            LocalTime.parse("10:45:00.999999999"),
                            LocalTime.parse("10:45:01.000001"),
                            LocalTime.parse("10:45:01.000000001"),
                            LocalTime.parse("10:45:01.999999"),
                            LocalTime.parse("10:45:01.999999999"),
                            LocalTime.parse("10:45:59.000001"),
                            LocalTime.parse("10:45:59.000000001"),
                            LocalTime.parse("10:45:59.999999"),
                            LocalTime.parse("10:45:59.999999999")
                    ));
        }
    }

    @Nested
    @DisplayName("Month")
    class ForMonth extends NotSupportedTest<Month> {

        ForMonth() {
            super(Month.DECEMBER);
        }
    }

    @Nested
    @DisplayName("MonthDay")
    class ForMonthDay extends NotSupportedTest<MonthDay> {

        ForMonthDay() {
            super(MonthDay.now());
        }
    }

    @Nested
    @DisplayName("OffsetDateTime")
    class ForOffsetDateTime extends ConstraintTest<OffsetDateTime> {

        ForOffsetDateTime() {
            super("temporalAccessor",
                    Arrays.asList(
                            OffsetDateTime.parse("2007-12-03T10:45:00+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:00.001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:00.999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01.001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01.999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59.001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59.999+01:00")
                    ),
                    Arrays.asList(
                            OffsetDateTime.parse("2007-12-03T10:45:00.000001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:00.000000001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:00.999999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:00.999999999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01.000001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01.000000001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01.999999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:01.999999999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59.000001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59.000000001+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59.999999+01:00"),
                            OffsetDateTime.parse("2007-12-03T10:45:59.999999999+01:00")
                    ));
        }
    }

    @Nested
    @DisplayName("OffsetTime")
    class ForOffsetTime extends ConstraintTest<OffsetTime> {

        ForOffsetTime() {
            super("temporalAccessor",
                    Arrays.asList(
                            OffsetTime.parse("10:45:00+01:00"),
                            OffsetTime.parse("10:45:00.001+01:00"),
                            OffsetTime.parse("10:45:00.999+01:00"),
                            OffsetTime.parse("10:45:01+01:00"),
                            OffsetTime.parse("10:45:01.001+01:00"),
                            OffsetTime.parse("10:45:01.999+01:00"),
                            OffsetTime.parse("10:45:59+01:00"),
                            OffsetTime.parse("10:45:59.001+01:00"),
                            OffsetTime.parse("10:45:59.999+01:00")
                    ),
                    Arrays.asList(
                            OffsetTime.parse("10:45:00.000001+01:00"),
                            OffsetTime.parse("10:45:00.000000001+01:00"),
                            OffsetTime.parse("10:45:00.999999+01:00"),
                            OffsetTime.parse("10:45:00.999999999+01:00"),
                            OffsetTime.parse("10:45:01.000001+01:00"),
                            OffsetTime.parse("10:45:01.000000001+01:00"),
                            OffsetTime.parse("10:45:01.999999+01:00"),
                            OffsetTime.parse("10:45:01.999999999+01:00"),
                            OffsetTime.parse("10:45:59.000001+01:00"),
                            OffsetTime.parse("10:45:59.000000001+01:00"),
                            OffsetTime.parse("10:45:59.999999+01:00"),
                            OffsetTime.parse("10:45:59.999999999+01:00")
                    ));
        }
    }

    @Nested
    @DisplayName("Year")
    class ForYear extends NotSupportedTest<Year> {

        ForYear() {
            super(Year.now());
        }
    }

    @Nested
    @DisplayName("YearMonth")
    class ForYearMonth extends NotSupportedTest<YearMonth> {

        ForYearMonth() {
            super(YearMonth.now());
        }
    }

    @Nested
    @DisplayName("ZonedDateTime")
    class ForZonedDateTime extends ConstraintTest<ZonedDateTime> {

        ForZonedDateTime() {
            super("temporalAccessor",
                    Arrays.asList(
                            ZonedDateTime.parse("2007-12-03T10:45:00+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:00.001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:00.999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01.001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01.999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59.001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59.999+01:00")
                    ),
                    Arrays.asList(
                            ZonedDateTime.parse("2007-12-03T10:45:00.000001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:00.000000001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:00.999999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:00.999999999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01.000001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01.000000001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01.999999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:01.999999999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59.000001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59.000000001+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59.999999+01:00"),
                            ZonedDateTime.parse("2007-12-03T10:45:59.999999999+01:00")
                    ));
        }
    }

    @TestInstance(Lifecycle.PER_CLASS)
    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final String propertyName;
        private final List<T> allowedValues;
        private final List<T> notAllowedValues;
        private final ClockProvider clockProvider;
        private final String expectedMessage;

        private ConstraintTest(String propertyName, List<T> allowedValues, List<T> notAllowedValues) {
            this.propertyName = propertyName;
            this.allowedValues = allowedValues;
            this.notAllowedValues = notAllowedValues;
            this.clockProvider = () -> null;

            this.expectedMessage = "must have a minimum precision of milliseconds";
        }

        @Test
        @DisplayName("null")
        void testNull() {
            List<?> violations = validate(clockProvider, TestClass.class, propertyName, null);
            assertEquals(Collections.emptyList(), violations);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("allowedValues")
        @DisplayName("allowed value")
        void testAllowedValue(T allowedValue) {
            List<?> violations = validate(clockProvider, TestClass.class, propertyName, allowedValue);
            assertEquals(Collections.emptyList(), violations);
        }

        List<T> allowedValues() {
            return allowedValues;
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("notAllowedValues")
        @DisplayName("not allowed value")
        void testNotAllowedValue(T notAllowedValue) {
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, TestClass.class, propertyName, notAllowedValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, MillisecondPrecision.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        List<T> notAllowedValues() {
            return notAllowedValues;
        }
    }

    private abstract static class NotSupportedTest<T extends TemporalAccessor> extends AbstractConstraintTest {

        private final T value;
        private final ClockProvider clockProvider;

        private NotSupportedTest(T value) {
            this.value = value;
            this.clockProvider = () -> null;
        }

        @Test
        @DisplayName("not supported")
        void testNotSupported() {
            ValidationException exception = assertThrows(ValidationException.class,
                    () -> validate(clockProvider, TestClass.class, "temporalAccessor", value));
            assertInstanceOf(UnsupportedTemporalTypeException.class, exception.getCause());
        }
    }

    private abstract static class NoValidatorTest<T> extends AbstractConstraintTest {

        private final String propertyName;
        private final T value;
        private final ClockProvider clockProvider;

        private NoValidatorTest(String propertyName, T value) {
            this.propertyName = propertyName;
            this.value = value;
            this.clockProvider = () -> null;
        }

        @Test
        @DisplayName("not supported")
        void testNotSupported() {
            assertThrows(UnexpectedTypeException.class, () -> validate(clockProvider, TestClass.class, propertyName, value));
        }
    }

    private static final class TestClass {
        @MillisecondPrecision
        Date date;

        @MillisecondPrecision
        Calendar calendar;

        @MillisecondPrecision
        TemporalAccessor temporalAccessor;
    }
}
