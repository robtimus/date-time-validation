/*
 * MonthBeforeTest.java
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

package com.github.robtimus.validation.time;

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

@SuppressWarnings("nls")
class MonthBeforeTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<Date> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "date",
                        Date.from(utcInstantAtDefaultZone("2007-05-01T00:50:15.00Z")),
                        Date.from(utcInstantAtDefaultZone("2007-04-30T23:50:15.00Z")),
                        Date.from(utcInstantAtDefaultZone("2007-06-01T00:50:15.00Z")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Date> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "date",
                        Date.from(utcInstantAtOffset("2007-06-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2007-05-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2007-06-01T01:50:15.00Z", 1)));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T01:50:15.00Z", 1)));
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
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T00:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-04-30T23:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T00:50:15+02:00[Europe/Paris]")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T01:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T01:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T02:50:15+02:00[Europe/Paris]")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:50:15+02:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:50:15+02:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:50:15+02:00[Europe/Paris]", 1)));
            }
        }
    }

    @Nested
    @DisplayName("Instant")
    class ForInstant {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<Instant> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "instant",
                        utcInstantAtDefaultZone("2007-05-01T00:50:15.00Z"),
                        utcInstantAtDefaultZone("2007-04-30T23:50:15.00Z"),
                        utcInstantAtDefaultZone("2007-06-01T00:50:15.00Z"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Instant> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "instant",
                        utcInstantAtOffset("2007-06-01T00:50:15.00Z", 1),
                        utcInstantAtOffset("2007-05-01T00:50:15.00Z", 1),
                        utcInstantAtOffset("2007-06-01T01:50:15.00Z", 1));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        utcInstantAtOffsetAfterSystem("2007-06-01T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2007-05-01T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2007-06-01T01:50:15.00Z", 1));
            }
        }
    }

    @Nested
    @DisplayName("LocalDate")
    class ForLocalDate {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<LocalDate> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "localDate",
                        LocalDate.parse("2007-05-31"),
                        LocalDate.parse("2007-04-30"),
                        LocalDate.parse("2007-06-01"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<LocalDate> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "localDate",
                        LocalDate.parse("2007-05-31"),
                        LocalDate.parse("2007-04-30"),
                        LocalDate.parse("2007-06-01"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDate> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDate",
                        LocalDate.parse("2007-05-31"),
                        LocalDate.parse("2007-04-30"),
                        LocalDate.parse("2007-06-01"));
            }
        }
    }

    @Nested
    @DisplayName("LocalDateTime")
    class ForLocalDateTime {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<LocalDateTime> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "localDateTime",
                        LocalDateTime.parse("2007-05-01T00:50:15"),
                        LocalDateTime.parse("2007-04-30T23:50:15"),
                        LocalDateTime.parse("2007-06-01T00:50:15"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<LocalDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "localDateTime",
                        LocalDateTime.parse("2007-05-01T00:50:15"),
                        LocalDateTime.parse("2007-04-30T23:50:15"),
                        LocalDateTime.parse("2007-06-01T00:50:15"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDateTime",
                        LocalDateTime.parse("2007-05-01T00:50:15"),
                        LocalDateTime.parse("2007-04-30T23:50:15"),
                        LocalDateTime.parse("2007-06-01T00:50:15"));
            }
        }
    }

    @Nested
    @DisplayName("Month")
    class ForMonth {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<Month> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "month",
                        Month.MAY,
                        Month.APRIL,
                        Month.JUNE,
                        "must be before %s");
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Month> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "month",
                        Month.MAY,
                        Month.APRIL,
                        Month.JUNE,
                        "must be before %s");
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Month> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "month",
                        Month.MAY,
                        Month.APRIL,
                        Month.JUNE,
                        "must be before %s");
            }
        }
    }

    @Nested
    @DisplayName("MonthDay")
    class ForMonthDay {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<MonthDay> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "monthDay",
                        MonthDay.parse("--05-31"),
                        MonthDay.parse("--04-30"),
                        MonthDay.parse("--06-01"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<MonthDay> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "monthDay",
                        MonthDay.parse("--05-31"),
                        MonthDay.parse("--04-30"),
                        MonthDay.parse("--06-01"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<MonthDay> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "monthDay",
                        MonthDay.parse("--05-31"),
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
                        OffsetDateTime.parse("2007-05-01T00:50:15+01:00"),
                        OffsetDateTime.parse("2007-04-30T23:50:15+01:00"),
                        OffsetDateTime.parse("2007-06-01T00:50:15+01:00"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-06-01T00:15:30+01:00"),
                        OffsetDateTime.parse("2007-05-01T00:50:15+01:00"),
                        OffsetDateTime.parse("2007-06-01T01:50:15+01:00"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        offsetDateTimeAtOffsetAfterSystem("2007-06-01T00:15:30+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2007-05-01T00:50:15+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2007-06-01T01:50:15+01:00", 1));
            }
        }
    }

    @Nested
    @DisplayName("YearMonth")
    class ForYearMonth {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<YearMonth> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "yearMonth",
                        YearMonth.parse("2007-05"),
                        YearMonth.parse("2007-04"),
                        YearMonth.parse("2007-06"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<YearMonth> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "yearMonth",
                        YearMonth.parse("2007-05"),
                        YearMonth.parse("2007-04"),
                        YearMonth.parse("2007-06"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<YearMonth> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "yearMonth",
                        YearMonth.parse("2007-05"),
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
                        ZonedDateTime.parse("2007-05-01T00:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-04-30T23:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-06-01T00:50:15+02:00[Europe/Paris]"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-06-01T01:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-05-01T01:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-06-01T02:50:15+02:00[Europe/Paris]"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:50:15+02:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:50:15+02:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:50:15+02:00[Europe/Paris]", 1));
            }
        }
    }

    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final T exactValue;
        private final T smallerValue;
        private final T largerValue;
        private final ClockProvider clockProvider;
        private final String expectedMessage;

        private ConstraintTest(Class<?> beanType, String propertyName, T exactValue, T smallerValue, T largerValue) {
            this(beanType, propertyName, exactValue, smallerValue, largerValue, "must have a month that is before %s");
        }

        private ConstraintTest(Class<?> beanType, String propertyName, T exactValue, T smallerValue, T largerValue, String expectedMessageFormat) {
            this.beanType = beanType;
            this.propertyName = propertyName;
            this.exactValue = exactValue;
            this.smallerValue = smallerValue;
            this.largerValue = largerValue;
            this.clockProvider = () -> null;

            Month month = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(MonthBefore.class)
                    .value();

            this.expectedMessage = String.format(expectedMessageFormat, month);
        }

        @Test
        @DisplayName("null")
        void testNull() {
            List<?> violations = validate(clockProvider, beanType, propertyName, null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("exact value")
        void testExactValue() {
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, exactValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, MonthBefore.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
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
            assertAnnotation(violation, MonthBefore.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @MonthBefore(value = Month.MAY, zoneId = "provided")
        Date date;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        Calendar calendar;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        Instant instant;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        LocalDate localDate;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        LocalDateTime localDateTime;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        Month month;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        MonthDay monthDay;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        YearMonth yearMonth;

        @MonthBefore(value = Month.MAY, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        Date date;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        Calendar calendar;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        Instant instant;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        LocalDate localDate;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        LocalDateTime localDateTime;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        Month month;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        MonthDay monthDay;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        YearMonth yearMonth;

        @MonthBefore(value = Month.MAY, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @MonthBefore(Month.MAY)
        Date date;

        @MonthBefore(Month.MAY)
        Calendar calendar;

        @MonthBefore(Month.MAY)
        Instant instant;

        @MonthBefore(Month.MAY)
        LocalDate localDate;

        @MonthBefore(Month.MAY)
        LocalDateTime localDateTime;

        @MonthBefore(Month.MAY)
        Month month;

        @MonthBefore(Month.MAY)
        MonthDay monthDay;

        @MonthBefore(Month.MAY)
        OffsetDateTime offsetDateTime;

        @MonthBefore(Month.MAY)
        YearMonth yearMonth;

        @MonthBefore(Month.MAY)
        ZonedDateTime zonedDateTime;
    }
}
