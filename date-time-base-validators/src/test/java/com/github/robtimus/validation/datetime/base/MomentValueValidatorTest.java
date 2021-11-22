/*
 * MomentValueValidatorTest.java
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
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "javadoc", "nls" })
//public because of the constraint and validators
public class MomentValueValidatorTest extends AbstractValidatorTest {

    @Nested
    @DisplayName("with moment 'now'")
    class WithMomentNow {

        @Test
        @DisplayName("null value")
        void testNullValue() {
            List<?> violations = validate(TestClass.class, "localDateTimeNow", null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("valid value")
        void testValidValue() {
            // Provide a clock, because comparing with "now" doesn't work with nanosecond precision
            Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

            List<?> violations = validate(() -> clock, TestClass.class, "localDateTimeNow", LocalDateTime.now(clock));
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("invalid value")
        void testInvalidValue() {
            // Provide a clock, because comparing with "now" doesn't work with nanosecond precision
            Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

            List<ConstraintViolation<TestClass>> violations = validate(() -> clock, TestClass.class, "localDateTimeNow",
                    LocalDateTime.now(clock).minusNanos(1));
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
            List<?> violations = validate(TestClass.class, "localDateTime", null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("valid value")
        void testValidValue() {
            List<?> violations = validate(TestClass.class, "localDateTime", LocalDateTime.parse("2007-12-03T10:15:30"));
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("invalid value")
        void testInvalidValue() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateTime",
                    LocalDateTime.parse("2007-12-03T10:15:29.999999999"));
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, TestConstraint.class);
        }
    }

    @Nested
    @DisplayName("with duration")
    class WithDuration {

        @Nested
        @DisplayName("with moment 'now'")
        class WithMomentNow {

            @Test
            @DisplayName("null value")
            void testNullValue() {
                List<?> violations = validate(TestClass.class, "localDateTimeNowWithDuration", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                // Provide a clock, because comparing with "now" doesn't work with nanosecond precision
                Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

                List<?> violations = validate(() -> clock, TestClass.class, "localDateTimeNowWithDuration", LocalDateTime.now(clock).plusMonths(1));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                // Provide a clock, because comparing with "now" doesn't work with nanosecond precision
                Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

                List<ConstraintViolation<TestClass>> violations = validate(() -> clock, TestClass.class, "localDateTimeNowWithDuration",
                        LocalDateTime.now(clock).plusMonths(1).minusDays(1));
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
                List<?> violations = validate(TestClass.class, "localDateTimeWithDuration", null);
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("valid value")
            void testValidValue() {
                List<?> violations = validate(TestClass.class, "localDateTimeWithDuration", LocalDateTime.parse("2008-01-03T10:15:30"));
                assertEquals(Collections.emptyList(), violations);
            }

            @Test
            @DisplayName("invalid value")
            void testInvalidValue() {
                List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "localDateTimeWithDuration",
                        LocalDateTime.parse("2008-01-02T10:15:30"));
                assertEquals(1, violations.size());

                ConstraintViolation<?> violation = violations.get(0);
                assertAnnotation(violation, TestConstraintWithDuration.class);
            }
        }
    }

    private static final class TestClass {

        @TestConstraint(moment = "now")
        private LocalDateTime localDateTimeNow;

        @TestConstraint(moment = "2007-12-03T10:15:30")
        private LocalDateTime localDateTime;

        @TestConstraintWithDuration(moment = "now", duration = "P1M")
        private LocalDateTime localDateTimeNowWithDuration;

        @TestConstraintWithDuration(moment = "2007-12-03T10:15:30", duration = "P1M")
        private LocalDateTime localDateTimeWithDuration;
    }

    @Constraint(validatedBy = LocalDateTimeValidator.class)
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraint {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String moment();
    }

    @Constraint(validatedBy = LocalDateTimeDurationValidator.class)
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraintWithDuration {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String moment();

        String duration();
    }

    public static final class LocalDateTimeValidator extends MomentValueValidator<TestConstraint, LocalDateTime> {

        public LocalDateTimeValidator() {
            super(TestConstraint::moment, LocalDateTime::parse, LocalDateTime::now, LocalDateTime::equals);
        }
    }

    public static final class LocalDateTimeDurationValidator extends MomentValueValidator<TestConstraintWithDuration, LocalDateTime> {

        public LocalDateTimeDurationValidator() {
            super(TestConstraintWithDuration::moment, LocalDateTime::parse, LocalDateTime::now,
                    TestConstraintWithDuration::duration, LocalDateTime::plus, LocalDateTime::equals);
        }
    }
}
