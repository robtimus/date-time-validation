/*
 * MomentPartValidatorTest.java
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

package com.github.robtimus.validation.datetime.base;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "javadoc", "nls" })
//public because of the constraint and validators
public class MomentPartValidatorTest extends AbstractValidatorTest {

    @Nested
    @DisplayName("for generic type")
    class ForGenericTypeTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithSystemZoneId", OffsetDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeNowWithSystemZoneId",
                            OffsetDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeWithSystemZoneId",
                            OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithSystemZoneId",
                            OffsetDateTime.parse("2007-12-02T10:15:30+01:00"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithProvidedZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithProvidedZoneId", OffsetDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeNowWithProvidedZoneId",
                            OffsetDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeWithProvidedZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeWithProvidedZoneId",
                            OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithProvidedZoneId",
                            OffsetDateTime.parse("2007-12-02T10:15:30+01:00"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithExplicitZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithExplicitZoneId", OffsetDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeNowWithExplicitZoneId",
                            OffsetDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeWithExplicitZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "offsetDateTimeWithExplicitZoneId",
                            OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithExplicitZoneId",
                            OffsetDateTime.parse("2007-12-02T10:15:30+01:00"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with duration")
        class WithDuration {

            @Nested
            @DisplayName("with system zoneId")
            class WithSystemZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithSystemZoneId",
                                OffsetDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithSystemZoneId",
                                OffsetDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithSystemZoneId",
                                OffsetDateTime.parse("2008-01-03T10:15:30+01:00"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithSystemZoneId",
                                OffsetDateTime.parse("2008-01-02T10:15:30+01:00"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }

            @Nested
            @DisplayName("with provided zoneId")
            class WithProvidedZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithProvidedZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithProvidedZoneId",
                                OffsetDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithProvidedZoneId",
                                OffsetDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithProvidedZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithProvidedZoneId",
                                OffsetDateTime.parse("2008-01-03T10:15:30+01:00"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithProvidedZoneId",
                                OffsetDateTime.parse("2008-01-02T10:15:30+01:00"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }

            @Nested
            @DisplayName("with explicit zoneId")
            class WithExplicitZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithExplicitZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithExplicitZoneId",
                                OffsetDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeNowWithDurationWithExplicitZoneId",
                                OffsetDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithExplicitZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithExplicitZoneId",
                                OffsetDateTime.parse("2008-01-03T10:15:30+01:00"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithDurationWithExplicitZoneId",
                                OffsetDateTime.parse("2008-01-02T10:15:30+01:00"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("without zoneId")
    class WithoutZoneIdTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "localDateTimeNowWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "localDateTimeNowWithSystemZoneId", LocalDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateTimeNowWithSystemZoneId",
                            LocalDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "localDateTimeWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "localDateTimeWithSystemZoneId", LocalDateTime.parse("2007-12-03T10:15:30"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateTimeWithSystemZoneId",
                            LocalDateTime.parse("2007-12-02T10:15:30"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("not allowed")
                void testNotAllowed() {
                    ValidationException exception = assertThrows(ValidationException.class,
                            () -> validate(() -> null, TestClass.class, "localDateTimeNowWithProvidedZoneId", null));

                    Throwable cause = exception.getCause();
                    assertInstanceOf(IllegalStateException.class, cause);
                    assertEquals("zoneId should be 'system', is 'provided'", cause.getMessage());
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("not allowed")
                void testNotAllowed() {
                    ValidationException exception = assertThrows(ValidationException.class,
                            () -> validate(() -> null, TestClass.class, "localDateTimeWithProvidedZoneId", null));

                    Throwable cause = exception.getCause();
                    assertInstanceOf(IllegalStateException.class, cause);
                    assertEquals("zoneId should be 'system', is 'provided'", cause.getMessage());
                }
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("not allowed")
                void testNotAllowed() {
                    ValidationException exception = assertThrows(ValidationException.class,
                            () -> validate(() -> null, TestClass.class, "localDateTimeNowWithExplicitZoneId", null));

                    Throwable cause = exception.getCause();
                    assertInstanceOf(IllegalStateException.class, cause);
                    assertEquals("zoneId should be 'system', is 'UTC'", cause.getMessage());
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("not allowed")
                void testNotAllowed() {
                    ValidationException exception = assertThrows(ValidationException.class,
                            () -> validate(() -> null, TestClass.class, "localDateTimeWithExplicitZoneId", null));

                    Throwable cause = exception.getCause();
                    assertInstanceOf(IllegalStateException.class, cause);
                    assertEquals("zoneId should be 'system', is 'UTC'", cause.getMessage());
                }
            }
        }

        @Nested
        @DisplayName("with duration")
        class WithDuration {

            @Nested
            @DisplayName("with system zoneId")
            class WithSystemZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "localDateTimeNowWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "localDateTimeNowWithDurationWithSystemZoneId",
                                LocalDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateTimeNowWithDurationWithSystemZoneId",
                                LocalDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "localDateTimeWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "localDateTimeWithDurationWithSystemZoneId",
                                LocalDateTime.parse("2008-01-03T10:15:30"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateTimeWithDurationWithSystemZoneId",
                                LocalDateTime.parse("2008-01-02T10:15:30"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }

            @Nested
            @DisplayName("with provided zoneId")
            class WithProvidedZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("not allowed")
                    void testNotAllowed() {
                        ValidationException exception = assertThrows(ValidationException.class,
                                () -> validate(() -> null, TestClass.class, "localDateTimeNowWithDurationWithProvidedZoneId", null));

                        Throwable cause = exception.getCause();
                        assertInstanceOf(IllegalStateException.class, cause);
                        assertEquals("zoneId should be 'system', is 'provided'", cause.getMessage());
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("not allowed")
                    void testNotAllowed() {
                        ValidationException exception = assertThrows(ValidationException.class,
                                () -> validate(() -> null, TestClass.class, "localDateTimeWithDurationWithProvidedZoneId", null));

                        Throwable cause = exception.getCause();
                        assertInstanceOf(IllegalStateException.class, cause);
                        assertEquals("zoneId should be 'system', is 'provided'", cause.getMessage());
                    }
                }
            }

            @Nested
            @DisplayName("with explicit zoneId")
            class WithExplicitZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("not allowed")
                    void testNotAllowed() {
                        ValidationException exception = assertThrows(ValidationException.class,
                                () -> validate(() -> null, TestClass.class, "localDateTimeNowWithDurationWithExplicitZoneId", null));

                        Throwable cause = exception.getCause();
                        assertInstanceOf(IllegalStateException.class, cause);
                        assertEquals("zoneId should be 'system', is 'UTC'", cause.getMessage());
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("not allowed")
                    void testNotAllowed() {
                        ValidationException exception = assertThrows(ValidationException.class,
                                () -> validate(() -> null, TestClass.class, "localDateTimeWithDurationWithExplicitZoneId", null));

                        Throwable cause = exception.getCause();
                        assertInstanceOf(IllegalStateException.class, cause);
                        assertEquals("zoneId should be 'system', is 'UTC'", cause.getMessage());
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("for Instant")
    class ForInstantTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "instantNowWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "instantNowWithSystemZoneId", Instant.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantNowWithSystemZoneId",
                            Instant.now().minus(24, ChronoUnit.HOURS));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "instantWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "instantWithSystemZoneId", Instant.parse("2007-12-03T10:15:30.00Z"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantWithSystemZoneId",
                            Instant.parse("2007-12-02T10:15:30.00Z"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("zoneId 'provided' not allowed")
                void testZoneIdProvidedNotAllowed() {
                    ValidationException exception = assertThrows(ValidationException.class,
                            () -> validate(() -> null, TestClass.class, "instantNowWithProvidedZoneId", null));

                    Throwable cause = exception.getCause();
                    assertInstanceOf(IllegalStateException.class, cause);
                    assertEquals("zoneId should not be 'provided'", cause.getMessage());
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("zoneId 'provided' not allowed")
                void testZoneIdProvidedNotAllowed() {
                    ValidationException exception = assertThrows(ValidationException.class,
                            () -> validate(() -> null, TestClass.class, "instantWithProvidedZoneId", null));

                    Throwable cause = exception.getCause();
                    assertInstanceOf(IllegalStateException.class, cause);
                    assertEquals("zoneId should not be 'provided'", cause.getMessage());
                }
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "instantNowWithExplicitZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "instantNowWithExplicitZoneId", Instant.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantNowWithExplicitZoneId",
                            Instant.now().minus(24, ChronoUnit.HOURS));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "instantWithExplicitZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "instantWithExplicitZoneId", Instant.parse("2007-12-03T10:15:30.00Z"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantWithExplicitZoneId",
                            Instant.parse("2007-12-02T10:15:30.00Z"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with duration")
        class WithDuration {

            @Nested
            @DisplayName("with system zoneId")
            class WithSystemZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "instantNowWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "instantNowWithDurationWithSystemZoneId",
                                Instant.now().plus(31 * 24, ChronoUnit.HOURS));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantNowWithDurationWithSystemZoneId",
                                Instant.now().plus(30 * 24, ChronoUnit.HOURS));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "instantWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "instantWithDurationWithSystemZoneId",
                                Instant.parse("2008-01-03T10:15:30.00Z"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantWithDurationWithSystemZoneId",
                                Instant.parse("2008-01-02T10:15:30.00Z"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }

            @Nested
            @DisplayName("with provided zoneId")
            class WithProvidedZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("zoneId 'provided' not allowed")
                    void testZoneIdProvidedNotAllowed() {
                        ValidationException exception = assertThrows(ValidationException.class,
                                () -> validate(() -> null, TestClass.class, "instantNowWithDurationWithProvidedZoneId", null));

                        Throwable cause = exception.getCause();
                        assertInstanceOf(IllegalStateException.class, cause);
                        assertEquals("zoneId should not be 'provided'", cause.getMessage());
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("zoneId 'provided' not allowed")
                    void testZoneIdProvidedNotAllowed() {
                        ValidationException exception = assertThrows(ValidationException.class,
                                () -> validate(() -> null, TestClass.class, "instantWithDurationWithProvidedZoneId", null));

                        Throwable cause = exception.getCause();
                        assertInstanceOf(IllegalStateException.class, cause);
                        assertEquals("zoneId should not be 'provided'", cause.getMessage());
                    }
                }
            }

            @Nested
            @DisplayName("with explicit zoneId")
            class WithExplicitZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "instantNowWithDurationWithExplicitZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "instantNowWithDurationWithExplicitZoneId",
                                Instant.now().plus(31 * 24, ChronoUnit.HOURS));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantNowWithDurationWithExplicitZoneId",
                                Instant.now().plus(30 * 24, ChronoUnit.HOURS));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "instantWithDurationWithExplicitZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "instantWithDurationWithExplicitZoneId",
                                Instant.parse("2008-01-03T10:15:30.00Z"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "instantWithDurationWithExplicitZoneId",
                                Instant.parse("2008-01-02T10:15:30.00Z"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("for ZonedDateTime")
    class ForZonedDateTimeTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithSystemZoneId", ZonedDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeNowWithSystemZoneId",
                            ZonedDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeWithSystemZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeWithSystemZoneId",
                            ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeWithSystemZoneId",
                            ZonedDateTime.parse("2007-12-02T10:15:30+01:00[Europe/Paris]"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithProvidedZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithProvidedZoneId", ZonedDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeNowWithProvidedZoneId",
                            ZonedDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeWithProvidedZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeWithProvidedZoneId",
                            ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeWithProvidedZoneId",
                            ZonedDateTime.parse("2007-12-02T10:15:30+01:00[Europe/Paris]"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Nested
            @DisplayName("with moment 'now'")
            class WithMomentNow {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithExplicitZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithExplicitZoneId", ZonedDateTime.now());
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeNowWithExplicitZoneId",
                            ZonedDateTime.now().minusDays(1));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }

            @Nested
            @DisplayName("with explicit moment")
            class WithExplicitMoment {

                @Test
                @DisplayName("null value")
                void testNullValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeWithExplicitZoneId", null);
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("valid value")
                void testValidValue() {
                    List<?> violations = validate(TestClass.class, "zonedDateTimeWithExplicitZoneId",
                            ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"));
                    assertEquals(Collections.emptyList(), violations);
                }

                @Test
                @DisplayName("invalid value")
                void testInvalidValue() {
                    List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeWithExplicitZoneId",
                            ZonedDateTime.parse("2007-12-02T10:15:30+01:00[Europe/Paris]"));
                    assertEquals(1, violations.size());

                    ConstraintViolation<?> violation = violations.get(0);
                    assertAnnotation(violation, TestConstraint.class);
                }
            }
        }

        @Nested
        @DisplayName("with duration")
        class WithDuration {

            @Nested
            @DisplayName("with system zoneId")
            class WithSystemZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithSystemZoneId",
                                ZonedDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithSystemZoneId",
                                ZonedDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithSystemZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithSystemZoneId",
                                ZonedDateTime.parse("2008-01-03T10:15:30+01:00[Europe/Paris]"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithSystemZoneId",
                                ZonedDateTime.parse("2008-01-02T10:15:30+01:00[Europe/Paris]"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }

            @Nested
            @DisplayName("with provided zoneId")
            class WithProvidedZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithProvidedZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithProvidedZoneId",
                                ZonedDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithProvidedZoneId",
                                ZonedDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithProvidedZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithProvidedZoneId",
                                ZonedDateTime.parse("2008-01-03T10:15:30+01:00[Europe/Paris]"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithProvidedZoneId",
                                ZonedDateTime.parse("2008-01-02T10:15:30+01:00[Europe/Paris]"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }

            @Nested
            @DisplayName("with explicit zoneId")
            class WithExplicitZoneId {

                @Nested
                @DisplayName("with moment 'now'")
                class WithMomentNow {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithExplicitZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithExplicitZoneId",
                                ZonedDateTime.now().plusMonths(1));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeNowWithDurationWithExplicitZoneId",
                                ZonedDateTime.now().plusMonths(1).minusDays(1));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }

                @Nested
                @DisplayName("with explicit moment")
                class WithExplicitMoment {

                    @Test
                    @DisplayName("null value")
                    void testNullValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithExplicitZoneId", null);
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("valid value")
                    void testValidValue() {
                        List<?> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithExplicitZoneId",
                                ZonedDateTime.parse("2008-01-03T10:15:30+01:00[Europe/Paris]"));
                        assertEquals(Collections.emptyList(), violations);
                    }

                    @Test
                    @DisplayName("invalid value")
                    void testInvalidValue() {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "zonedDateTimeWithDurationWithExplicitZoneId",
                                ZonedDateTime.parse("2008-01-02T10:15:30+01:00[Europe/Paris]"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<?> violation = violations.get(0);
                        assertAnnotation(violation, TestConstraintWithDuration.class);
                    }
                }
            }
        }
    }

    private static final class TestClass {

        @TestConstraint(moment = "now", zoneId = "system")
        private OffsetDateTime offsetDateTimeNowWithSystemZoneId;

        @TestConstraint(moment = "now", zoneId = "provided")
        private OffsetDateTime offsetDateTimeNowWithProvidedZoneId;

        @TestConstraint(moment = "now", zoneId = "UTC")
        private OffsetDateTime offsetDateTimeNowWithExplicitZoneId;

        @TestConstraint(moment = "now", zoneId = "system")
        private LocalDateTime localDateTimeNowWithSystemZoneId;

        @TestConstraint(moment = "now", zoneId = "provided")
        private LocalDateTime localDateTimeNowWithProvidedZoneId;

        @TestConstraint(moment = "now", zoneId = "UTC")
        private LocalDateTime localDateTimeNowWithExplicitZoneId;

        @TestConstraint(moment = "now", zoneId = "system")
        private Instant instantNowWithSystemZoneId;

        @TestConstraint(moment = "now", zoneId = "provided")
        private Instant instantNowWithProvidedZoneId;

        @TestConstraint(moment = "now", zoneId = "UTC")
        private Instant instantNowWithExplicitZoneId;

        @TestConstraint(moment = "now", zoneId = "system")
        private ZonedDateTime zonedDateTimeNowWithSystemZoneId;

        @TestConstraint(moment = "now", zoneId = "provided")
        private ZonedDateTime zonedDateTimeNowWithProvidedZoneId;

        @TestConstraint(moment = "now", zoneId = "UTC")
        private ZonedDateTime zonedDateTimeNowWithExplicitZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "system")
        private OffsetDateTime offsetDateTimeWithSystemZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "provided")
        private OffsetDateTime offsetDateTimeWithProvidedZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "UTC")
        private OffsetDateTime offsetDateTimeWithExplicitZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "system")
        private LocalDateTime localDateTimeWithSystemZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "provided")
        private LocalDateTime localDateTimeWithProvidedZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "UTC")
        private LocalDateTime localDateTimeWithExplicitZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "system")
        private Instant instantWithSystemZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "provided")
        private Instant instantWithProvidedZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "UTC")
        private Instant instantWithExplicitZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "system")
        private ZonedDateTime zonedDateTimeWithSystemZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "provided")
        private ZonedDateTime zonedDateTimeWithProvidedZoneId;

        @TestConstraint(moment = "2007-12-03", zoneId = "UTC")
        private ZonedDateTime zonedDateTimeWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "system")
        private OffsetDateTime offsetDateTimeNowWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "provided")
        private OffsetDateTime offsetDateTimeNowWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "UTC")
        private OffsetDateTime offsetDateTimeNowWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "system")
        private LocalDateTime localDateTimeNowWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "provided")
        private LocalDateTime localDateTimeNowWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "UTC")
        private LocalDateTime localDateTimeNowWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P31D", zoneId = "system")
        private Instant instantNowWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P31D", zoneId = "provided")
        private Instant instantNowWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P31D", zoneId = "UTC")
        private Instant instantNowWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "system")
        private ZonedDateTime zonedDateTimeNowWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "provided")
        private ZonedDateTime zonedDateTimeNowWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "now", duration = "P1M", zoneId = "UTC")
        private ZonedDateTime zonedDateTimeNowWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "system")
        private OffsetDateTime offsetDateTimeWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "provided")
        private OffsetDateTime offsetDateTimeWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "UTC")
        private OffsetDateTime offsetDateTimeWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "system")
        private LocalDateTime localDateTimeWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "provided")
        private LocalDateTime localDateTimeWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "UTC")
        private LocalDateTime localDateTimeWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P31D", zoneId = "system")
        private Instant instantWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P31D", zoneId = "provided")
        private Instant instantWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P31D", zoneId = "UTC")
        private Instant instantWithDurationWithExplicitZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "system")
        private ZonedDateTime zonedDateTimeWithDurationWithSystemZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "provided")
        private ZonedDateTime zonedDateTimeWithDurationWithProvidedZoneId;

        @TestConstraintWithDuration(moment = "2007-12-03", duration = "P1M", zoneId = "UTC")
        private ZonedDateTime zonedDateTimeWithDurationWithExplicitZoneId;
    }

    @Constraint(validatedBy = { OffsetDateTimeValidator.class, LocalDateTimeValidator.class, InstantValidator.class, ZonedDateTimeValidator.class })
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraint {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String moment();

        String zoneId();
    }

    @Constraint(validatedBy = { OffsetDateTimeDurationValidator.class, LocalDateTimeDurationValidator.class, InstantDurationValidator.class,
            ZonedDateTimeDurationValidator.class })
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraintWithDuration {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String moment();

        String duration();

        String zoneId();
    }

    public static final class OffsetDateTimeValidator extends MomentPartValidator<TestConstraint, OffsetDateTime, LocalDate> {

        public OffsetDateTimeValidator() {
            super(TestConstraint::moment, LocalDate::parse, LocalDate::now, TestConstraint::zoneId,
                    OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate, LocalDate::equals);
        }
    }

    public static final class LocalDateTimeValidator extends MomentPartValidator.WithoutZoneId<TestConstraint, LocalDateTime, LocalDate> {

        public LocalDateTimeValidator() {
            super(TestConstraint::moment, LocalDate::parse, LocalDate::now, TestConstraint::zoneId, LocalDateTime::toLocalDate, LocalDate::equals);
        }
    }

    public static final class InstantValidator extends MomentPartValidator.ForInstant<TestConstraint, LocalDate> {

        public InstantValidator() {
            super(TestConstraint::moment, LocalDate::parse, LocalDate::now, TestConstraint::zoneId, ZonedDateTime::toLocalDate, LocalDate::equals);
        }
    }

    public static final class ZonedDateTimeValidator extends MomentPartValidator.ForZonedDateTime<TestConstraint, LocalDate> {

        public ZonedDateTimeValidator() {
            super(TestConstraint::moment, LocalDate::parse, LocalDate::now, TestConstraint::zoneId, ZonedDateTime::toLocalDate, LocalDate::equals);
        }
    }

    public static final class OffsetDateTimeDurationValidator extends MomentPartValidator<TestConstraintWithDuration, OffsetDateTime, LocalDate> {

        public OffsetDateTimeDurationValidator() {
            super(TestConstraintWithDuration::moment, LocalDate::parse, LocalDate::now, TestConstraintWithDuration::duration, LocalDate::plus,
                    TestConstraintWithDuration::zoneId, OffsetDateTime::toLocalDate, OffsetDateTime::atZoneSameInstant, ZonedDateTime::toLocalDate,
                    LocalDate::equals);
        }
    }

    public static final class LocalDateTimeDurationValidator
            extends MomentPartValidator.WithoutZoneId<TestConstraintWithDuration, LocalDateTime, LocalDate> {

        public LocalDateTimeDurationValidator() {
            super(TestConstraintWithDuration::moment, LocalDate::parse, LocalDate::now, TestConstraintWithDuration::duration, LocalDate::plus,
                    TestConstraintWithDuration::zoneId, LocalDateTime::toLocalDate, LocalDate::equals);
        }
    }

    public static final class InstantDurationValidator extends MomentPartValidator.ForInstant<TestConstraintWithDuration, LocalDate> {

        public InstantDurationValidator() {
            super(TestConstraintWithDuration::moment, LocalDate::parse, LocalDate::now, TestConstraintWithDuration::duration, LocalDate::plus,
                    TestConstraintWithDuration::zoneId, ZonedDateTime::toLocalDate, LocalDate::equals);
        }
    }

    public static final class ZonedDateTimeDurationValidator extends MomentPartValidator.ForZonedDateTime<TestConstraintWithDuration, LocalDate> {

        public ZonedDateTimeDurationValidator() {
            super(TestConstraintWithDuration::moment, LocalDate::parse, LocalDate::now, TestConstraintWithDuration::duration, LocalDate::plus,
                    TestConstraintWithDuration::zoneId, ZonedDateTime::toLocalDate, LocalDate::equals);
        }
    }
}
