/*
 * DayOfMonthNotInTest.java
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

package com.github.robtimus.validation.dayofmonth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("nls")
class DayOfMonthNotInTest extends AbstractConstraintTest {

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
                                Date.from(utcInstantAtOffset("2007-12-15T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-16T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-20T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-21T01:00:00Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2007-12-15T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-16T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-20T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-12-21T00:59:59.999Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-15T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-16T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-20T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-21T01:00:00Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-15T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-16T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-20T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-21T00:59:59.999Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Date> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-15T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-15T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-16T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-16T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-20T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-21T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-21T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-12-21T01:00:00Z", 1))
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(Date allowedValue) {
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
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-14T23:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-16T00:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-19T23:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-21T00:00:00+01:00"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-15T00:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-15T23:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-20T00:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-20T23:59:59.999+01:00"))
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-15T00:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-16T01:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-20T00:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-21T01:00:00+01:00"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-15T01:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-16T00:59:59.999+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-20T01:00:00+01:00")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-12-21T00:59:59.999+01:00"))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-15T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-16T01:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-20T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-21T01:00:00+01:00", 1))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-15T01:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-16T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-20T01:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-21T00:59:59.999+01:00", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Calendar> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-15T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-15T01:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-16T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-16T01:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-20T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-20T01:00:00+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-21T00:59:59.999+01:00", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-12-21T01:00:00+01:00", 1))
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(Calendar allowedValue) {
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
                                utcInstantAtOffset("2007-12-15T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-12-16T01:00:00Z", 1),
                                utcInstantAtOffset("2007-12-20T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-12-21T01:00:00Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffset("2007-12-15T01:00:00Z", 1),
                                utcInstantAtOffset("2007-12-16T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-12-20T01:00:00Z", 1),
                                utcInstantAtOffset("2007-12-21T00:59:59.999999999Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-15T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-16T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-20T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-21T01:00:00Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-15T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-16T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-20T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-21T00:59:59.999999999Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Instant> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-12-15T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-15T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-16T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-16T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-20T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-20T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-21T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-12-21T01:00:00Z", 1)
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(Instant allowedValue) {
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
                                LocalDate.parse("2007-12-14"),
                                LocalDate.parse("2007-12-16"),
                                LocalDate.parse("2007-12-19"),
                                LocalDate.parse("2007-12-21")
                        ),
                        Arrays.asList(
                                LocalDate.parse("2007-12-15"),
                                LocalDate.parse("2007-12-20")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalDate> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localDate",
                        Arrays.asList(
                                LocalDate.parse("2007-12-14"),
                                LocalDate.parse("2007-12-15"),
                                LocalDate.parse("2007-12-16"),
                                LocalDate.parse("2007-12-19"),
                                LocalDate.parse("2007-12-20"),
                                LocalDate.parse("2007-12-21")
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(LocalDate allowedValue) {
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
                                LocalDateTime.parse("2007-12-14T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-16T00:00:00"),
                                LocalDateTime.parse("2007-12-19T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-21T00:00:00")
                        ),
                        Arrays.asList(
                                LocalDateTime.parse("2007-12-15T00:00:00"),
                                LocalDateTime.parse("2007-12-15T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-20T00:00:00"),
                                LocalDateTime.parse("2007-12-20T23:59:59.999999999")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localDateTime",
                        Arrays.asList(
                                LocalDateTime.parse("2007-12-14T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-15T00:00:00"),
                                LocalDateTime.parse("2007-12-15T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-16T00:00:00"),
                                LocalDateTime.parse("2007-12-19T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-20T00:00:00"),
                                LocalDateTime.parse("2007-12-20T23:59:59.999999999"),
                                LocalDateTime.parse("2007-12-21T01:00:00")
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(LocalDateTime allowedValue) {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Nested
    @DisplayName("MonthDay")
    class ForMonthDay extends AbstractSystemOnlyZoneIdTest<MonthDay> {

        ForMonthDay() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "monthDay", MonthDay.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<MonthDay> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "monthDay",
                        Arrays.asList(
                                MonthDay.parse("--12-14"),
                                MonthDay.parse("--12-16"),
                                MonthDay.parse("--12-19"),
                                MonthDay.parse("--12-21")
                        ),
                        Arrays.asList(
                                MonthDay.parse("--12-15"),
                                MonthDay.parse("--12-20")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<MonthDay> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "monthDay",
                        Arrays.asList(
                                MonthDay.parse("--12-14"),
                                MonthDay.parse("--12-15"),
                                MonthDay.parse("--12-16"),
                                MonthDay.parse("--12-19"),
                                MonthDay.parse("--12-20"),
                                MonthDay.parse("--12-21")
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(MonthDay allowedValue) {
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
                                OffsetDateTime.parse("2007-12-14T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-16T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-19T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-21T01:00:00+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-15T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-15T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-20T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-20T23:59:59.999999999+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-15T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-16T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-20T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-21T01:00:00+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-12-15T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-16T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-12-20T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-12-21T00:59:59.999999999+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-15T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-16T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-20T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-21T01:00:00+01:00", 1)
                        ),
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-15T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-16T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-20T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-21T00:59:59.999999999+01:00", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<OffsetDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-12-15T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-15T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-16T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-16T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-20T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-20T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-21T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-12-21T01:00:00+01:00", 1)
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(OffsetDateTime allowedValue) {
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
                                ZonedDateTime.parse("2007-12-14T23:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-16T00:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-19T23:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-21T00:00:00+01:00")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-15T00:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-15T23:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-20T00:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-20T23:59:59.999999999+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-15T00:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-16T01:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-20T00:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-21T01:00:00+01:00")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-12-15T01:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-16T00:59:59.999999999+01:00"),
                                ZonedDateTime.parse("2007-12-20T01:00:00+01:00"),
                                ZonedDateTime.parse("2007-12-21T00:59:59.999999999+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-15T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-16T01:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-20T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-21T01:00:00+01:00", 1)
                        ),
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-15T01:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-16T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-20T01:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-21T00:59:59.999999999+01:00", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<ZonedDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-12-15T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-15T01:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-16T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-16T01:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-20T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-20T01:00:00+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-21T00:59:59.999999999+01:00", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-12-21T01:00:00+01:00", 1)
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(ZonedDateTime allowedValue) {
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
                    .getAnnotation(DayOfMonthNotIn.class)
                    .value();

            this.expectedMessage = String.format("must have a day of the month that is not one of %s", Arrays.toString(hours));
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
            assertAnnotation(violation, DayOfMonthNotIn.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        List<T> notAllowedValues() {
            return notAllowedValues;
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        Date date;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        Calendar calendar;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        Instant instant;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        LocalDate localDate;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        LocalDateTime localDateTime;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        MonthDay monthDay;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        Date date;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        Calendar calendar;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        Instant instant;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        LocalDate localDate;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        LocalDateTime localDateTime;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        MonthDay monthDay;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @DayOfMonthNotIn(value = { 15, 20 }, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @DayOfMonthNotIn({ 15, 20 })
        Date date;

        @DayOfMonthNotIn({ 15, 20 })
        Calendar calendar;

        @DayOfMonthNotIn({ 15, 20 })
        Instant instant;

        @DayOfMonthNotIn({ 15, 20 })
        LocalDate localDate;

        @DayOfMonthNotIn({ 15, 20 })
        LocalDateTime localDateTime;

        @DayOfMonthNotIn({ 15, 20 })
        MonthDay monthDay;

        @DayOfMonthNotIn({ 15, 20 })
        OffsetDateTime offsetDateTime;

        @DayOfMonthNotIn({ 15, 20 })
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithNoValues {
        @DayOfMonthNotIn({})
        Date date;

        @DayOfMonthNotIn({})
        Calendar calendar;

        @DayOfMonthNotIn({})
        Instant instant;

        @DayOfMonthNotIn({})
        LocalDate localDate;

        @DayOfMonthNotIn({})
        LocalDateTime localDateTime;

        @DayOfMonthNotIn({})
        MonthDay monthDay;

        @DayOfMonthNotIn({})
        OffsetDateTime offsetDateTime;

        @DayOfMonthNotIn({})
        ZonedDateTime zonedDateTime;
    }
}
