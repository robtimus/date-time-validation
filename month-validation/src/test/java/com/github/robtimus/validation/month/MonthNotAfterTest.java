/*
 * MonthNotAfterTest.java
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

package com.github.robtimus.validation.month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.validation.ClockProvider;
import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("nls")
class MonthNotAfterTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate extends AbstractNonProvidedZoneIdTest<Date> {

        ForDate() {
            super(TestClassWithProvidedZoneId.class, "date", new Date());
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Date> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2007-05-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-06-01T00:59:59.999Z", 1))
                        ),
                        Date.from(utcInstantAtOffset("2007-05-01T00:59:59.999Z", 1)),
                        Date.from(utcInstantAtOffset("2007-06-01T01:00:00Z", 1)));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T00:59:59.999Z", 1))
                        ),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T00:59:59.999Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T01:00:00Z", 1)));
            }
        }
    }

    @Nested
    @DisplayName("Calendar")
    class ForCalendar {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<Calendar> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-31T23:59:59.999+02:00[Europe/Paris]"))
                        ),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-04-30T23:59:59.999+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T00:00:00+02:00[Europe/Paris]")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T01:59:59.999+02:00[Europe/Paris]"))
                        ),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T01:59:59.999+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T02:00:00+02:00[Europe/Paris]")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999+02:00[Europe/Paris]", 1))
                        ),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+02:00[Europe/Paris]", 1)));
            }
        }
    }

    @Nested
    @DisplayName("Instant")
    class ForInstant extends AbstractNonProvidedZoneIdTest<Instant> {

        ForInstant() {
            super(TestClassWithProvidedZoneId.class, "instant", Instant.now());
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Instant> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffset("2007-05-01T01:00:00Z", 1),
                                utcInstantAtOffset("2007-06-01T00:59:59.999999999Z", 1)
                        ),
                        utcInstantAtOffset("2007-05-01T00:59:59.999999999Z", 1),
                        utcInstantAtOffset("2007-06-01T01:00:00Z", 1));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-05-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-06-01T00:59:59.999999999Z", 1)
                        ),
                        utcInstantAtOffsetAfterSystem("2007-05-01T00:59:59.999999999Z", 1),
                        utcInstantAtOffsetAfterSystem("2007-06-01T01:00:00Z", 1));
            }
        }
    }

    @Nested
    @DisplayName("LocalDate")
    class ForLocalDate extends AbstractSystemOnlyZoneIdTest<LocalDate> {

        ForLocalDate() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "localDate", LocalDate.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDate> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDate",
                        Arrays.asList(
                                LocalDate.parse("2007-05-01"),
                                LocalDate.parse("2007-05-31")
                        ),
                        LocalDate.parse("2007-04-30"),
                        LocalDate.parse("2007-06-01"));
            }
        }
    }

    @Nested
    @DisplayName("LocalDateTime")
    class ForLocalDateTime extends AbstractSystemOnlyZoneIdTest<LocalDateTime> {

        ForLocalDateTime() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "localDateTime", LocalDateTime.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDateTime",
                        Arrays.asList(
                                LocalDateTime.parse("2007-05-01T00:00:00"),
                                LocalDateTime.parse("2007-05-31T23:59:59.999999999")
                        ),
                        LocalDateTime.parse("2007-04-30T23:59:59.999999999"),
                        LocalDateTime.parse("2007-06-01T00:00:00"));
            }
        }
    }

    @Nested
    @DisplayName("Month")
    class ForMonth extends AbstractSystemOnlyZoneIdTest<Month> {

        ForMonth() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "month", Month.OCTOBER);
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Month> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "month",
                        Arrays.asList(
                                Month.MAY
                        ),
                        Month.APRIL,
                        Month.JUNE,
                        "must not be after %s");
            }
        }
    }

    @Nested
    @DisplayName("MonthDay")
    class ForMonthDay extends AbstractSystemOnlyZoneIdTest<MonthDay> {

        ForMonthDay() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "monthDay", MonthDay.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<MonthDay> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "monthDay",
                        Arrays.asList(
                                MonthDay.parse("--05-01"),
                                MonthDay.parse("--05-31")
                        ),
                        MonthDay.parse("--04-30"),
                        MonthDay.parse("--06-01"));
            }
        }
    }

    @Nested
    @DisplayName("OffsetDateTime")
    class ForOffsetDateTime {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<OffsetDateTime> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-01T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-05-31T23:59:59.999999999+01:00")
                        ),
                        OffsetDateTime.parse("2007-04-30T23:59:59.999999999+01:00"),
                        OffsetDateTime.parse("2007-06-01T00:00:00+01:00"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-01T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-06-01T00:59:59.999999999+01:00")
                        ),
                        OffsetDateTime.parse("2007-05-01T00:59:59.999999999+01:00"),
                        OffsetDateTime.parse("2007-06-01T01:00:00+01:00"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999999999+01:00", 1)
                        ),
                        offsetDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999999999+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+01:00", 1));
            }
        }
    }

    @Nested
    @DisplayName("YearMonth")
    class ForYearMonth extends AbstractSystemOnlyZoneIdTest<YearMonth> {

        ForYearMonth() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "yearMonth", YearMonth.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<YearMonth> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "yearMonth",
                        Arrays.asList(
                                YearMonth.parse("2007-05")
                        ),
                        YearMonth.parse("2007-04"),
                        YearMonth.parse("2007-06"));
            }
        }
    }

    @Nested
    @DisplayName("ZonedDateTime")
    class ForZonedDateTime {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<ZonedDateTime> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-01T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-05-31T23:59:59.999999999+02:00[Europe/Paris]")
                        ),
                        ZonedDateTime.parse("2007-04-30T23:59:59.999999999+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-06-01T00:00:00+02:00[Europe/Paris]"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-01T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-06-01T01:59:59.999999999+02:00[Europe/Paris]")
                        ),
                        ZonedDateTime.parse("2007-05-01T01:59:59.999999999+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-06-01T02:00:00+02:00[Europe/Paris]"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999999999+02:00[Europe/Paris]", 1)
                        ),
                        zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+02:00[Europe/Paris]", 1));
            }
        }
    }

    @TestInstance(Lifecycle.PER_CLASS)
    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final List<T> exactValues;
        private final T smallerValue;
        private final T largerValue;
        private final ClockProvider clockProvider;
        private final String expectedMessage;

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> exactValues, T smallerValue, T largerValue) {
            this(beanType, propertyName, exactValues, smallerValue, largerValue, "must have a month that is not after %s");
        }

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> exactValues, T smallerValue, T largerValue,
                String expectedMessageFormat) {

            this.beanType = beanType;
            this.propertyName = propertyName;
            this.exactValues = exactValues;
            this.smallerValue = smallerValue;
            this.largerValue = largerValue;
            this.clockProvider = () -> null;

            Month month = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(MonthNotAfter.class)
                    .value();

            this.expectedMessage = String.format(expectedMessageFormat, month);
        }

        @Test
        @DisplayName("null")
        void testNull() {
            List<?> violations = validate(clockProvider, beanType, propertyName, null);
            assertEquals(Collections.emptyList(), violations);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("exactValues")
        @DisplayName("exact value")
        void testExactValue(T exactValue) {
            List<?> violations = validate(clockProvider, beanType, propertyName, exactValue);
            assertEquals(Collections.emptyList(), violations);
        }

        List<T> exactValues() {
            return exactValues;
        }

        @Test
        @DisplayName("smaller value")
        void testSmallerValue() {
            List<?> violations = validate(clockProvider, beanType, propertyName, smallerValue);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("larger value")
        void testLargerValue() {
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, largerValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, MonthNotAfter.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        Date date;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        Calendar calendar;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        Instant instant;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        LocalDate localDate;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        LocalDateTime localDateTime;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        Month month;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        MonthDay monthDay;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        YearMonth yearMonth;

        @MonthNotAfter(value = Month.MAY, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        Date date;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        Calendar calendar;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        Instant instant;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        LocalDate localDate;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        LocalDateTime localDateTime;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        Month month;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        MonthDay monthDay;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        YearMonth yearMonth;

        @MonthNotAfter(value = Month.MAY, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @MonthNotAfter(Month.MAY)
        Date date;

        @MonthNotAfter(Month.MAY)
        Calendar calendar;

        @MonthNotAfter(Month.MAY)
        Instant instant;

        @MonthNotAfter(Month.MAY)
        LocalDate localDate;

        @MonthNotAfter(Month.MAY)
        LocalDateTime localDateTime;

        @MonthNotAfter(Month.MAY)
        Month month;

        @MonthNotAfter(Month.MAY)
        MonthDay monthDay;

        @MonthNotAfter(Month.MAY)
        OffsetDateTime offsetDateTime;

        @MonthNotAfter(Month.MAY)
        YearMonth yearMonth;

        @MonthNotAfter(Month.MAY)
        ZonedDateTime zonedDateTime;
    }
}
