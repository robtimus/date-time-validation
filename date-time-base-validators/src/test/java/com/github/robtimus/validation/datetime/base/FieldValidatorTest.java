/*
 * FieldValidatorTest.java
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
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "javadoc", "nls" })
//public because of the constraint and validators
public class FieldValidatorTest extends AbstractValidatorTest {

    @Nested
    @DisplayName("for generic type")
    class ForGenericTypeTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "offsetDateTimeWithSystemZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "offsetDateTimeWithSystemZoneId", OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithSystemZoneId",
                        OffsetDateTime.parse("2007-11-03T10:15:30+01:00"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "offsetDateTimeWithProvidedZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "offsetDateTimeWithProvidedZoneId", OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithProvidedZoneId",
                        OffsetDateTime.parse("2007-11-03T10:15:30+01:00"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "offsetDateTimeWithExplicitZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "offsetDateTimeWithExplicitZoneId", OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetDateTimeWithExplicitZoneId",
                        OffsetDateTime.parse("2007-11-03T10:15:30+01:00"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }
    }

    @Nested
    @DisplayName("without zoneId")
    class WithoutZoneIdTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "localDateWithSystemZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "localDateWithSystemZoneId", LocalDate.of(2007, 12, 3));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateWithSystemZoneId", LocalDate.of(2007, 11, 3));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Test
            @DisplayName("not allowed")
            void testNotAllowed() {
                ValidationException exception = assertThrows(ValidationException.class,
                        () -> validate(() -> null, TestClass.class, "localDateWithProvidedZoneId", null));

                Throwable cause = exception.getCause();
                assertInstanceOf(IllegalStateException.class, cause);
                assertEquals("zoneId should be 'system', is 'provided'", cause.getMessage());
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Test
            @DisplayName("not allowed")
            void testNotAllowed() {
                ValidationException exception = assertThrows(ValidationException.class,
                        () -> validate(() -> null, TestClass.class, "localDateWithExplicitZoneId", null));

                Throwable cause = exception.getCause();
                assertInstanceOf(IllegalStateException.class, cause);
                assertEquals("zoneId should be 'system', is 'UTC'", cause.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("for Instant")
    class ForInstantTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

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
                        Instant.parse("2007-11-03T10:15:30.00Z"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

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

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

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
                        Instant.parse("2007-11-03T10:15:30.00Z"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }
    }

    @Nested
    @DisplayName("for OffsetTime")
    class ForOffsetTimeTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "offsetTimeWithSystemZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "offsetTimeWithSystemZoneId", OffsetTime.parse("10:15:30+01:00"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetTimeWithSystemZoneId",
                        OffsetTime.parse("10:14:30+01:00"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "offsetTimeWithProvidedZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "offsetTimeWithProvidedZoneId", OffsetTime.parse("10:15:30+01:00"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetTimeWithProvidedZoneId",
                        OffsetTime.parse("10:14:30+01:00"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "offsetTimeWithExplicitZoneId", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "offsetTimeWithExplicitZoneId", OffsetTime.parse("10:15:30+01:00"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "offsetTimeWithExplicitZoneId",
                        OffsetTime.parse("10:14:30+01:00"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }
    }

    @Nested
    @DisplayName("for ZonedDateTime")
    class ForZonedDateTimeTest {

        @Nested
        @DisplayName("with system zoneId")
        class WithSystemZoneId {

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
                        ZonedDateTime.parse("2007-11-03T10:15:30+01:00[Europe/Paris]"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with provided zoneId")
        class WithProvidedZoneId {

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
                        ZonedDateTime.parse("2007-11-03T10:15:30+01:00[Europe/Paris]"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }

        @Nested
        @DisplayName("with explicit zoneId")
        class WithExplicitZoneId {

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
                        ZonedDateTime.parse("2007-11-03T10:15:30+01:00[Europe/Paris]"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraint.class);
            }
        }
    }

    private static final class TestClass {

        @TestConstraint(zoneId = "system")
        private OffsetDateTime offsetDateTimeWithSystemZoneId;

        @TestConstraint(zoneId = "provided")
        private OffsetDateTime offsetDateTimeWithProvidedZoneId;

        @TestConstraint(zoneId = "UTC")
        private OffsetDateTime offsetDateTimeWithExplicitZoneId;

        @TestConstraint(zoneId = "system")
        private LocalDate localDateWithSystemZoneId;

        @TestConstraint(zoneId = "provided")
        private LocalDate localDateWithProvidedZoneId;

        @TestConstraint(zoneId = "UTC")
        private LocalDate localDateWithExplicitZoneId;

        @TestConstraint(zoneId = "system")
        private Instant instantWithSystemZoneId;

        @TestConstraint(zoneId = "provided")
        private Instant instantWithProvidedZoneId;

        @TestConstraint(zoneId = "UTC")
        private Instant instantWithExplicitZoneId;

        @TestConstraint(zoneId = "system")
        private OffsetTime offsetTimeWithSystemZoneId;

        @TestConstraint(zoneId = "provided")
        private OffsetTime offsetTimeWithProvidedZoneId;

        @TestConstraint(zoneId = "UTC")
        private OffsetTime offsetTimeWithExplicitZoneId;

        @TestConstraint(zoneId = "system")
        private ZonedDateTime zonedDateTimeWithSystemZoneId;

        @TestConstraint(zoneId = "provided")
        private ZonedDateTime zonedDateTimeWithProvidedZoneId;

        @TestConstraint(zoneId = "UTC")
        private ZonedDateTime zonedDateTimeWithExplicitZoneId;
    }

    @Constraint(validatedBy = { OffsetDateTimeValidator.class,
            LocalDateValidator.class,
            InstantValidator.class,
            OffsetTimeValidator.class,
            ZonedDateTimeValidator.class
    })
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraint {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String zoneId();
    }

    public static final class OffsetDateTimeValidator extends FieldValidator<TestConstraint, OffsetDateTime> {

        public OffsetDateTimeValidator() {
            super(ChronoField.MONTH_OF_YEAR, TestConstraint::zoneId, OffsetDateTime::atZoneSameInstant, annotation -> (m, p) -> m == 12);
        }
    }

    public static final class LocalDateValidator extends FieldValidator.WithoutZoneId<TestConstraint, LocalDate> {

        public LocalDateValidator() {
            super(ChronoField.MONTH_OF_YEAR, TestConstraint::zoneId, annotation -> (m, p) -> m == 12);
        }
    }

    public static final class InstantValidator extends FieldValidator.ForInstant<TestConstraint> {

        public InstantValidator() {
            super(ChronoField.MONTH_OF_YEAR, TestConstraint::zoneId, annotation -> (m, p) -> m == 12);
        }
    }

    public static final class OffsetTimeValidator extends FieldValidator.ForOffsetTime<TestConstraint> {

        public OffsetTimeValidator() {
            super(ChronoField.MINUTE_OF_HOUR, TestConstraint::zoneId, annotation -> (m, p) -> m == 15);
        }
    }

    public static final class ZonedDateTimeValidator extends FieldValidator.ForZonedDateTime<TestConstraint> {

        public ZonedDateTimeValidator() {
            super(ChronoField.MONTH_OF_YEAR, TestConstraint::zoneId, annotation -> (m, p) -> m == 12);
        }
    }
}
