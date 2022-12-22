/*
 * CalendarValidatorTest.java
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
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "javadoc", "nls" })
//public because of the constraint and validators
public class CalendarValidatorTest extends AbstractValidatorTest {

    @Nested
    @DisplayName("validator")
    class Validator {

        @Test
        @DisplayName("null value")
        void testNullValue() {
            List<?> violations = validate(TestClass.class, "calendar", null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("valid value")
        void testValidValue() {
            List<?> violations = validate(TestClass.class, "calendar",
                    GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]")));
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        @DisplayName("invalid value")
        void testInvalidValue() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "calendar",
                    GregorianCalendar.from(ZonedDateTime.parse("2007-12-03T10:15:29.999+01:00[Europe/Paris]")));
            assertEquals(1, violations.size());

            ConstraintViolation<?> violation = violations.get(0);
            assertAnnotation(violation, TestConstraint.class);
        }
    }

    private static final class TestClass {

        @TestConstraint(moment = "2007-12-03T10:15:30+01:00[Europe/Paris]")
        private Calendar calendar;
    }

    @Constraint(validatedBy = TestValidator.class)
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    public @interface TestConstraint {

        String message() default "default message";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String moment();
    }

    public static final class TestValidator extends CalendarValidator<TestConstraint> {

        public TestValidator() {
            super(new ZonedDateTimeValidator());
        }
    }

    private static final class ZonedDateTimeValidator implements ConstraintValidator<TestConstraint, ZonedDateTime> {

        private ZonedDateTime moment;

        @Override
        public void initialize(TestConstraint constraintAnnotation) {
            moment = ZonedDateTime.parse(constraintAnnotation.moment());
        }

        @Override
        public boolean isValid(ZonedDateTime value, ConstraintValidatorContext context) {
            return value == null || value.equals(moment);
        }
    }
}
