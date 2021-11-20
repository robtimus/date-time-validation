/*
 * YearMaxBeforeTest.java
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

package com.github.robtimus.validation.year;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
class YearMaxBeforeTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate extends AbstractNonProvidedZoneIdTest<Date> {

        ForDate() {
            super(TestClassWithProvidedZoneId.class, "date", new Date());
        }

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Date> {

            WithNow() {
                super(TestClassWithNow.class, "date",
                        Date.from(Instant.parse("2006-01-01T13:50:15.00Z")),
                        Date.from(Instant.parse("2005-12-31T13:50:15.00Z")),
                        Date.from(Instant.parse("2007-01-01T13:50:15.00Z")),
                        () -> Clock.fixed(Instant.parse("2007-12-01T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Date> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "date",
                        Date.from(utcInstantAtOffset("2007-01-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2006-01-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2007-01-02T00:50:15.00Z", 1)),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Date.from(utcInstantAtOffsetAfterSystem("2007-01-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2006-01-01T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-01-02T00:50:15.00Z", 1)),
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
                        GregorianCalendar.from(ZonedDateTime.parse("2006-01-01T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2005-12-31T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-01-01T13:50:15+01:00[Europe/Paris]")),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-01T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2006-01-01T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2005-12-31T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-01-01T13:50:15+01:00[Europe/Paris]")),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-01-01T00:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-01-01T00:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-01-02T00:50:15+01:00[Europe/Paris]")),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-01-01T00:50:15+01:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-01-01T00:50:15+01:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-01-02T00:50:15+01:00[Europe/Paris]", 1)),
                        () -> null);
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
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Instant> {

            WithNow() {
                super(TestClassWithNow.class, "instant",
                        Instant.parse("2006-01-01T13:50:15.00Z"),
                        Instant.parse("2005-12-31T13:50:15.00Z"),
                        Instant.parse("2007-01-01T13:50:15.00Z"),
                        () -> Clock.fixed(Instant.parse("2007-12-01T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Instant> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "instant",
                        utcInstantAtOffset("2007-01-01T00:50:15.00Z", 1),
                        utcInstantAtOffset("2006-01-01T00:50:15.00Z", 1),
                        utcInstantAtOffset("2007-01-02T00:50:15.00Z", 1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        utcInstantAtOffsetAfterSystem("2007-01-01T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2006-01-01T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2007-01-02T00:50:15.00Z", 1),
                        () -> null);
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
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalDate> {

            WithNow() {
                super(TestClassWithNow.class, "localDate",
                        LocalDate.parse("2006-01-01"),
                        LocalDate.parse("2005-12-31"),
                        LocalDate.parse("2007-01-01"),
                        () -> Clock.fixed(Instant.parse("2007-12-01T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDate> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDate",
                        LocalDate.parse("2006-01-01"),
                        LocalDate.parse("2005-12-31"),
                        LocalDate.parse("2007-01-01"),
                        () -> null);
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
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalDateTime> {

            WithNow() {
                super(TestClassWithNow.class, "localDateTime",
                        LocalDateTime.parse("2006-01-01T13:50:15"),
                        LocalDateTime.parse("2005-12-31T13:50:15"),
                        LocalDateTime.parse("2007-01-01T13:50:15"),
                        () -> Clock.fixed(Instant.parse("2007-12-01T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDateTime",
                        LocalDateTime.parse("2006-01-01T13:50:15"),
                        LocalDateTime.parse("2005-12-31T13:50:15"),
                        LocalDateTime.parse("2007-01-01T13:50:15"),
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
                        OffsetDateTime.parse("2006-01-01T13:50:15+01:00"),
                        OffsetDateTime.parse("2005-12-31T13:50:15+01:00"),
                        OffsetDateTime.parse("2007-01-01T13:50:15+01:00"),
                        () -> Clock.fixed(Instant.parse("2007-12-01T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "offsetDateTime",
                        OffsetDateTime.parse("2006-01-01T13:50:15+01:00"),
                        OffsetDateTime.parse("2005-12-31T13:50:15+01:00"),
                        OffsetDateTime.parse("2007-01-01T13:50:15+01:00"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-01-01T00:50:15+01:00"),
                        OffsetDateTime.parse("2006-01-01T00:50:15+01:00"),
                        OffsetDateTime.parse("2007-01-02T00:50:15+01:00"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        offsetDateTimeAtOffsetAfterSystem("2007-01-01T00:50:15+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2006-01-01T00:50:15+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2007-01-02T00:50:15+01:00", 1),
                        () -> null);
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
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<YearMonth> {

            WithNow() {
                super(TestClassWithNow.class, "yearMonth",
                        YearMonth.parse("2006-01"),
                        YearMonth.parse("2005-12"),
                        YearMonth.parse("2007-01"),
                        () -> Clock.fixed(Instant.parse("2007-12-01T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<YearMonth> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "yearMonth",
                        YearMonth.parse("2006-01"),
                        YearMonth.parse("2005-12"),
                        YearMonth.parse("2007-01"),
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
                        ZonedDateTime.parse("2006-01-01T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2005-12-31T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-01-01T13:50:15+01:00[Europe/Paris]"),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-01T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "zonedDateTime",
                        ZonedDateTime.parse("2006-01-01T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2005-12-31T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-01-01T13:50:15+01:00[Europe/Paris]"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-01-01T00:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-01-01T00:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-01-02T00:50:15+01:00[Europe/Paris]"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        zonedDateTimeAtOffsetAfterSystem("2007-01-01T00:50:15+01:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2006-01-01T00:50:15+01:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2007-01-02T00:50:15+01:00[Europe/Paris]", 1),
                        () -> null);
            }
        }
    }

    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final String moment;
        private final int years;
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

            YearMaxBefore maxBefore = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(YearMaxBefore.class);
            this.moment = maxBefore.moment();
            this.years = maxBefore.years();
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
            assertAnnotation(violation, YearMaxBefore.class);
            assertEquals("must have a year that is at most " + years + " years before " + moment, violation.getMessage());
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
        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        Date date;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        Calendar calendar;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        Instant instant;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        LocalDate localDate;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        LocalDateTime localDateTime;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        OffsetDateTime offsetDateTime;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        YearMonth yearMonth;

        @YearMaxBefore(years = 1, moment = "now", zoneId = "system")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithProvidedZoneId {
        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        Date date;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        Calendar calendar;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        Instant instant;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        LocalDate localDate;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        LocalDateTime localDateTime;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        YearMonth yearMonth;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        Date date;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        Calendar calendar;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        Instant instant;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        LocalDate localDate;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        LocalDateTime localDateTime;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        YearMonth yearMonth;

        @YearMaxBefore(years = 1, moment = "2007", zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @YearMaxBefore(years = 1, moment = "2007")
        Date date;

        @YearMaxBefore(years = 1, moment = "2007")
        Calendar calendar;

        @YearMaxBefore(years = 1, moment = "2007")
        Instant instant;

        @YearMaxBefore(years = 1, moment = "2007")
        LocalDate localDate;

        @YearMaxBefore(years = 1, moment = "2007")
        LocalDateTime localDateTime;

        @YearMaxBefore(years = 1, moment = "2007")
        OffsetDateTime offsetDateTime;

        @YearMaxBefore(years = 1, moment = "2007")
        YearMonth yearMonth;

        @YearMaxBefore(years = 1, moment = "2007")
        ZonedDateTime zonedDateTime;
    }
}
