/*
 * MinuteIsTest.java
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

package com.github.robtimus.validation.minute;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
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
class MinuteIsTest extends AbstractConstraintTest {

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
                                Date.from(utcInstantAtOffset("2007-12-03T10:45:00Z", 30)),
                                Date.from(utcInstantAtOffset("2007-12-03T10:45:59.999Z", 30))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2007-12-03T10:44:59.999Z", 30)),
                                Date.from(utcInstantAtOffset("2007-12-03T10:46:00Z", 30))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T10:45:00Z", 30)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T10:45:59.999Z", 30))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T10:44:59.999Z", 30)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T10:46:00Z", 30))
                        ));
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
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:00+05:30")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:59.999+05:30"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:14:59.999+05:30")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:16:00+05:30"))
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:45:00+05:30")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:45:59.999+05:30"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:44:59.999+05:30")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:46:00+05:30"))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:45:00+05:30", 30)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:45:59.999+05:30", 30))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:44:59.999+05:30", 30)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:46:00+05:30", 30))
                        ));
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
                                utcInstantAtOffset("2007-12-03T10:45:00Z", 30),
                                utcInstantAtOffset("2007-12-03T10:45:59.999999999Z", 30)
                        ),
                        Arrays.asList(
                                utcInstantAtOffset("2007-12-03T10:44:59.999999999Z", 30),
                                utcInstantAtOffset("2007-12-03T10:46:00Z", 30)
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-03T10:45:00Z", 30),
                                utcInstantAtOffsetAfterSystem("2007-12-03T10:45:59.999999999Z", 30)
                        ),
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-03T10:44:59.999999999Z", 30),
                                utcInstantAtOffsetAfterSystem("2007-12-03T10:46:00Z", 30)
                        ));
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
                                LocalDateTime.parse("2007-12-03T10:15:00"),
                                LocalDateTime.parse("2007-12-03T10:15:59.999999999")
                        ),
                        Arrays.asList(
                                LocalDateTime.parse("2007-12-03T10:14:59.999999999"),
                                LocalDateTime.parse("2007-12-03T10:16:00")
                        ));
            }
        }
    }

    @Nested
    @DisplayName("LocalTime")
    class ForLocalTime extends AbstractSystemOnlyZoneIdTest<LocalTime> {

        ForLocalTime() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "localTime", LocalTime.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<LocalTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "localTime",
                        Arrays.asList(
                                LocalTime.parse("10:15:00"),
                                LocalTime.parse("10:15:59.999999999")
                        ),
                        Arrays.asList(
                                LocalTime.parse("10:14:59.999999999"),
                                LocalTime.parse("10:16:00")
                        ));
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
                                OffsetDateTime.parse("2007-12-03T10:15:00+05:30"),
                                OffsetDateTime.parse("2007-12-03T10:15:59.999999999+05:30")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-03T10:14:59.999999999+05:30"),
                                OffsetDateTime.parse("2007-12-03T10:16:00+05:30")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-03T10:45:00+05:30"),
                                OffsetDateTime.parse("2007-12-03T10:45:59.999999999+05:30")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-03T10:44:59.999999999+05:30"),
                                OffsetDateTime.parse("2007-12-03T10:46:00+05:30")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T10:45:00+05:30", 30),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T10:45:59.999999999+05:30", 30)
                        ),
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T10:44:59.999999999+05:30", 30),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T10:46:00+05:30", 30)
                        ));
            }
        }
    }

    @Nested
    @DisplayName("OffsetTime")
    class ForOffsetTime {

        @Nested
        @DisplayName("with provided zone id")
        class WithProvidedZoneId extends ConstraintTest<OffsetTime> {

            WithProvidedZoneId() {
                super(TestClassWithProvidedZoneId.class, "offsetTime",
                        Arrays.asList(
                                OffsetTime.parse("10:15:00+05:30"),
                                OffsetTime.parse("10:15:59.999999999+05:30")
                        ),
                        Arrays.asList(
                                OffsetTime.parse("10:14:59.999999999+05:30"),
                                OffsetTime.parse("10:16:00+05:30")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetTime",
                        Arrays.asList(
                                OffsetTime.parse("10:45:00+05:30"),
                                OffsetTime.parse("10:45:59.999999999+05:30")
                        ),
                        Arrays.asList(
                                OffsetTime.parse("10:44:59.999999999+05:30"),
                                OffsetTime.parse("10:46:00+05:30")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetTime",
                        Arrays.asList(
                                offsetTimeAtOffsetAfterSystem("10:45:00+05:30", 30),
                                offsetTimeAtOffsetAfterSystem("10:45:59.999999999+05:30", 30)
                        ),
                        Arrays.asList(
                                offsetTimeAtOffsetAfterSystem("10:44:59.999999999+05:30", 30),
                                offsetTimeAtOffsetAfterSystem("10:46:00+05:30", 30)
                        ));
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
                                ZonedDateTime.parse("2007-12-03T10:15:00+05:30"),
                                ZonedDateTime.parse("2007-12-03T10:15:59.999999999+05:30")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-03T10:14:59.999999999+05:30"),
                                ZonedDateTime.parse("2007-12-03T10:16:00+05:30")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-03T10:45:00+05:30"),
                                ZonedDateTime.parse("2007-12-03T10:45:59.999999999+05:30")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-03T10:44:59.999999999+05:30"),
                                ZonedDateTime.parse("2007-12-03T10:46:00+05:30")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:45:00+05:30", 30),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:45:59.999999999+05:30", 30)
                        ),
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:44:59.999999999+05:30", 30),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T10:46:00+05:30", 30)
                        ));
            }
        }
    }

    @TestInstance(Lifecycle.PER_CLASS)
    private abstract static class ConstraintTest<T> extends AbstractConstraintTest {

        private final Class<?> beanType;
        private final String propertyName;
        private final List<T> allowedValues;
        private final List<T> notAllowedValues;
        private final ClockProvider clockProvider;
        private final String expectedMessage;

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> allowedValues, List<T> notAllowedValues) {
            this.beanType = beanType;
            this.propertyName = propertyName;
            this.allowedValues = allowedValues;
            this.notAllowedValues = notAllowedValues;
            this.clockProvider = () -> null;

            int minute = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(MinuteIs.class)
                    .value();

            this.expectedMessage = String.format("must have a minute that is %d", minute);
        }

        @Test
        @DisplayName("null")
        void testNull() {
            List<?> violations = validate(clockProvider, beanType, propertyName, null);
            assertEquals(Collections.emptyList(), violations);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("allowedValues")
        @DisplayName("allowed value")
        void testAllowedValue(T allowedValue) {
            List<?> violations = validate(clockProvider, beanType, propertyName, allowedValue);
            assertEquals(Collections.emptyList(), violations);
        }

        List<T> allowedValues() {
            return allowedValues;
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("notAllowedValues")
        @DisplayName("not allowed value")
        void testNotAllowedValue(T notAllowedValue) {
            List<? extends ConstraintViolation<?>> violations = validate(clockProvider, beanType, propertyName, notAllowedValue);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, MinuteIs.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        List<T> notAllowedValues() {
            return notAllowedValues;
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @MinuteIs(value = 15, zoneId = "provided")
        Date date;

        @MinuteIs(value = 15, zoneId = "provided")
        Calendar calendar;

        @MinuteIs(value = 15, zoneId = "provided")
        Instant instant;

        @MinuteIs(value = 15, zoneId = "provided")
        LocalDateTime localDateTime;

        @MinuteIs(value = 15, zoneId = "provided")
        LocalTime localTime;

        @MinuteIs(value = 15, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @MinuteIs(value = 15, zoneId = "provided")
        OffsetTime offsetTime;

        @MinuteIs(value = 15, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @MinuteIs(value = 15, zoneId = "UTC")
        Date date;

        @MinuteIs(value = 15, zoneId = "UTC")
        Calendar calendar;

        @MinuteIs(value = 15, zoneId = "UTC")
        Instant instant;

        @MinuteIs(value = 15, zoneId = "UTC")
        LocalDateTime localDateTime;

        @MinuteIs(value = 15, zoneId = "UTC")
        LocalTime localTime;

        @MinuteIs(value = 15, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @MinuteIs(value = 15, zoneId = "UTC")
        OffsetTime offsetTime;

        @MinuteIs(value = 15, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @MinuteIs(15)
        Date date;

        @MinuteIs(15)
        Calendar calendar;

        @MinuteIs(15)
        Instant instant;

        @MinuteIs(15)
        LocalDateTime localDateTime;

        @MinuteIs(15)
        LocalTime localTime;

        @MinuteIs(15)
        OffsetDateTime offsetDateTime;

        @MinuteIs(15)
        OffsetTime offsetTime;

        @MinuteIs(15)
        ZonedDateTime zonedDateTime;
    }
}
