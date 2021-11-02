/*
 * DayOfWeekNotAfterTest.java
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
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
class DayOfWeekNotAfterTest extends AbstractConstraintTest {

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
                        Date.from(utcInstantAtOffset("2007-05-18T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2007-05-17T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffset("2007-05-18T01:50:15.00Z", 1)));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Date.from(utcInstantAtOffsetAfterSystem("2007-05-18T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-05-17T00:50:15.00Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2007-05-18T01:50:15.00Z", 1)));
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
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-17T00:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-16T23:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-18T00:50:15+02:00[Europe/Paris]")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-18T01:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-17T01:50:15+02:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-05-18T02:50:15+02:00[Europe/Paris]")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-18T00:50:15+02:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-17T00:50:15+02:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-18T01:50:15+02:00[Europe/Paris]", 1)));
            }
        }
    }

    @Nested
    @DisplayName("DayOfWeek")
    class ForDayOfWeek extends AbstractSystemOnlyZoneIdTest<DayOfWeek> {

        ForDayOfWeek() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "dayOfWeek", DayOfWeek.MONDAY);
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<DayOfWeek> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "dayOfWeek",
                        DayOfWeek.THURSDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.FRIDAY,
                        "must not be after %s");
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
                        utcInstantAtOffset("2007-05-18T00:50:15.00Z", 1),
                        utcInstantAtOffset("2007-05-17T00:50:15.00Z", 1),
                        utcInstantAtOffset("2007-05-18T01:50:15.00Z", 1));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        utcInstantAtOffsetAfterSystem("2007-05-18T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2007-05-17T00:50:15.00Z", 1),
                        utcInstantAtOffsetAfterSystem("2007-05-18T01:50:15.00Z", 1));
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
                        LocalDate.parse("2007-05-17"),
                        LocalDate.parse("2007-05-16"),
                        LocalDate.parse("2007-05-18"));
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
                        LocalDateTime.parse("2007-05-17T00:50:15"),
                        LocalDateTime.parse("2007-05-16T23:50:15"),
                        LocalDateTime.parse("2007-05-18T00:50:15"));
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
                        OffsetDateTime.parse("2007-05-17T00:50:15+01:00"),
                        OffsetDateTime.parse("2007-05-16T23:50:15+01:00"),
                        OffsetDateTime.parse("2007-05-18T00:50:15+01:00"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-05-18T00:15:30+01:00"),
                        OffsetDateTime.parse("2007-05-17T00:50:15+01:00"),
                        OffsetDateTime.parse("2007-05-18T01:50:15+01:00"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        offsetDateTimeAtOffsetAfterSystem("2007-05-18T00:15:30+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2007-05-17T00:50:15+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2007-05-18T01:50:15+01:00", 1));
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
                        ZonedDateTime.parse("2007-05-17T00:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-05-16T23:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-05-18T00:50:15+02:00[Europe/Paris]"));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-05-18T01:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-05-17T01:50:15+02:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-05-18T02:50:15+02:00[Europe/Paris]"));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        zonedDateTimeAtOffsetAfterSystem("2007-05-18T00:50:15+02:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2007-05-17T00:50:15+02:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2007-05-18T01:50:15+02:00[Europe/Paris]", 1));
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
            this(beanType, propertyName, exactValue, smallerValue, largerValue, "must have a day of the week that is not after %s");
        }

        private ConstraintTest(Class<?> beanType, String propertyName, T exactValue, T smallerValue, T largerValue, String expectedMessageFormat) {
            this.beanType = beanType;
            this.propertyName = propertyName;
            this.exactValue = exactValue;
            this.smallerValue = smallerValue;
            this.largerValue = largerValue;
            this.clockProvider = () -> null;

            DayOfWeek dayOfWeek = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(DayOfWeekNotAfter.class)
                    .value();

            this.expectedMessage = String.format(expectedMessageFormat, dayOfWeek);
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
            assertAnnotation(violation, DayOfWeekNotAfter.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        Date date;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        Calendar calendar;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        DayOfWeek dayOfWeek;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        Instant instant;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        LocalDate localDate;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        LocalDateTime localDateTime;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        Date date;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        Calendar calendar;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        DayOfWeek dayOfWeek;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        Instant instant;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        LocalDate localDate;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        LocalDateTime localDateTime;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @DayOfWeekNotAfter(value = DayOfWeek.THURSDAY, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        Date date;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        Calendar calendar;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        DayOfWeek dayOfWeek;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        Instant instant;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        LocalDate localDate;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        LocalDateTime localDateTime;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        OffsetDateTime offsetDateTime;

        @DayOfWeekNotAfter(DayOfWeek.THURSDAY)
        ZonedDateTime zonedDateTime;
    }
}
