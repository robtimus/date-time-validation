/*
 * DayOfWeekInTest.java
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

package com.github.robtimus.validation.dayofweek;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
class DayOfWeekInTest extends AbstractConstraintTest {

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
                                Date.from(utcInstantAtOffset("2007-05-17T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-05-18T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-10-13T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-10-14T00:59:59.999Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2007-05-17T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-05-18T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-10-13T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-10-14T01:00:00Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-17T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-18T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-13T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-14T00:59:59.999Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-17T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-18T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-13T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-14T01:00:00Z", 1))
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
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-17T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-17T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-18T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-18T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-13T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-13T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-14T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-14T01:00:00Z", 1))
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
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-17T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-17T23:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-13T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-13T23:59:59.999+02:00[Europe/Paris]"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-16T23:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-18T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-12T23:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-14T00:00:00+02:00[Europe/Paris]"))
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-17T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-18T01:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-13T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-14T01:59:59.999+02:00[Europe/Paris]"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-17T01:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-18T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-13T01:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-14T02:00:00+02:00[Europe/Paris]"))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-17T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-18T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-13T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-14T00:59:59.999+02:00[Europe/Paris]", 1))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-17T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-18T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-13T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-14T01:00:00+02:00[Europe/Paris]", 1))
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
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-17T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-17T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-18T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-18T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-13T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-13T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-14T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-14T01:00:00+02:00[Europe/Paris]", 1))
                        ));
            }

            @Override
            void testAllowedValue(Calendar allowedValue) {
                throw new UnsupportedOperationException();
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
                        Arrays.asList(
                                DayOfWeek.THURSDAY,
                                DayOfWeek.SATURDAY
                        ),
                        Arrays.asList(
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.FRIDAY,
                                DayOfWeek.SUNDAY
                        ),
                        "must be one of %s");
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<DayOfWeek> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "dayOfWeek",
                        Collections.emptyList(),
                        Arrays.asList(
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.THURSDAY,
                                DayOfWeek.FRIDAY,
                                DayOfWeek.SATURDAY,
                                DayOfWeek.SUNDAY
                        ),
                        "must be one of %s");
            }

            @Override
            void testAllowedValue(DayOfWeek allowedValue) {
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
                                utcInstantAtOffset("2007-05-17T01:00:00Z", 1),
                                utcInstantAtOffset("2007-05-18T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-10-13T01:00:00Z", 1),
                                utcInstantAtOffset("2007-10-14T00:59:59.999999999Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffset("2007-05-17T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-05-18T01:00:00Z", 1),
                                utcInstantAtOffset("2007-10-13T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-10-14T01:00:00Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-05-17T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-05-18T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-13T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-14T00:59:59.999999999Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-05-17T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-05-18T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-13T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-14T01:00:00Z", 1)
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
                                utcInstantAtOffsetAfterSystem("2007-05-17T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-05-17T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-05-18T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-05-18T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-13T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-13T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-14T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-14T01:00:00Z", 1)
                        ));
            }

            @Override
            void testAllowedValue(Instant allowedValue) {
                throw new UnsupportedOperationException();
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
                        Arrays.asList(
                                LocalDate.parse("2007-05-17"),
                                LocalDate.parse("2007-10-13")
                        ),
                        Arrays.asList(
                                LocalDate.parse("2007-05-16"),
                                LocalDate.parse("2007-05-18"),
                                LocalDate.parse("2007-10-12"),
                                LocalDate.parse("2007-10-14")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalDate> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localDate",
                        Collections.emptyList(),
                        Arrays.asList(
                                LocalDate.parse("2007-05-16"),
                                LocalDate.parse("2007-05-17"),
                                LocalDate.parse("2007-05-18"),
                                LocalDate.parse("2007-10-12"),
                                LocalDate.parse("2007-10-13"),
                                LocalDate.parse("2007-10-14")
                        ));
            }

            @Override
            void testAllowedValue(LocalDate allowedValue) {
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
                                LocalDateTime.parse("2007-05-17T00:00:00"),
                                LocalDateTime.parse("2007-05-17T23:59:59.999999999"),
                                LocalDateTime.parse("2007-10-13T00:00:00"),
                                LocalDateTime.parse("2007-10-13T23:59:59.999999999")
                        ),
                        Arrays.asList(
                                LocalDateTime.parse("2007-05-16T23:59:59.999999999"),
                                LocalDateTime.parse("2007-05-18T00:00:00"),
                                LocalDateTime.parse("2007-10-12T23:59:59.999999999"),
                                LocalDateTime.parse("2007-10-14T00:00:00")
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
                                LocalDateTime.parse("2007-05-16T23:59:59.999999999"),
                                LocalDateTime.parse("2007-05-17T00:00:00"),
                                LocalDateTime.parse("2007-05-17T23:59:59.999999999"),
                                LocalDateTime.parse("2007-05-18T00:00:00"),
                                LocalDateTime.parse("2007-10-12T23:59:59.999999999"),
                                LocalDateTime.parse("2007-10-13T00:00:00"),
                                LocalDateTime.parse("2007-10-13T23:59:59.999999999"),
                                LocalDateTime.parse("2007-10-14T00:00:00")
                        ));
            }

            @Override
            void testAllowedValue(LocalDateTime allowedValue) {
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
                                OffsetDateTime.parse("2007-05-17T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-05-17T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-10-13T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-10-13T23:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-16T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-05-18T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-10-12T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-10-14T00:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-17T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-05-18T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-10-13T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-10-14T00:59:59.999999999+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-17T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-05-18T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-10-13T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-10-14T01:00:00+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-05-17T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-05-18T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-13T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-14T00:59:59.999999999+01:00", 1)
                        ),
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-05-17T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-05-18T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-13T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-14T01:00:00+01:00", 1)
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
                                offsetDateTimeAtOffsetAfterSystem("2007-05-17T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-05-17T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-05-18T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-05-18T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-13T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-13T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-14T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-14T01:00:00+01:00", 1)
                        ));
            }

            @Override
            void testAllowedValue(OffsetDateTime allowedValue) {
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
                                ZonedDateTime.parse("2007-05-17T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-05-17T23:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-13T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-13T23:59:59.999999999+02:00[Europe/Paris]")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-16T23:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-05-18T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-12T23:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-14T00:00:00+02:00[Europe/Paris]")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-17T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-05-18T01:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-13T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-14T01:59:59.999999999+02:00[Europe/Paris]")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-17T01:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-05-18T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-13T01:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-14T02:00:00+02:00[Europe/Paris]")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-05-17T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-05-18T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-13T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-14T00:59:59.999999999+02:00[Europe/Paris]", 1)
                        ),
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-05-17T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-05-18T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-13T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-14T01:00:00+02:00[Europe/Paris]", 1)
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
                                zonedDateTimeAtOffsetAfterSystem("2007-05-17T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-05-17T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-05-18T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-05-18T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-13T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-13T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-14T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-14T01:00:00+02:00[Europe/Paris]", 1)
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

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> allowedValues, List<T> invalidValues) {
            this(beanType, propertyName, allowedValues, invalidValues, "must have a day of the week that is one of %s");
        }

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> allowedValues, List<T> notAllowedValues,
                String expectedMessageFormat) {

            this.beanType = beanType;
            this.propertyName = propertyName;
            this.allowedValues = allowedValues;
            this.notAllowedValues = notAllowedValues;
            this.clockProvider = () -> null;

            DayOfWeek[] daysOfWeek = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(DayOfWeekIn.class)
                    .value();

            this.expectedMessage = String.format(expectedMessageFormat, Arrays.toString(daysOfWeek));
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
            assertAnnotation(violation, DayOfWeekIn.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        List<T> notAllowedValues() {
            return notAllowedValues;
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        Date date;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        Calendar calendar;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        DayOfWeek dayOfWeek;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        Instant instant;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        LocalDate localDate;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        LocalDateTime localDateTime;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        Date date;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        Calendar calendar;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        DayOfWeek dayOfWeek;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        Instant instant;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        LocalDate localDate;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        LocalDateTime localDateTime;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @DayOfWeekIn(value = { DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        Date date;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        Calendar calendar;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        DayOfWeek dayOfWeek;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        Instant instant;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        LocalDate localDate;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        LocalDateTime localDateTime;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        OffsetDateTime offsetDateTime;

        @DayOfWeekIn({ DayOfWeek.THURSDAY, DayOfWeek.SATURDAY })
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithNoValues {
        @DayOfWeekIn({})
        Date date;

        @DayOfWeekIn({})
        Calendar calendar;

        @DayOfWeekIn({})
        DayOfWeek dayOfWeek;

        @DayOfWeekIn({})
        Instant instant;

        @DayOfWeekIn({})
        LocalDate localDate;

        @DayOfWeekIn({})
        LocalDateTime localDateTime;

        @DayOfWeekIn({})
        OffsetDateTime offsetDateTime;

        @DayOfWeekIn({})
        ZonedDateTime zonedDateTime;
    }
}
