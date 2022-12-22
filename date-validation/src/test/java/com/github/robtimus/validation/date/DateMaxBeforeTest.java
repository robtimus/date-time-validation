/*
 * DateMaxBeforeTest.java
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

package com.github.robtimus.validation.date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import jakarta.validation.ClockProvider;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("nls")
class DateMaxBeforeTest extends AbstractConstraintTest {

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
                        Arrays.asList(
                                Date.from(utcInstantAtDefaultZone("2006-11-02T00:00:00Z")),
                                Date.from(utcInstantAtDefaultZone("2006-11-02T23:59:59.999Z"))
                        ),
                        Date.from(utcInstantAtDefaultZone("2006-11-01T23:59:59.999Z")),
                        Date.from(utcInstantAtDefaultZone("2006-11-03T00:00:00Z")),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Date> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2006-11-02T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2006-11-03T00:59:59.999Z", 1))
                        ),
                        Date.from(utcInstantAtOffset("2006-11-02T00:59:59.999Z", 1)),
                        Date.from(utcInstantAtOffset("2006-11-03T01:00:00Z", 1)),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2006-11-02T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2006-11-03T00:59:59.999Z", 1))
                        ),
                        Date.from(utcInstantAtOffsetAfterSystem("2006-11-02T00:59:59.999Z", 1)),
                        Date.from(utcInstantAtOffsetAfterSystem("2006-11-03T01:00:00Z", 1)),
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
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtDefaultZone("2006-11-02T00:00:00+01:00[Europe/Paris]")),
                                GregorianCalendar.from(zonedDateTimeAtDefaultZone("2006-11-02T00:59:59.999+01:00[Europe/Paris]"))
                        ),
                        GregorianCalendar.from(zonedDateTimeAtDefaultZone("2006-11-01T23:59:59.999+01:00[Europe/Paris]")),
                        GregorianCalendar.from(zonedDateTimeAtDefaultZone("2006-11-03T00:00:00+01:00[Europe/Paris]")),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T00:00:00+01:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T23:59:59.999+01:00[Europe/Paris]"))
                        ),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-01T23:59:59.999+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-03T00:00:00+01:00[Europe/Paris]")),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T01:00:00+01:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2006-11-03T00:59:59.999+01:00[Europe/Paris]"))
                        ),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-02T00:59:59.999+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2006-11-03T01:00:00+01:00[Europe/Paris]")),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-02T01:00:00+01:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-03T00:59:59.999+01:00[Europe/Paris]", 1))
                        ),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-02T00:59:59.999+01:00[Europe/Paris]", 1)),
                        GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2006-11-03T01:00:00+01:00[Europe/Paris]", 1)),
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
                        Arrays.asList(
                                utcInstantAtDefaultZone("2006-11-02T00:00:00Z"),
                                utcInstantAtDefaultZone("2006-11-02T23:59:59.999999999Z")
                        ),
                        utcInstantAtDefaultZone("2006-11-01T23:59:59.999999999Z"),
                        utcInstantAtDefaultZone("2006-11-03T00:00:00Z"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Instant> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffset("2006-11-02T01:00:00Z", 1),
                                utcInstantAtOffset("2006-11-03T00:59:59.999999999Z", 1)
                        ),
                        utcInstantAtOffset("2006-11-02T00:59:59.999999999Z", 1),
                        utcInstantAtOffset("2006-11-03T01:00:00Z", 1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2006-11-02T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2006-11-03T00:59:59.999999999Z", 1)
                        ),
                        utcInstantAtOffsetAfterSystem("2006-11-02T00:59:59.999999999Z", 1),
                        utcInstantAtOffsetAfterSystem("2006-11-03T01:00:00Z", 1),
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
                        Arrays.asList(
                                LocalDateTime.parse("2006-11-02T00:00:00"),
                                LocalDateTime.parse("2006-11-02T23:59:59.999999999")
                        ),
                        LocalDateTime.parse("2006-11-01T23:59:59.999999999"),
                        LocalDateTime.parse("2006-11-03T00:00:00"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localDateTime",
                        Arrays.asList(
                                LocalDateTime.parse("2006-11-02T00:00:00"),
                                LocalDateTime.parse("2006-11-02T23:59:59.999999999")
                        ),
                        LocalDateTime.parse("2006-11-01T23:59:59.999999999"),
                        LocalDateTime.parse("2006-11-03T00:00:00"),
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
                        Arrays.asList(
                                offsetDateTimeAtDefaultZone("2006-11-02T00:00:00+01:00"),
                                offsetDateTimeAtDefaultZone("2006-11-02T23:59:59.999999999+01:00")
                        ),
                        offsetDateTimeAtDefaultZone("2006-11-01T23:59:59.999999999+01:00"),
                        offsetDateTimeAtDefaultZone("2006-11-03T00:00:00+01:00"),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2006-11-02T00:00:00+01:00"),
                                OffsetDateTime.parse("2006-11-02T23:59:59.999999999+01:00")
                        ),
                        OffsetDateTime.parse("2006-11-01T23:59:59.999999999+01:00"),
                        OffsetDateTime.parse("2006-11-03T00:00:00+01:00"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2006-11-02T01:00:00+01:00"),
                                OffsetDateTime.parse("2006-11-03T00:59:59.999999999+01:00")
                        ),
                        OffsetDateTime.parse("2006-11-02T00:59:59.999999999+01:00"),
                        OffsetDateTime.parse("2006-11-03T01:00:00+01:00"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2006-11-02T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2006-11-03T00:59:59.999999999+01:00", 1)
                        ),
                        offsetDateTimeAtOffsetAfterSystem("2006-11-02T00:59:59.999999999+01:00", 1),
                        offsetDateTimeAtOffsetAfterSystem("2006-11-03T01:00:00+01:00", 1),
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
                        Arrays.asList(
                                zonedDateTimeAtDefaultZone("2006-11-02T00:00:00+01:00[Europe/Paris]"),
                                zonedDateTimeAtDefaultZone("2006-11-02T00:59:59.999+01:00[Europe/Paris]")
                        ),
                        zonedDateTimeAtDefaultZone("2006-11-01T23:59:59.999+01:00[Europe/Paris]"),
                        zonedDateTimeAtDefaultZone("2006-11-03T00:00:00+01:00[Europe/Paris]"),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithProvidedZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2006-11-02T00:00:00+01:00[Europe/Paris]"),
                                ZonedDateTime.parse("2006-11-02T23:59:59.999+01:00[Europe/Paris]")
                        ),
                        ZonedDateTime.parse("2006-11-01T23:59:59.999+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-03T00:00:00+01:00[Europe/Paris]"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2006-11-02T01:00:00+01:00[Europe/Paris]"),
                                ZonedDateTime.parse("2006-11-03T00:59:59.999+01:00[Europe/Paris]")
                        ),
                        ZonedDateTime.parse("2006-11-02T00:59:59.999+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2006-11-03T01:00:00+01:00[Europe/Paris]"),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2006-11-02T01:00:00+01:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2006-11-03T00:59:59.999+01:00[Europe/Paris]", 1)
                        ),
                        zonedDateTimeAtOffsetAfterSystem("2006-11-02T00:59:59.999+01:00[Europe/Paris]", 1),
                        zonedDateTimeAtOffsetAfterSystem("2006-11-03T01:00:00+01:00[Europe/Paris]", 1),
                        () -> null);
            }
        }
    }

    @TestInstance(Lifecycle.PER_CLASS)
    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final String moment;
        private final String duration;
        private final List<T> exactValues;
        private final T smallerValue;
        private final T largerValue;
        private final ClockProvider clockProvider;

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> exactValues, T smallerValue, T largerValue,
                ClockProvider clockProvider) {

            this.beanType = beanType;
            this.propertyName = propertyName;
            this.exactValues = exactValues;
            this.smallerValue = smallerValue;
            this.largerValue = largerValue;
            this.clockProvider = clockProvider;

            DateMaxBefore maxBefore = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(DateMaxBefore.class);
            this.moment = maxBefore.moment();
            this.duration = maxBefore.duration();
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
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, smallerValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, DateMaxBefore.class);
            assertEquals("must have a date that is at most " + duration + " before " + moment, violation.getMessage());
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
        @DateMaxBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        Date date;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        Calendar calendar;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        Instant instant;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        LocalDateTime localDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        OffsetDateTime offsetDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "now", zoneId = "system")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithProvidedZoneId {
        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        Date date;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        Calendar calendar;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        Instant instant;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        LocalDateTime localDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        Date date;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        Calendar calendar;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        Instant instant;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        LocalDateTime localDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03", zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        Date date;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        Calendar calendar;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        Instant instant;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        LocalDateTime localDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        OffsetDateTime offsetDateTime;

        @DateMaxBefore(duration = "P1Y1M1D", moment = "2007-12-03")
        ZonedDateTime zonedDateTime;
    }
}
