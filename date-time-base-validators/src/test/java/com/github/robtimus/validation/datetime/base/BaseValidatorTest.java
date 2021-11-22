/*
 * BaseValidatorTest.java
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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings({ "javadoc", "nls" })
// public because of the constraint and validators
public class BaseValidatorTest extends AbstractValidatorTest {

    @Nested
    @DisplayName("validator")
    class Validator {

        @Test
        @DisplayName("null date with default message")
        void testNullDateWithDefaultMessage() {
            testNull("dateWithDefaultMessage");
        }

        @Test
        @DisplayName("valid date with default message")
        void testValidDateWithDefaultMessage() {
            testValid("dateWithDefaultMessage", LocalDate.of(2007, 12, 3));
        }

        @Test
        @DisplayName("invalid date with default message")
        void testInvalidDateWithDefaultMessage() {
            testInvalid("dateWithDefaultMessage", LocalDate.of(2007, 11, 3), "default message");
        }

        @Test
        @DisplayName("null date with custom message")
        void testNullDateWithCustomMessage() {
            testNull("dateWithCustomMessage");
        }

        @Test
        @DisplayName("valid date with custom message")
        void testValidDateWithCustomMessage() {
            testValid("dateWithCustomMessage", LocalDate.of(2007, 12, 3));
        }

        @Test
        @DisplayName("invalid date with custom message")
        void testInvalidDateWithCustomMessage() {
            testInvalid("dateWithCustomMessage", LocalDate.of(2007, 11, 3), "custom message");
        }

        @Test
        @DisplayName("null time with default message")
        void testNullTimeWithDefaultMessage() {
            testNull("timeWithDefaultMessage");
        }

        @Test
        @DisplayName("valid time with default message")
        void testValidTimeWithDefaultMessage() {
            testValid("timeWithDefaultMessage", LocalTime.of(10, 15, 30));
        }

        @Test
        @DisplayName("invalid time with default message")
        void testInvalidTimeWithDefaultMessage() {
            testInvalid("timeWithDefaultMessage", LocalTime.of(9, 15, 30), "different default message");
        }

        @Test
        @DisplayName("null time with custom message")
        void testNullTimeWithCustomMessage() {
            testNull("timeWithCustomMessage");
        }

        @Test
        @DisplayName("valid time with custom message")
        void testValidTimeWithCustomMessage() {
            testValid("timeWithCustomMessage", LocalTime.of(10, 15, 30));
        }

        @Test
        @DisplayName("invalid time with custom message")
        void testInvalidTimeWithCustomMessage() {
            testInvalid("timeWithCustomMessage", LocalTime.of(9, 15, 30), "custom message");
        }

        private void testNull(String propertyName) {
            List<?> violations = validate(TestClass.class, propertyName, null);
            assertEquals(Collections.emptyList(), violations);
        }

        private void testValid(String propertyName, Object value) {
            List<?> violations = validate(TestClass.class, propertyName, value);
            assertEquals(Collections.emptyList(), violations);
        }

        private void testInvalid(String propertyName, Object value, String message) {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, propertyName, value);
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, TestConstraint.class);
            assertEquals(message, violation.getMessage());
            assertEquals(propertyName, violation.getPropertyPath().toString());
        }
    }

    private static final class TestClass {

        @TestConstraint
        private LocalDate dateWithDefaultMessage;

        @TestConstraint(message = "custom message")
        private LocalDate dateWithCustomMessage;

        @TestConstraint
        private LocalTime timeWithDefaultMessage;

        @TestConstraint(message = "custom message")
        private LocalTime timeWithCustomMessage;
    }

    @Constraint(validatedBy = { DefaultValidator.class, DifferentDefaultMessageValidator.class })
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraint {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    public static final class DefaultValidator extends BaseValidator<TestConstraint, LocalDate> {

        public DefaultValidator() {
            super(annotation -> (d, p) -> d.getMonth() == Month.DECEMBER);
        }
    }

    public static final class DifferentDefaultMessageValidator extends BaseValidator<TestConstraint, LocalTime> {

        public DifferentDefaultMessageValidator() {
            super(annotation -> (t, p) -> t.getHour() == 10);
            useReplacementMessageTemplate(TestConstraint::message, "default message", "different default message");
        }
    }

    @Nested
    @DisplayName("plus(Instant, TemporalAmount)")
    class Plus {

        @ParameterizedTest(name = "{1}")
        @CsvSource({
                "2007-12-03T10:15:30.00Z, PT1S, 2007-12-03T10:15:31.00Z",
                "2007-12-03T10:15:30.00Z, PT1M, 2007-12-03T10:16:30.00Z",
                "2007-12-03T10:15:30.00Z, PT1H, 2007-12-03T11:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1D, 2007-12-04T10:15:30.00Z" // 1 day is allowed for Duration
        })
        @DisplayName("with Duration")
        void testWithDuration(Instant value, Duration duration, Instant result) {
            assertEquals(result, BaseValidator.plus(value, duration));
        }

        @ParameterizedTest(name = "{1}")
        @CsvSource({
                "2007-12-03T10:15:30.00Z, P1D, 2007-12-04T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1M, 2008-01-03T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1Y, 2008-12-03T10:15:30.00Z"
        })
        @DisplayName("with Period")
        void testWithDuration(Instant value, Period period, Instant result) {
            assertEquals(result, BaseValidator.plus(value, period));
        }

        @ParameterizedTest(name = "{1}")
        @CsvSource({
                "2007-12-03T10:15:30.00Z, PT1S, 2007-12-03T10:15:31.00Z",
                "2007-12-03T10:15:30.00Z, PT1M, 2007-12-03T10:16:30.00Z",
                "2007-12-03T10:15:30.00Z, PT1H, 2007-12-03T11:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1D, 2007-12-04T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1M, 2008-01-03T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1Y, 2008-12-03T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1Y1M1DT1H1M1S, 2009-01-04T11:16:31.00Z"
        })
        @DisplayName("with ISODuration")
        void testWithDuration(Instant value, @ConvertWith(ISODurationConverter.class) TemporalAmount duration, Instant result) {
            assertEquals(result, BaseValidator.plus(value, duration));
        }
    }

    @Nested
    @DisplayName("minus(Instant, TemporalAmount)")
    class Minus {

        @ParameterizedTest(name = "{1}")
        @CsvSource({
                "2007-12-03T10:15:30.00Z, PT1S, 2007-12-03T10:15:29.00Z",
                "2007-12-03T10:15:30.00Z, PT1M, 2007-12-03T10:14:30.00Z",
                "2007-12-03T10:15:30.00Z, PT1H, 2007-12-03T09:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1D, 2007-12-02T10:15:30.00Z" // 1 day is allowed for Duration
        })
        @DisplayName("with Duration")
        void testWithDuration(Instant value, Duration duration, Instant result) {
            assertEquals(result, BaseValidator.minus(value, duration));
        }

        @ParameterizedTest(name = "{1}")
        @CsvSource({
                "2007-12-03T10:15:30.00Z, P1D, 2007-12-02T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1M, 2007-11-03T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1Y, 2006-12-03T10:15:30.00Z"
        })
        @DisplayName("with Period")
        void testWithDuration(Instant value, Period period, Instant result) {
            assertEquals(result, BaseValidator.minus(value, period));
        }

        @ParameterizedTest(name = "{1}")
        @CsvSource({
                "2007-12-03T10:15:30.00Z, PT1S, 2007-12-03T10:15:29.00Z",
                "2007-12-03T10:15:30.00Z, PT1M, 2007-12-03T10:14:30.00Z",
                "2007-12-03T10:15:30.00Z, PT1H, 2007-12-03T09:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1D, 2007-12-02T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1M, 2007-11-03T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1Y, 2006-12-03T10:15:30.00Z",
                "2007-12-03T10:15:30.00Z, P1Y1M1DT1H1M1S, 2006-11-02T09:14:29.00Z"
        })
        @DisplayName("with ISODuration")
        void testWithDuration(Instant value, @ConvertWith(ISODurationConverter.class) TemporalAmount duration, Instant result) {
            assertEquals(result, BaseValidator.minus(value, duration));
        }
    }

    private static final class ISODurationConverter implements ArgumentConverter {

        @Override
        public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
            return ISODuration.parse((String) source);
        }
    }
}
