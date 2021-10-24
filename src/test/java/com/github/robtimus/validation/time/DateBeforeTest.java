/*
 * DateBeforeTest.java
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
class DateBeforeTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Date> {

            WithNow() {
                super(TestClassWithNow.class, "date",
                        Date.from(Instant.parse("2007-12-03T13:50:15.00Z")),
                        Date.from(Instant.parse("2007-12-03T13:50:15.00Z").minus(24, ChronoUnit.HOURS)),
                        Date.from(Instant.parse("2007-12-03T13:50:15.00Z").plus(24, ChronoUnit.HOURS)),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Date> {

            WithMoment() {
                super(TestClassWithMoment.class, "date",
                        Date.from(Instant.parse("2007-12-03T13:50:15.00Z")),
                        Date.from(Instant.parse("2007-12-03T13:50:15.00Z").minus(24, ChronoUnit.HOURS)),
                        Date.from(Instant.parse("2007-12-03T13:50:15.00Z").plus(24, ChronoUnit.HOURS)),
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
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").minusDays(1)),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").plusDays(1)),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithMoment.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").minusDays(1)),
                        GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").plusDays(1)),
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
                        Instant.parse("2007-12-03T13:50:15.00Z"),
                        Instant.parse("2007-12-03T13:50:15.00Z").minus(24, ChronoUnit.HOURS),
                        Instant.parse("2007-12-03T13:50:15.00Z").plus(24, ChronoUnit.HOURS),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Instant> {

            WithMoment() {
                super(TestClassWithMoment.class, "instant",
                        Instant.parse("2007-12-03T13:50:15.00Z"),
                        Instant.parse("2007-12-03T13:50:15.00Z").minus(24, ChronoUnit.HOURS),
                        Instant.parse("2007-12-03T13:50:15.00Z").plus(24, ChronoUnit.HOURS),
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
                        LocalDateTime.parse("2007-12-03T13:50:15"),
                        LocalDateTime.parse("2007-12-03T13:50:15").minusDays(1),
                        LocalDateTime.parse("2007-12-03T13:50:15").plusDays(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "localDateTime",
                        LocalDateTime.parse("2007-12-03T13:50:15"),
                        LocalDateTime.parse("2007-12-03T13:50:15").minusDays(1),
                        LocalDateTime.parse("2007-12-03T13:50:15").plusDays(1),
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
                        OffsetDateTime.parse("2007-12-03T13:50:15+01:00"),
                        OffsetDateTime.parse("2007-12-03T13:50:15+01:00").minusDays(1),
                        OffsetDateTime.parse("2007-12-03T13:50:15+01:00").plusDays(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "offsetDateTime",
                        OffsetDateTime.parse("2007-12-03T10:15:30+01:00"),
                        OffsetDateTime.parse("2007-12-03T13:50:15+01:00").minusDays(1),
                        OffsetDateTime.parse("2007-12-03T13:50:15+01:00").plusDays(1),
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
                        ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").minusDays(1),
                        ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").plusDays(1),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "zonedDateTime",
                        ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").minusDays(1),
                        ZonedDateTime.parse("2007-12-03T13:50:15+01:00[Europe/Paris]").plusDays(1),
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
                    .getAnnotation(DateBefore.class)
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
            assertAnnotation(violation, DateBefore.class);
            assertEquals("must have a date that is before " + moment, violation.getMessage());
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
            assertAnnotation(violation, DateBefore.class);
            assertEquals("must have a date that is before " + moment, violation.getMessage());
        }
    }

    private static final class TestClassWithNow {
        @DateBefore(moment = "now")
        Date date;

        @DateBefore(moment = "now")
        Calendar calendar;

        @DateBefore(moment = "now")
        Instant instant;

        @DateBefore(moment = "now")
        LocalDateTime localDateTime;

        @DateBefore(moment = "now")
        OffsetDateTime offsetDateTime;

        @DateBefore(moment = "now")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithMoment {
        @DateBefore(moment = "2007-12-03")
        Date date;

        @DateBefore(moment = "2007-12-03")
        Calendar calendar;

        @DateBefore(moment = "2007-12-03")
        Instant instant;

        @DateBefore(moment = "2007-12-03")
        LocalDateTime localDateTime;

        @DateBefore(moment = "2007-12-03")
        OffsetDateTime offsetDateTime;

        @DateBefore(moment = "2007-12-03")
        ZonedDateTime zonedDateTime;
    }
}