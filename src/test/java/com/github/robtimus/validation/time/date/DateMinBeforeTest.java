/*
 * DateMinBeforeTest.java
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

package com.github.robtimus.validation.time.date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
class DateMinBeforeTest extends AbstractConstraintTest {

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
                        Date.from(Instant.parse("2006-11-02T13:50:15.00Z")),
                        Date.from(Instant.parse("2006-11-01T13:50:15.00Z")),
                        Date.from(Instant.parse("2006-11-03T13:50:15.00Z")),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Date> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "date",
                        Date.from(utcInstantAtOffset("2006-11-03T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2006-11-02T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2006-11-04T00:50:15.00Z", 1)),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Date.from(utcInstantAtOffsetAfterSystem("2006-11-03T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2006-11-02T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2006-11-04T00:50:15.00Z", 1)),
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
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-01T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-03T13:50:15+01:00[Europe/Paris]")),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-01T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-03T13:50:15+01:00[Europe/Paris]")),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-03T00:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T00:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-04T00:50:15+01:00[Europe/Paris]")),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-03T00:50:15+01:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-02T00:50:15+01:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-04T00:50:15+01:00[Europe/Paris]", 1)),
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
                        Instant.parse("2006-11-02T13:50:15.00Z"),
                        Instant.parse("2006-11-01T13:50:15.00Z"),
                        Instant.parse("2006-11-03T13:50:15.00Z"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Instant> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "instant",
                        utcInstantAtOffset("2006-11-03T00:50:15.00Z", 1),
                        utcInstantAtOffset("2006-11-02T00:50:15.00Z", 1),
                        utcInstantAtOffset("2006-11-04T00:50:15.00Z", 1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        utcInstantAtOffsetAfterSystem("2006-11-03T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2006-11-02T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2006-11-04T00:50:15.00Z", 1),
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
                        LocalDateTime.parse("2006-11-02T13:50:15"),
                        LocalDateTime.parse("2006-11-01T13:50:15"),
                        LocalDateTime.parse("2006-11-03T13:50:15"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDateTime",
                        LocalDateTime.parse("2006-11-02T13:50:15"),
                        LocalDateTime.parse("2006-11-01T13:50:15"),
                        LocalDateTime.parse("2006-11-03T13:50:15"),
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
                        OffsetDateTime.parse("2006-11-02T13:50:15+01:00"),
                        OffsetDateTime.parse("2006-11-01T13:50:15+01:00"),
                        OffsetDateTime.parse("2006-11-03T13:50:15+01:00"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "offsetDateTime",
                        OffsetDateTime.parse("2006-11-02T13:50:15+01:00"),
                        OffsetDateTime.parse("2006-11-01T13:50:15+01:00"),
                        OffsetDateTime.parse("2006-11-03T13:50:15+01:00"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        OffsetDateTime.parse("2006-11-03T00:50:15+01:00"),
                        OffsetDateTime.parse("2006-11-02T00:50:15+01:00"),
                        OffsetDateTime.parse("2006-11-04T00:50:15+01:00"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        offsetDateTimeAtOffsetAfterSystem("2006-11-03T00:50:15+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2006-11-02T00:50:15+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2006-11-04T00:50:15+01:00", 1),
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
                        ZonedDateTime.parse("2006-11-02T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-01T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-03T13:50:15+01:00[Europe/Paris]"),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "zonedDateTime",
                        ZonedDateTime.parse("2006-11-02T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-01T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-03T13:50:15+01:00[Europe/Paris]"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        ZonedDateTime.parse("2006-11-03T00:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-02T00:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-04T00:50:15+01:00[Europe/Paris]"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        zonedDateTimeAtOffsetAfterSystem("2006-11-03T00:50:15+01:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2006-11-02T00:50:15+01:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2006-11-04T00:50:15+01:00[Europe/Paris]", 1),
                        () -> null);
            }
        }
    }

    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final String moment;
        private final String duration;
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

            DateMinBefore minBefore = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(DateMinBefore.class);
            this.moment = minBefore.moment();
            this.duration = minBefore.duration();
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
            List<?> violations = validate(clockProvider, beanType, propertyName, smallerValue);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("larger value")
        void testLargerValue() {
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, largerValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, DateMinBefore.class);
            assertEquals("must have a date that is at least " + duration + " before " + moment, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }
    }

    private static final class TestClassWithNow {
        @DateMinBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        Date date;

        @DateMinBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        Calendar calendar;

        @DateMinBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        Instant instant;

        @DateMinBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        LocalDateTime localDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        OffsetDateTime offsetDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithProvidedZoneId {
        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        Date date;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        Calendar calendar;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        Instant instant;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        LocalDateTime localDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        Date date;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        Calendar calendar;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        Instant instant;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        LocalDateTime localDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        Date date;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        Calendar calendar;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        Instant instant;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        LocalDateTime localDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        OffsetDateTime offsetDateTime;

        @DateMinBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        ZonedDateTime zonedDateTime;
    }
}
