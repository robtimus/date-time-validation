/*
 * HourInTest.java
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

package com.github.robtimus.validation.hour;

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
class HourInTest extends AbstractConstraintTest {

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
                                Date.from(utcInstantAtOffset("2007-12-03T16:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-03T16:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-03T21:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-03T21:59:59.999Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2007-12-03T15:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-03T17:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-03T20:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-03T22:00:00Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T16:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T16:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T21:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T21:59:59.999Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T15:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T17:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T20:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T22:00:00Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Date> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "date",
                        Collections.emptyList(),
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T15:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T16:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T16:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T17:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T20:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T21:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T21:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-03T22:00:00Z", 1))
                        ));
            }

            @Override
            void testAllowedValue(Date allowedValue) {
                throw new UnsupportedOperationException();
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
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T15:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T15:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T20:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T20:59:59.999+01:00"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T14:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T16:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T19:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T21:00:00+01:00"))
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T16:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T16:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T21:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T21:59:59.999+01:00"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T15:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T17:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T20:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T22:00:00+01:00"))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:59:59.999+01:00", 1))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T15:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T17:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T20:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T22:00:00+01:00", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Calendar> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "calendar",
                        Collections.emptyList(),
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T15:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T17:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T20:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-03T22:00:00+01:00", 1))
                        ));
            }

            @Override
            void testAllowedValue(Calendar allowedValue) {
                throw new UnsupportedOperationException();
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
                                utcInstantAtOffset("2007-12-03T16:00:00Z", 1),
                                utcInstantAtOffset("2007-12-03T16:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-12-03T21:00:00Z", 1),
                                utcInstantAtOffset("2007-12-03T21:59:59.999999999Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffset("2007-12-03T15:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-12-03T17:00:00Z", 1),
                                utcInstantAtOffset("2007-12-03T20:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-12-03T22:00:00Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-03T16:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T16:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T21:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T21:59:59.999999999Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-03T15:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T17:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T20:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T22:00:00Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Instant> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "instant",
                        Collections.emptyList(),
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-03T15:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T16:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T16:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T17:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T20:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T21:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T21:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-03T22:00:00Z", 1)
                        ));
            }

            @Override
            void testAllowedValue(Instant allowedValue) {
                throw new UnsupportedOperationException();
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
                                LocalDateTime.parse("2007-12-03T15:00:00"),
                                LocalDateTime.parse("2007-12-03T15:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T20:00:00"),
                                LocalDateTime.parse("2007-12-03T20:59:59.999999999")
                        ),
                        Arrays.asList(
                                LocalDateTime.parse("2007-12-03T14:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T16:00:00"),
                                LocalDateTime.parse("2007-12-03T19:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T21:00:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localDateTime",
                        Collections.emptyList(),
                        Arrays.asList(
                                LocalDateTime.parse("2007-12-03T14:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T15:00:00"),
                                LocalDateTime.parse("2007-12-03T15:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T16:00:00"),
                                LocalDateTime.parse("2007-12-03T19:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T20:00:00"),
                                LocalDateTime.parse("2007-12-03T20:59:59.999999999"),
                                LocalDateTime.parse("2007-12-03T21:00:00")
                        ));
            }

            @Override
            void testAllowedValue(LocalDateTime allowedValue) {
                throw new UnsupportedOperationException();
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
                                LocalTime.parse("15:00:00"),
                                LocalTime.parse("15:59:59.999999999"),
                                LocalTime.parse("20:00:00"),
                                LocalTime.parse("20:59:59.999999999")
                        ),
                        Arrays.asList(
                                LocalTime.parse("14:59:59.999999999"),
                                LocalTime.parse("16:00:00"),
                                LocalTime.parse("19:59:59.999999999"),
                                LocalTime.parse("21:00:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localTime",
                        Collections.emptyList(),
                        Arrays.asList(
                                LocalTime.parse("14:59:59.999999999"),
                                LocalTime.parse("15:00:00"),
                                LocalTime.parse("15:59:59.999999999"),
                                LocalTime.parse("16:00:00"),
                                LocalTime.parse("19:59:59.999999999"),
                                LocalTime.parse("20:00:00"),
                                LocalTime.parse("20:59:59.999999999"),
                                LocalTime.parse("21:00:00")
                        ));
            }

            @Override
            void testAllowedValue(LocalTime allowedValue) {
                throw new UnsupportedOperationException();
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
                                OffsetDateTime.parse("2007-12-03T15:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-03T15:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-03T20:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-03T20:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-03T14:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-03T16:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-03T19:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-03T21:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-03T16:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-03T16:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-03T21:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-03T21:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-03T15:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-03T17:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-03T20:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-03T22:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T16:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T16:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T21:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T21:59:59.999999999+01:00", 1)
                        ),
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T15:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T17:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T20:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T22:00:00+01:00", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<OffsetDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "offsetDateTime",
                        Collections.emptyList(),
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T15:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T16:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T16:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T17:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T20:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T21:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T21:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-03T22:00:00+01:00", 1)
                        ));
            }

            @Override
            void testAllowedValue(OffsetDateTime allowedValue) {
                throw new UnsupportedOperationException();
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
                                OffsetTime.parse("15:00:00+01:00"),
                                OffsetTime.parse("15:59:59.999999999+01:00"),
                                OffsetTime.parse("20:00:00+01:00"),
                                OffsetTime.parse("20:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                OffsetTime.parse("14:59:59.999999999+01:00"),
                                OffsetTime.parse("16:00:00+01:00"),
                                OffsetTime.parse("19:59:59.999999999+01:00"),
                                OffsetTime.parse("21:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetTime",
                        Arrays.asList(
                                OffsetTime.parse("16:00:00+01:00"),
                                OffsetTime.parse("16:59:59.999999999+01:00"),
                                OffsetTime.parse("21:00:00+01:00"),
                                OffsetTime.parse("21:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                OffsetTime.parse("15:59:59.999999999+01:00"),
                                OffsetTime.parse("17:00:00+01:00"),
                                OffsetTime.parse("20:59:59.999999999+01:00"),
                                OffsetTime.parse("22:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetTime",
                        Arrays.asList(
                                offsetTimeAtOffsetAfterSystem("16:00:00+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("16:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("21:00:00+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("21:59:59.999999999+01:00", 1)
                        ),
                        Arrays.asList(
                                offsetTimeAtOffsetAfterSystem("15:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("17:00:00+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("20:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("22:00:00+01:00", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<OffsetTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "offsetTime",
                        Collections.emptyList(),
                        Arrays.asList(
                                offsetTimeAtOffsetAfterSystem("15:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("16:00:00+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("16:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("17:00:00+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("20:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("21:00:00+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("21:59:59.999999999+01:00", 1),
                                offsetTimeAtOffsetAfterSystem("22:00:00+01:00", 1)
                        ));
            }

            @Override
            void testAllowedValue(OffsetTime allowedValue) {
                throw new UnsupportedOperationException();
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
                                ZonedDateTime.parse("2007-12-03T15:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-03T15:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-03T20:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-03T20:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-03T14:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-03T16:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-03T19:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-03T21:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-03T16:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-03T16:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-03T21:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-03T21:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-03T15:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-03T17:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-03T20:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-03T22:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:59:59.999999999+01:00", 1)
                        ),
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T15:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T17:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T20:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T22:00:00+01:00", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<ZonedDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "zonedDateTime",
                        Collections.emptyList(),
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T15:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T16:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T17:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T20:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T21:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-03T22:00:00+01:00", 1)
                        ));
            }

            @Override
            void testAllowedValue(ZonedDateTime allowedValue) {
                throw new UnsupportedOperationException();
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

            int[] hours = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(HourIn.class)
                    .value();

            this.expectedMessage = String.format("must have an hour that is one of %s", Arrays.toString(hours));
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
            assertAnnotation(violation, HourIn.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        List<T> notAllowedValues() {
            return notAllowedValues;
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @HourIn(value = { 15, 20 }, zoneId = "provided")
        Date date;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        Calendar calendar;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        Instant instant;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        LocalDateTime localDateTime;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        LocalTime localTime;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        OffsetTime offsetTime;

        @HourIn(value = { 15, 20 }, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        Date date;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        Calendar calendar;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        Instant instant;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        LocalDateTime localDateTime;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        LocalTime localTime;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        OffsetTime offsetTime;

        @HourIn(value = { 15, 20 }, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @HourIn({ 15, 20 })
        Date date;

        @HourIn({ 15, 20 })
        Calendar calendar;

        @HourIn({ 15, 20 })
        Instant instant;

        @HourIn({ 15, 20 })
        LocalDateTime localDateTime;

        @HourIn({ 15, 20 })
        LocalTime localTime;

        @HourIn({ 15, 20 })
        OffsetDateTime offsetDateTime;

        @HourIn({ 15, 20 })
        OffsetTime offsetTime;

        @HourIn({ 15, 20 })
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithNoValues {
        @HourIn({})
        Date date;

        @HourIn({})
        Calendar calendar;

        @HourIn({})
        Instant instant;

        @HourIn({})
        LocalDateTime localDateTime;

        @HourIn({})
        LocalTime localTime;

        @HourIn({})
        OffsetDateTime offsetDateTime;

        @HourIn({})
        OffsetTime offsetTime;

        @HourIn({})
        ZonedDateTime zonedDateTime;
    }
}
