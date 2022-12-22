/*
 * MonthNotInTest.java
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

package com.github.robtimus.validation.month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.YearMonth;
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
class MonthNotInTest extends AbstractConstraintTest {

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
                                Date.from(utcInstantAtOffset("2007-05-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-06-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-10-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-11-01T01:00:00Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffset("2007-05-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-06-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffset("2007-10-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffset("2007-11-01T00:59:59.999Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Date> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-11-01T01:00:00Z", 1))
                        ),
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-11-01T00:59:59.999Z", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Date> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "date",
                        Arrays.asList(
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-05-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-06-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-10-01T01:00:00Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-11-01T00:59:59.999Z", 1)),
                                Date.from(utcInstantAtOffsetAfterSystem("2007-11-01T01:00:00Z", 1))
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
                                GregorianCalendar.from(ZonedDateTime.parse("2007-04-30T23:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-09-30T23:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-11-01T00:00:00+01:00[Europe/Paris]"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-31T23:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-01T00:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-01T23:59:59.999+02:00[Europe/Paris]"))
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<Calendar> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T01:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-01T01:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-11-01T01:00:00+01:00[Europe/Paris]"))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(ZonedDateTime.parse("2007-05-01T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-06-01T01:59:59.999+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-10-01T02:00:00+02:00[Europe/Paris]")),
                                GregorianCalendar.from(ZonedDateTime.parse("2007-11-01T00:59:59.999+01:00[Europe/Paris]"))
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Calendar> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-11-01T01:00:00+01:00[Europe/Paris]", 1))
                        ),
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-11-01T00:59:59.999+01:00[Europe/Paris]", 1))
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Calendar> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "calendar",
                        Arrays.asList(
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-01T00:59:59.999+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-10-01T01:00:00+02:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-11-01T00:59:59.999+01:00[Europe/Paris]", 1)),
                                GregorianCalendar.from(zonedDateTimeAtOffsetAfterSystem("2007-11-01T01:00:00+01:00[Europe/Paris]", 1))
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
                                utcInstantAtOffset("2007-05-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-06-01T01:00:00Z", 1),
                                utcInstantAtOffset("2007-10-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-11-01T01:00:00Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffset("2007-05-01T01:00:00Z", 1),
                                utcInstantAtOffset("2007-06-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffset("2007-10-01T01:00:00Z", 1),
                                utcInstantAtOffset("2007-11-01T00:59:59.999999999Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Instant> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-05-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-06-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-11-01T01:00:00Z", 1)
                        ),
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-05-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-06-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-11-01T00:59:59.999999999Z", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Instant> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "instant",
                        Arrays.asList(
                                utcInstantAtOffsetAfterSystem("2007-05-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-05-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-06-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-06-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-10-01T01:00:00Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-11-01T00:59:59.999999999Z", 1),
                                utcInstantAtOffsetAfterSystem("2007-11-01T01:00:00Z", 1)
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
                                LocalDate.parse("2007-04-30"),
                                LocalDate.parse("2007-06-01"),
                                LocalDate.parse("2007-09-30"),
                                LocalDate.parse("2007-11-01")
                        ),
                        Arrays.asList(
                                LocalDate.parse("2007-05-01"),
                                LocalDate.parse("2007-05-31"),
                                LocalDate.parse("2007-10-01"),
                                LocalDate.parse("2007-10-31")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalDate> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localDate",
                        Arrays.asList(
                                LocalDate.parse("2007-04-30"),
                                LocalDate.parse("2007-05-01"),
                                LocalDate.parse("2007-05-31"),
                                LocalDate.parse("2007-06-01"),
                                LocalDate.parse("2007-09-30"),
                                LocalDate.parse("2007-10-01"),
                                LocalDate.parse("2007-10-31"),
                                LocalDate.parse("2007-11-01")
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
                                LocalDateTime.parse("2007-04-30T23:59:59.999999999"),
                                LocalDateTime.parse("2007-06-01T00:00:00"),
                                LocalDateTime.parse("2007-09-30T23:59:59.999999999"),
                                LocalDateTime.parse("2007-11-01T00:00:00")
                        ),
                        Arrays.asList(
                                LocalDateTime.parse("2007-05-01T00:00:00"),
                                LocalDateTime.parse("2007-05-31T23:59:59.999999999"),
                                LocalDateTime.parse("2007-10-01T00:00:00"),
                                LocalDateTime.parse("2007-10-31T23:59:59.999999999")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<LocalDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "localDateTime",
                        Arrays.asList(
                                LocalDateTime.parse("2007-04-30T23:59:59.999999999"),
                                LocalDateTime.parse("2007-05-01T00:00:00"),
                                LocalDateTime.parse("2007-05-31T23:59:59.999999999"),
                                LocalDateTime.parse("2007-06-01T00:00:00"),
                                LocalDateTime.parse("2007-09-30T23:59:59.999999999"),
                                LocalDateTime.parse("2007-10-01T00:00:00"),
                                LocalDateTime.parse("2007-10-31T23:59:59.999999999"),
                                LocalDateTime.parse("2007-11-01T00:00:00")
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
    @DisplayName("Month")
    class ForMonth extends AbstractSystemOnlyZoneIdTest<Month> {

        ForMonth() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "month", Month.OCTOBER);
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<Month> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "month",
                        Arrays.asList(
                                Month.APRIL,
                                Month.JUNE,
                                Month.SEPTEMBER,
                                Month.NOVEMBER
                        ),
                        Arrays.asList(
                                Month.MAY,
                                Month.OCTOBER
                        ),
                        "must not be one of %s");
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<Month> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "month",
                        Arrays.asList(
                                Month.APRIL,
                                Month.JUNE,
                                Month.MAY,
                                Month.SEPTEMBER,
                                Month.OCTOBER,
                                Month.NOVEMBER
                        ),
                        Collections.emptyList(),
                        "must not be one of %s");
            }

            @Override
            void testNotAllowedValue(Month allowedValue) {
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
                                MonthDay.parse("--04-30"),
                                MonthDay.parse("--06-01"),
                                MonthDay.parse("--09-30"),
                                MonthDay.parse("--11-01")
                        ),
                        Arrays.asList(
                                MonthDay.parse("--05-01"),
                                MonthDay.parse("--05-31"),
                                MonthDay.parse("--10-01"),
                                MonthDay.parse("--10-31")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<MonthDay> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "monthDay",
                        Arrays.asList(
                                MonthDay.parse("--04-30"),
                                MonthDay.parse("--05-01"),
                                MonthDay.parse("--05-31"),
                                MonthDay.parse("--06-01"),
                                MonthDay.parse("--09-30"),
                                MonthDay.parse("--10-01"),
                                MonthDay.parse("--10-31"),
                                MonthDay.parse("--11-01")
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
                                OffsetDateTime.parse("2007-04-30T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-06-01T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-09-30T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-11-01T00:00:00+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-01T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-05-31T23:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-10-01T00:00:00+01:00"),
                                OffsetDateTime.parse("2007-10-31T23:59:59.999999999+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<OffsetDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-01T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-06-01T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-10-01T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-11-01T01:00:00+01:00")
                        ),
                        Arrays.asList(
                                OffsetDateTime.parse("2007-05-01T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-06-01T00:59:59.999999999+01:00"),
                                OffsetDateTime.parse("2007-10-01T01:00:00+01:00"),
                                OffsetDateTime.parse("2007-11-01T00:59:59.999999999+01:00")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<OffsetDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-11-01T01:00:00+01:00", 1)
                        ),
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-11-01T00:59:59.999999999+01:00", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<OffsetDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "offsetDateTime",
                        Arrays.asList(
                                offsetDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-10-01T01:00:00+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-11-01T00:59:59.999999999+01:00", 1),
                                offsetDateTimeAtOffsetAfterSystem("2007-11-01T01:00:00+01:00", 1)
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
    @DisplayName("YearMonth")
    class ForYearMonth extends AbstractSystemOnlyZoneIdTest<YearMonth> {

        ForYearMonth() {
            super(TestClassWithProvidedZoneId.class, TestClassWithZoneId.class, "yearMonth", YearMonth.now());
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<YearMonth> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "yearMonth",
                        Arrays.asList(
                                YearMonth.parse("2007-04"),
                                YearMonth.parse("2007-06"),
                                YearMonth.parse("2007-09"),
                                YearMonth.parse("2007-11")
                        ),
                        Arrays.asList(
                                YearMonth.parse("2007-05"),
                                YearMonth.parse("2007-10")
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<YearMonth> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "yearMonth",
                        Arrays.asList(
                                YearMonth.parse("2007-04"),
                                YearMonth.parse("2007-05"),
                                YearMonth.parse("2007-06"),
                                YearMonth.parse("2007-09"),
                                YearMonth.parse("2007-10"),
                                YearMonth.parse("2007-11")
                        ),
                        Collections.emptyList());
            }

            @Override
            void testNotAllowedValue(YearMonth allowedValue) {
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
                                ZonedDateTime.parse("2007-04-30T23:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-06-01T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-09-30T23:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-11-01T00:00:00+01:00[Europe/Paris]")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-01T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-05-31T23:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-01T00:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-01T23:59:59.999999999+02:00[Europe/Paris]")
                        ));
            }
        }

        @Nested
        @DisplayName("with zone id")
        class WithZoneId extends ConstraintTest<ZonedDateTime> {

            WithZoneId() {
                super(TestClassWithZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-01T01:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-06-01T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-01T01:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-11-01T01:00:00+01:00[Europe/Paris]")
                        ),
                        Arrays.asList(
                                ZonedDateTime.parse("2007-05-01T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-06-01T01:59:59.999999999+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-10-01T02:00:00+02:00[Europe/Paris]"),
                                ZonedDateTime.parse("2007-11-01T00:59:59.999999999+01:00[Europe/Paris]")
                        ));
            }
        }

        @Nested
        @DisplayName("with system zone id")
        class WithSystemZoneId extends ConstraintTest<ZonedDateTime> {

            WithSystemZoneId() {
                super(TestClassWithSystemZoneId.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-11-01T01:00:00+01:00[Europe/Paris]", 1)
                        ),
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-11-01T00:59:59.999999999+01:00[Europe/Paris]", 1)
                        ));
            }
        }

        @Nested
        @DisplayName("with no values")
        class WithNoValues extends ConstraintTest<ZonedDateTime> {

            WithNoValues() {
                super(TestClassWithNoValues.class, "zonedDateTime",
                        Arrays.asList(
                                zonedDateTimeAtOffsetAfterSystem("2007-05-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-05-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-06-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-06-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-01T00:59:59.999999999+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-10-01T01:00:00+02:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-11-01T00:59:59.999999999+01:00[Europe/Paris]", 1),
                                zonedDateTimeAtOffsetAfterSystem("2007-11-01T01:00:00+01:00[Europe/Paris]", 1)
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
            this(beanType, propertyName, allowedValues, notAllowedValues, "must have a month that is not one of %s");
        }

        private ConstraintTest(Class<?> beanType, String propertyName, List<T> allowedValues, List<T> notAllowedValues,
                String expectedMessageFormat) {

            this.beanType = beanType;
            this.propertyName = propertyName;
            this.allowedValues = allowedValues;
            this.notAllowedValues = notAllowedValues;
            this.clockProvider = () -> null;

            Month[] months = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(MonthNotIn.class)
                    .value();

            this.expectedMessage = String.format(expectedMessageFormat, Arrays.toString(months));
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
            assertAnnotation(violation, MonthNotIn.class);
            assertEquals(expectedMessage, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }

        List<T> notAllowedValues() {
            return notAllowedValues;
        }
    }

    private static final class TestClassWithProvidedZoneId {
        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        Date date;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        Calendar calendar;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        Instant instant;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        LocalDate localDate;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        LocalDateTime localDateTime;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        Month month;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        MonthDay monthDay;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        OffsetDateTime offsetDateTime;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        YearMonth yearMonth;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "provided")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithZoneId {
        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        Date date;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        Calendar calendar;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        Instant instant;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        LocalDate localDate;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        LocalDateTime localDateTime;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        Month month;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        MonthDay monthDay;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        OffsetDateTime offsetDateTime;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        YearMonth yearMonth;

        @MonthNotIn(value = { Month.MAY, Month.OCTOBER }, zoneId = "UTC")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithSystemZoneId {
        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        Date date;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        Calendar calendar;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        Instant instant;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        LocalDate localDate;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        LocalDateTime localDateTime;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        Month month;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        MonthDay monthDay;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        OffsetDateTime offsetDateTime;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        YearMonth yearMonth;

        @MonthNotIn({ Month.MAY, Month.OCTOBER })
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithNoValues {
        @MonthNotIn({})
        Date date;

        @MonthNotIn({})
        Calendar calendar;

        @MonthNotIn({})
        Instant instant;

        @MonthNotIn({})
        LocalDate localDate;

        @MonthNotIn({})
        LocalDateTime localDateTime;

        @MonthNotIn({})
        Month month;

        @MonthNotIn({})
        MonthDay monthDay;

        @MonthNotIn({})
        OffsetDateTime offsetDateTime;

        @MonthNotIn({})
        YearMonth yearMonth;

        @MonthNotIn({})
        ZonedDateTime zonedDateTime;
    }
}
