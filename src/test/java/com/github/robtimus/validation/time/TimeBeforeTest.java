/*
 * TimeBeforeTest.java
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
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
class TimeBeforeTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Date> {

            WithNow() {
                super(TestClassWithNow.class, "date",
                        Date.from(utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z")),
                        Date.from(utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").minusMillis(1)),
                        Date.from(utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").plusMillis(1)),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Date> {

            WithMoment() {
                super(TestClassWithMoment.class, "date",
                        Date.from(utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z")),
                        Date.from(utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").minusMillis(1)),
                        Date.from(utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").plusMillis(1)),
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
                        GregorianCalendar.from(ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").plus(1, ChronoUnit.MILLIS)),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithMoment.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").plus(1, ChronoUnit.MILLIS)),
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
                        utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z"),
                        utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").minusNanos(1),
                        utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Instant> {

            WithMoment() {
                super(TestClassWithMoment.class, "instant",
                        utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z"),
                        utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").minusNanos(1),
                        utcInstantAtDefaultZone("2021-10-24T10:15:30.00Z").plusNanos(1),
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
                        LocalDateTime.parse("2021-10-24T10:15:30"),
                        LocalDateTime.parse("2021-10-24T10:15:30").minusNanos(1),
                        LocalDateTime.parse("2021-10-24T10:15:30").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "localDateTime",
                        LocalDateTime.parse("2021-10-24T10:15:30"),
                        LocalDateTime.parse("2021-10-24T10:15:30").minusNanos(1),
                        LocalDateTime.parse("2021-10-24T10:15:30").plusNanos(1),
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
                        OffsetDateTime.parse("2021-10-24T10:15:30+01:00"),
                        OffsetDateTime.parse("2021-10-24T10:15:30+01:00").minusNanos(1),
                        OffsetDateTime.parse("2021-10-24T10:15:30+01:00").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "offsetDateTime",
                        OffsetDateTime.parse("2021-10-24T10:15:30+01:00"),
                        OffsetDateTime.parse("2021-10-24T10:15:30+01:00").minusNanos(1),
                        OffsetDateTime.parse("2021-10-24T10:15:30+01:00").plusNanos(1),
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
                        ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").minusNanos(1),
                        ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").plusNanos(1),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "zonedDateTime",
                        ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").minusNanos(1),
                        ZonedDateTime.parse("2021-10-24T10:15:30+01:00[Europe/Paris]").plusNanos(1),
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
                    .getAnnotation(TimeBefore.class)
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
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, exactValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, TimeBefore.class);
            assertEquals("must have a time that is before " + moment, violation.getMessage());
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
            assertAnnotation(violation, TimeBefore.class);
            assertEquals("must have a time that is before " + moment, violation.getMessage());
        }
    }

    private static final class TestClassWithNow {
        @TimeBefore(moment = "now")
        Date date;

        @TimeBefore(moment = "now")
        Calendar calendar;

        @TimeBefore(moment = "now")
        Instant instant;

        @TimeBefore(moment = "now")
        LocalDateTime localDateTime;

        @TimeBefore(moment = "now")
        OffsetDateTime offsetDateTime;

        @TimeBefore(moment = "now")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithMoment {
        @TimeBefore(moment = "10:15:30")
        Date date;

        @TimeBefore(moment = "10:15:30")
        Calendar calendar;

        @TimeBefore(moment = "10:15:30")
        Instant instant;

        @TimeBefore(moment = "10:15:30")
        LocalDateTime localDateTime;

        @TimeBefore(moment = "10:15:30")
        OffsetDateTime offsetDateTime;

        @TimeBefore(moment = "10:15:30")
        ZonedDateTime zonedDateTime;
    }
}
