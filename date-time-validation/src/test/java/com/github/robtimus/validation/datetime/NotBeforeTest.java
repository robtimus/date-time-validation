/*
 * NotBeforeTest.java
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

package com.github.robtimus.validation.datetime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
class NotBeforeTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Date> {

            WithNow() {
                super(TestClassWithNow.class, "date",
                        Date.from(Instant.parse("2007-12-03T10:15:30.00Z")),
                        Date.from(Instant.parse("2007-12-03T10:15:30.00Z").minusMillis(1)),
                        Date.from(Instant.parse("2007-12-03T10:15:30.00Z").plusMillis(1)),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Date> {

            WithMoment() {
                super(TestClassWithMoment.class, "date",
                        Date.from(Instant.parse("2007-12-03T10:15:30.00Z")),
                        Date.from(Instant.parse("2007-12-03T10:15:30.00Z").minusMillis(1)),
                        Date.from(Instant.parse("2007-12-03T10:15:30.00Z").plusMillis(1)),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("Calendar")
    class ForCalendar {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Calendar> {

            WithNow() {
                super(TestClassWithNow.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").plus(1, ChronoUnit.MILLIS)),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithMoment.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").plus(1, ChronoUnit.MILLIS)),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different time zone")
        class WithDifferentTimeZone extends ConstraintTest<Calendar> {

            WithDifferentTimeZone() {
                super(TestClassWithMoment.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T09:15:30+01:00[UTC]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T09:15:30+01:00[UTC]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T09:15:30+01:00[UTC]").plus(1, ChronoUnit.MILLIS)),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("Instant")
    class ForInstant {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Instant> {

            WithNow() {
                super(TestClassWithNow.class, "instant",
                        Instant.parse("2007-12-03T10:15:30.00Z"),
                        Instant.parse("2007-12-03T10:15:30.00Z").minusNanos(1),
                        Instant.parse("2007-12-03T10:15:30.00Z").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Instant> {

            WithMoment() {
                super(TestClassWithMoment.class, "instant",
                        Instant.parse("2007-12-03T10:15:30.00Z"),
                        Instant.parse("2007-12-03T10:15:30.00Z").minusNanos(1),
                        Instant.parse("2007-12-03T10:15:30.00Z").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("LocalDate")
    class ForLocalDate {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalDate> {

            WithNow() {
                super(TestClassWithNow.class, "localDate",
                        LocalDate.parse("2007-12-03"),
                        LocalDate.parse("2007-12-03").minusDays(1),
                        LocalDate.parse("2007-12-03").plusDays(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalDate> {

            WithMoment() {
                super(TestClassWithMoment.class, "localDate",
                        LocalDate.parse("2007-12-03"),
                        LocalDate.parse("2007-12-03").minusDays(1),
                        LocalDate.parse("2007-12-03").plusDays(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("LocalDateTime")
    class ForLocalDateTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalDateTime> {

            WithNow() {
                super(TestClassWithNow.class, "localDateTime",
                        LocalDateTime.parse("2007-12-03T10:15:30"),
                        LocalDateTime.parse("2007-12-03T10:15:30").minusNanos(1),
                        LocalDateTime.parse("2007-12-03T10:15:30").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "localDateTime",
                        LocalDateTime.parse("2007-12-03T10:15:30"),
                        LocalDateTime.parse("2007-12-03T10:15:30").minusNanos(1),
                        LocalDateTime.parse("2007-12-03T10:15:30").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("LocalTime")
    class ForLocalTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalTime> {

            WithNow() {
                super(TestClassWithNow.class, "localTime",
                        LocalTime.parse("10:15:30"),
                        LocalTime.parse("10:15:30").minusNanos(1),
                        LocalTime.parse("10:15:30").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "localTime",
                        LocalTime.parse("10:15:30"),
                        LocalTime.parse("10:15:30").minusNanos(1),
                        LocalTime.parse("10:15:30").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("MonthDay")
    class ForMonthDay {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<MonthDay> {

            WithNow() {
                super(TestClassWithNow.class, "monthDay",
                        MonthDay.parse("--12-03"),
                        MonthDay.parse("--12-02"),
                        MonthDay.parse("--12-04"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<MonthDay> {

            WithMoment() {
                super(TestClassWithMoment.class, "monthDay",
                        MonthDay.parse("--12-03"),
                        MonthDay.parse("--12-02"),
                        MonthDay.parse("--12-04"),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("OffsetDateTime")
    class ForOffsetDateTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<OffsetDateTime> {

            WithNow() {
                super(TestClassWithNow.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00"),
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00").minusNanos(1),
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00"),
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00").minusNanos(1),
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00").plusNanos(1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different offset")
        class WithDifferentOffset extends ConstraintTest<OffsetDateTime> {

            WithDifferentOffset() {
                super(TestClassWithMoment.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-12-03T08:15:30-01:00"),
                        OffsetDateTime.parse("2007-12-03T08:15:30-01:00").minusNanos(1),
                        OffsetDateTime.parse("2007-12-03T08:15:30-01:00").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("OffsetTime")
    class ForOffsetTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<OffsetTime> {

            WithNow() {
                super(TestClassWithNow.class, "offsetTime",
                        OffsetTime.parse("10:15:30+01:00"),
                        OffsetTime.parse("10:15:30+01:00").minusNanos(1),
                        OffsetTime.parse("10:15:30+01:00").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "offsetTime",
                        OffsetTime.parse("10:15:30+01:00"),
                        OffsetTime.parse("10:15:30+01:00").minusNanos(1),
                        OffsetTime.parse("10:15:30+01:00").plusNanos(1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different offset")
        class WithDifferentOffset extends ConstraintTest<OffsetTime> {

            WithDifferentOffset() {
                super(TestClassWithMoment.class, "offsetTime",
                        OffsetTime.parse("08:15:30-01:00"),
                        OffsetTime.parse("08:15:30-01:00").minusNanos(1),
                        OffsetTime.parse("08:15:30-01:00").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("Year")
    class ForYear {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Year> {

            WithNow() {
                super(TestClassWithNow.class, "year",
                        Year.parse("2007"),
                        Year.parse("2007").minusYears(1),
                        Year.parse("2007").plusYears(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Year> {

            WithMoment() {
                super(TestClassWithMoment.class, "year",
                        Year.parse("2007"),
                        Year.parse("2007").minusYears(1),
                        Year.parse("2007").plusYears(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("YearMonth")
    class ForYearMonth {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<YearMonth> {

            WithNow() {
                super(TestClassWithNow.class, "yearMonth",
                        YearMonth.parse("2007-12"),
                        YearMonth.parse("2007-12").minusMonths(1),
                        YearMonth.parse("2007-12").plusMonths(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<YearMonth> {

            WithMoment() {
                super(TestClassWithMoment.class, "yearMonth",
                        YearMonth.parse("2007-12"),
                        YearMonth.parse("2007-12").minusMonths(1),
                        YearMonth.parse("2007-12").plusMonths(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("ZonedDateTime")
    class ForZonedDateTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<ZonedDateTime> {

            WithNow() {
                super(TestClassWithNow.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").minusNanos(1),
                        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").plusNanos(1),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").minusNanos(1),
                        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").plusNanos(1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different time zone")
        class WithDifferentTimeZone extends ConstraintTest<ZonedDateTime> {

            WithDifferentTimeZone() {
                super(TestClassWithMoment.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-12-03T09:15:30+01:00[UTC]"),
                        ZonedDateTime.parse("2007-12-03T09:15:30+01:00[UTC]").minusNanos(1),
                        ZonedDateTime.parse("2007-12-03T09:15:30+01:00[UTC]").plusNanos(1),
                        () -> null);
            }
        }
    }

    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final String moment;
        private final T exactValue;
        private final T smallerValue;
        private final T largerValue;
        private final ClockProvider clockProvider;

        private ConstraintTest(Class<?> beanType, String propertyName, T exactValue, T smallerValue, T largerValue, ClockProvider clockProvider) {
            this.beanType = beanType;
            this.propertyName = propertyName;
            this.exactValue = exactValue;
            this.smallerValue = smallerValue;
            this.largerValue = largerValue;
            this.clockProvider = clockProvider;

            this.moment = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(NotBefore.class)
                    .moment();
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
            List<?> violations = validate(clockProvider, beanType, propertyName, exactValue);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("smaller value")
        void testSmallerValue() {
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, smallerValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, NotBefore.class);
            assertEquals("must not be before " + moment, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("larger value")
        void testLargerValue() {
            List<?> violations = validate(clockProvider, beanType, propertyName, largerValue);
            assertEquals(Collections.emptyList(), violations);
        }
    }

    private static final class TestClassWithNow {
        @NotBefore(moment = "now")
        Date date;

        @NotBefore(moment = "now")
        Calendar calendar;

        @NotBefore(moment = "now")
        Instant instant;

        @NotBefore(moment = "now")
        LocalDate localDate;

        @NotBefore(moment = "now")
        LocalDateTime localDateTime;

        @NotBefore(moment = "now")
        LocalTime localTime;

        @NotBefore(moment = "now")
        MonthDay monthDay;

        @NotBefore(moment = "now")
        OffsetDateTime offsetDateTime;

        @NotBefore(moment = "now")
        OffsetTime offsetTime;

        @NotBefore(moment = "now")
        Year year;

        @NotBefore(moment = "now")
        YearMonth yearMonth;

        @NotBefore(moment = "now")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithMoment {
        @NotBefore(moment = "2007-12-03T10:15:30.00Z")
        Date date;

        @NotBefore(moment = "2007-12-03T10:15:30+01:00[Europe/Paris]")
        Calendar calendar;

        @NotBefore(moment = "2007-12-03T10:15:30.00Z")
        Instant instant;

        @NotBefore(moment = "2007-12-03")
        LocalDate localDate;

        @NotBefore(moment = "2007-12-03T10:15:30")
        LocalDateTime localDateTime;

        @NotBefore(moment = "10:15:30")
        LocalTime localTime;

        @NotBefore(moment = "--12-03")
        MonthDay monthDay;

        @NotBefore(moment = "2007-12-03T10:15:30+01:00")
        OffsetDateTime offsetDateTime;

        @NotBefore(moment = "10:15:30+01:00")
        OffsetTime offsetTime;

        @NotBefore(moment = "2007")
        Year year;

        @NotBefore(moment = "2007-12")
        YearMonth yearMonth;

        @NotBefore(moment = "2007-12-03T10:15:30+01:00[Europe/Paris]")
        ZonedDateTime zonedDateTime;
    }
}
