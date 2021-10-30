/*
 * MaxAfterTest.java
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.github.robtimus.validation.time.validators.MaxAfterValidator;

@SuppressWarnings("nls")
class MaxAfterTest extends AbstractConstraintTest {

    @Nested
    @DisplayName("Date")
    class ForDate {

        @Nested
        @DisplayName("with 'now'")
        class WithNow {

            @Nested
            @DisplayName("with period and duration")
            class WithPeriodAndDuration extends ConstraintTest<Date> {

                WithPeriodAndDuration() {
                    super(TestClassWithNow.class, "date",
                            Date.from(Instant.parse("2009-01-04T11:16:31.00Z")),
                            Date.from(Instant.parse("2009-01-04T11:16:31.00Z").minusMillis(1)),
                            Date.from(Instant.parse("2009-01-04T11:16:31.00Z").plusMillis(1)),
                            () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
                }
            }

            @Nested
            @DisplayName("with only duration")
            class WithDuration extends ConstraintTest<Date> {

                WithDuration() {
                    super(TestClassWithNow.class, "dateWithoutPeriod",
                            Date.from(Instant.parse("2007-12-03T11:16:31.00Z")),
                            Date.from(Instant.parse("2007-12-03T11:16:31.00Z").minusMillis(1)),
                            Date.from(Instant.parse("2007-12-03T11:16:31.00Z").plusMillis(1)),
                            () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
                }
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment {

            @Nested
            @DisplayName("with period and duration")
            class WithPeriodAndDuration extends ConstraintTest<Date> {

                WithPeriodAndDuration() {
                    super(TestClassWithMoment.class, "date",
                            Date.from(Instant.parse("2009-01-04T11:16:31.00Z")),
                            Date.from(Instant.parse("2009-01-04T11:16:31.00Z").minusMillis(1)),
                            Date.from(Instant.parse("2009-01-04T11:16:31.00Z").plusMillis(1)),
                            () -> null);
                }
            }

            @Nested
            @DisplayName("with only duration")
            class WithDuration extends ConstraintTest<Date> {

                WithDuration() {
                    super(TestClassWithMoment.class, "dateWithoutPeriod",
                            Date.from(Instant.parse("2007-12-03T11:16:31.00Z")),
                            Date.from(Instant.parse("2007-12-03T11:16:31.00Z").minusMillis(1)),
                            Date.from(Instant.parse("2007-12-03T11:16:31.00Z").plusMillis(1)),
                            () -> null);
                }
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
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").plus(1, ChronoUnit.MILLIS)),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Calendar> {

            WithMoment() {
                super(TestClassWithMoment.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").plus(1, ChronoUnit.MILLIS)),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different time zone")
        class WithDifferentTimeZone extends ConstraintTest<Calendar> {

            WithDifferentTimeZone() {
                super(TestClassWithMoment.class, "calendar",
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T10:16:31+01:00[UTC]")),
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T10:16:31+01:00[UTC]").minus(1, ChronoUnit.MILLIS)),
                        GregorianCalendar.from(ZonedDateTime.parse("2009-01-04T10:16:31+01:00[UTC]").plus(1, ChronoUnit.MILLIS)),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("Instant")
    class ForInstant {

        @Nested
        @DisplayName("with 'now'")
        class WithNow {

            @Nested
            @DisplayName("with period and duration")
            class WithPeriodAndDuration extends ConstraintTest<Instant> {

                WithPeriodAndDuration() {
                    super(TestClassWithNow.class, "instant",
                            Instant.parse("2009-01-04T11:16:31.00Z"),
                            Instant.parse("2009-01-04T11:16:31.00Z").minusMillis(1),
                            Instant.parse("2009-01-04T11:16:31.00Z").plusMillis(1),
                            () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
                }
            }

            @Nested
            @DisplayName("with only duration")
            class WithDuration extends ConstraintTest<Instant> {

                WithDuration() {
                    super(TestClassWithNow.class, "instantWithoutPeriod",
                            Instant.parse("2007-12-03T11:16:31.00Z"),
                            Instant.parse("2007-12-03T11:16:31.00Z").minusMillis(1),
                            Instant.parse("2007-12-03T11:16:31.00Z").plusMillis(1),
                            () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
                }
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment {

            @Nested
            @DisplayName("with period and duration")
            class WithPeriodAndDuration extends ConstraintTest<Instant> {

                WithPeriodAndDuration() {
                    super(TestClassWithMoment.class, "instant",
                            Instant.parse("2009-01-04T11:16:31.00Z"),
                            Instant.parse("2009-01-04T11:16:31.00Z").minusMillis(1),
                            Instant.parse("2009-01-04T11:16:31.00Z").plusMillis(1),
                            () -> null);
                }
            }

            @Nested
            @DisplayName("with only duration")
            class WithDuration extends ConstraintTest<Instant> {

                WithDuration() {
                    super(TestClassWithMoment.class, "instantWithoutPeriod",
                            Instant.parse("2007-12-03T11:16:31.00Z"),
                            Instant.parse("2007-12-03T11:16:31.00Z").minusMillis(1),
                            Instant.parse("2007-12-03T11:16:31.00Z").plusMillis(1),
                            () -> null);
                }
            }
        }
    }

    @Nested
    @DisplayName("LocalDate")
    class ForLocalDate {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalDate> {

            WithNow() {
                super(TestClassWithNow.class, "localDate",
                        LocalDate.parse("2009-01-04"),
                        LocalDate.parse("2009-01-04").minusDays(1),
                        LocalDate.parse("2009-01-04").plusDays(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalDate> {

            WithMoment() {
                super(TestClassWithMoment.class, "localDate",
                        LocalDate.parse("2009-01-04"),
                        LocalDate.parse("2009-01-04").minusDays(1),
                        LocalDate.parse("2009-01-04").plusDays(1),
                        () -> null);
            }
        }

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = { "P1DT1H", "PT1H" })
        @DisplayName("invalid duration")
        void testInvalidDuration(String duration) {
            MaxAfter constraintAnnotation = mock(MaxAfter.class);
            when(constraintAnnotation.moment()).thenReturn("now");
            when(constraintAnnotation.duration()).thenReturn(duration);

            MaxAfterValidator.ForLocalDate validator = new MaxAfterValidator.ForLocalDate();
            assertThrows(DateTimeException.class, () -> validator.initialize(constraintAnnotation));
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
                        LocalDateTime.parse("2009-01-04T11:16:31"),
                        LocalDateTime.parse("2009-01-04T11:16:31").minusNanos(1),
                        LocalDateTime.parse("2009-01-04T11:16:31").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "localDateTime",
                        LocalDateTime.parse("2009-01-04T11:16:31"),
                        LocalDateTime.parse("2009-01-04T11:16:31").minusNanos(1),
                        LocalDateTime.parse("2009-01-04T11:16:31").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("LocalTime")
    class ForLocalTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<LocalTime> {

            WithNow() {
                super(TestClassWithNow.class, "localTime",
                        LocalTime.parse("11:16:31"),
                        LocalTime.parse("11:16:31").minusNanos(1),
                        LocalTime.parse("11:16:31").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<LocalTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "localTime",
                        LocalTime.parse("11:16:31"),
                        LocalTime.parse("11:16:31").minusNanos(1),
                        LocalTime.parse("11:16:31").plusNanos(1),
                        () -> null);
            }
        }

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = { "P1DT1H", "P1D" })
        @DisplayName("invalid duration")
        void testInvalidDuration(String duration) {
            MaxAfter constraintAnnotation = mock(MaxAfter.class);
            when(constraintAnnotation.moment()).thenReturn("now");
            when(constraintAnnotation.duration()).thenReturn(duration);

            MaxAfterValidator.ForLocalTime validator = new MaxAfterValidator.ForLocalTime();
            assertThrows(DateTimeException.class, () -> validator.initialize(constraintAnnotation));
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
                        OffsetDateTime.parse("2009-01-04T11:16:31+01:00"),
                        OffsetDateTime.parse("2009-01-04T11:16:31+01:00").minusNanos(1),
                        OffsetDateTime.parse("2009-01-04T11:16:31+01:00").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "offsetDateTime",
                        OffsetDateTime.parse("2009-01-04T11:16:31+01:00"),
                        OffsetDateTime.parse("2009-01-04T11:16:31+01:00").minusNanos(1),
                        OffsetDateTime.parse("2009-01-04T11:16:31+01:00").plusNanos(1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different offset")
        class WithDifferentOffset extends ConstraintTest<OffsetDateTime> {

            WithDifferentOffset() {
                super(TestClassWithMoment.class, "offsetDateTime",
                        OffsetDateTime.parse("2009-01-04T09:16:31-01:00"),
                        OffsetDateTime.parse("2009-01-04T09:16:31-01:00").minusNanos(1),
                        OffsetDateTime.parse("2009-01-04T09:16:31-01:00").plusNanos(1),
                        () -> null);
            }
        }
    }

    @Nested
    @DisplayName("OffsetTime")
    class ForOffsetTime {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<OffsetTime> {

            WithNow() {
                super(TestClassWithNow.class, "offsetTime",
                        OffsetTime.parse("11:16:31+01:00"),
                        OffsetTime.parse("11:16:31+01:00").minusNanos(1),
                        OffsetTime.parse("11:16:31+01:00").plusNanos(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T09:15:30.00Z"), ZoneOffset.ofHours(1)));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<OffsetTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "offsetTime",
                        OffsetTime.parse("11:16:31+01:00"),
                        OffsetTime.parse("11:16:31+01:00").minusNanos(1),
                        OffsetTime.parse("11:16:31+01:00").plusNanos(1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different offset")
        class WithDifferentOffset extends ConstraintTest<OffsetTime> {

            WithDifferentOffset() {
                super(TestClassWithMoment.class, "offsetTime",
                        OffsetTime.parse("09:16:31-01:00"),
                        OffsetTime.parse("09:16:31-01:00").minusNanos(1),
                        OffsetTime.parse("09:16:31-01:00").plusNanos(1),
                        () -> null);
            }
        }

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = { "P1DT1H", "P1D" })
        @DisplayName("invalid duration")
        void testInvalidDuration(String duration) {
            MaxAfter constraintAnnotation = mock(MaxAfter.class);
            when(constraintAnnotation.moment()).thenReturn("now");
            when(constraintAnnotation.duration()).thenReturn(duration);

            MaxAfterValidator.ForOffsetTime validator = new MaxAfterValidator.ForOffsetTime();
            assertThrows(DateTimeException.class, () -> validator.initialize(constraintAnnotation));
        }
    }

    @Nested
    @DisplayName("Year")
    class ForYear {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<Year> {

            WithNow() {
                super(TestClassWithNow.class, "year",
                        Year.parse("2008"),
                        Year.parse("2008").minusYears(1),
                        Year.parse("2008").plusYears(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<Year> {

            WithMoment() {
                super(TestClassWithMoment.class, "year",
                        Year.parse("2008"),
                        Year.parse("2008").minusYears(1),
                        Year.parse("2008").plusYears(1),
                        () -> null);
            }
        }

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = { "P1Y1M1D", "P1Y1D", "P1Y1M", "P1YT1H", "PT1H" })
        @DisplayName("invalid duration")
        void testInvalidDuration(String duration) {
            MaxAfter constraintAnnotation = mock(MaxAfter.class);
            when(constraintAnnotation.moment()).thenReturn("now");
            when(constraintAnnotation.duration()).thenReturn(duration);

            MaxAfterValidator.ForYear validator = new MaxAfterValidator.ForYear();
            assertThrows(DateTimeException.class, () -> validator.initialize(constraintAnnotation));
        }
    }

    @Nested
    @DisplayName("YearMonth")
    class ForYearMonth {

        @Nested
        @DisplayName("with 'now'")
        class WithNow extends ConstraintTest<YearMonth> {

            WithNow() {
                super(TestClassWithNow.class, "yearMonth",
                        YearMonth.parse("2009-01"),
                        YearMonth.parse("2009-01").minusMonths(1),
                        YearMonth.parse("2009-01").plusMonths(1),
                        () -> Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), ZoneId.of("UTC")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<YearMonth> {

            WithMoment() {
                super(TestClassWithMoment.class, "yearMonth",
                        YearMonth.parse("2009-01"),
                        YearMonth.parse("2009-01").minusMonths(1),
                        YearMonth.parse("2009-01").plusMonths(1),
                        () -> null);
            }
        }

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = { "P1Y1M1D", "P1Y1D", "P1YT1H", "PT1H" })
        @DisplayName("invalid duration")
        void testInvalidDuration(String duration) {
            MaxAfter constraintAnnotation = mock(MaxAfter.class);
            when(constraintAnnotation.moment()).thenReturn("now");
            when(constraintAnnotation.duration()).thenReturn(duration);

            MaxAfterValidator.ForYearMonth validator = new MaxAfterValidator.ForYearMonth();
            assertThrows(DateTimeException.class, () -> validator.initialize(constraintAnnotation));
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
                        ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").minusNanos(1),
                        ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").plusNanos(1),
                        () -> Clock.fixed(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").toInstant(), ZoneId.of("Europe/Paris")));
            }
        }

        @Nested
        @DisplayName("with moment")
        class WithMoment extends ConstraintTest<ZonedDateTime> {

            WithMoment() {
                super(TestClassWithMoment.class, "zonedDateTime",
                        ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]"),
                        ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").minusNanos(1),
                        ZonedDateTime.parse("2009-01-04T11:16:31+01:00[Europe/Paris]").plusNanos(1),
                        () -> null);
            }
        }

        @Nested
        @DisplayName("with different time zone")
        class WithDifferentTimeZone extends ConstraintTest<ZonedDateTime> {

            WithDifferentTimeZone() {
                super(TestClassWithMoment.class, "zonedDateTime",
                        ZonedDateTime.parse("2009-01-04T10:16:31+01:00[UTC]"),
                        ZonedDateTime.parse("2009-01-04T10:16:31+01:00[UTC]").minusNanos(1),
                        ZonedDateTime.parse("2009-01-04T10:16:31+01:00[UTC]").plusNanos(1),
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

            MaxAfter maxAfter = assertDoesNotThrow(() -> beanType.getDeclaredField(propertyName))
                    .getAnnotation(MaxAfter.class);
            this.moment = maxAfter.moment();
            this.duration = maxAfter.duration();
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
            assertAnnotation(violation, MaxAfter.class);
            assertEquals("must be at most " + duration + " after " + moment, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }
    }

    private static final class TestClassWithNow {
        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "now")
        Date date;

        @MaxAfter(duration = "PT1H1M1S", moment = "now")
        Date dateWithoutPeriod;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "now")
        Calendar calendar;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "now")
        Instant instant;

        @MaxAfter(duration = "PT1H1M1S", moment = "now")
        Instant instantWithoutPeriod;

        @MaxAfter(duration = "P1Y1M1D", moment = "now")
        LocalDate localDate;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "now")
        LocalDateTime localDateTime;

        @MaxAfter(duration = "PT1H1M1S", moment = "now")
        LocalTime localTime;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "now")
        OffsetDateTime offsetDateTime;

        @MaxAfter(duration = "PT1H1M1S", moment = "now")
        OffsetTime offsetTime;

        @MaxAfter(duration = "P1Y", moment = "now")
        Year year;

        @MaxAfter(duration = "P1Y1M", moment = "now")
        YearMonth yearMonth;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "now")
        ZonedDateTime zonedDateTime;
    }

    private static final class TestClassWithMoment {
        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "2007-12-03T10:15:30.00Z")
        Date date;

        @MaxAfter(duration = "PT1H1M1S", moment = "2007-12-03T10:15:30.00Z")
        Date dateWithoutPeriod;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "2007-12-03T10:15:30+01:00[Europe/Paris]")
        Calendar calendar;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "2007-12-03T10:15:30.00Z")
        Instant instant;

        @MaxAfter(duration = "PT1H1M1S", moment = "2007-12-03T10:15:30.00Z")
        Instant instantWithoutPeriod;

        @MaxAfter(duration = "P1Y1M1D", moment = "2007-12-03")
        LocalDate localDate;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "2007-12-03T10:15:30")
        LocalDateTime localDateTime;

        @MaxAfter(duration = "PT1H1M1S", moment = "10:15:30")
        LocalTime localTime;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "2007-12-03T10:15:30+01:00")
        OffsetDateTime offsetDateTime;

        @MaxAfter(duration = "PT1H1M1S", moment = "10:15:30+01:00")
        OffsetTime offsetTime;

        @MaxAfter(duration = "P1Y", moment = "2007")
        Year year;

        @MaxAfter(duration = "P1Y1M", moment = "2007-12")
        YearMonth yearMonth;

        @MaxAfter(duration = "P1Y1M1DT1H1M1S", moment = "2007-12-03T10:15:30+01:00[Europe/Paris]")
        ZonedDateTime zonedDateTime;
    }
}
