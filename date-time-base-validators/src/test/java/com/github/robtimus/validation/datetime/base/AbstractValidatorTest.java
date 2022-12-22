/*
 * AbstractValidatorTest.java
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

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import jakarta.validation.ClockProvider;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

abstract class AbstractValidatorTest {

    private static final Comparator<ConstraintViolation<?>> BY_PATH = comparing((ConstraintViolation<?> v) -> v.getPropertyPath().toString());
    private static final Comparator<ConstraintViolation<?>> BY_ANNOTATION = comparing(
            (ConstraintViolation<?> v) -> v.getConstraintDescriptor().getAnnotation().annotationType().toString());
    private static final Comparator<ConstraintViolation<?>> COMPARATOR = BY_PATH.thenComparing(BY_ANNOTATION);

    <T> List<ConstraintViolation<T>> validate(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return validate(null, beanType, propertyName, value, groups);
    }

    <T> List<ConstraintViolation<T>> validate(ClockProvider clockProvider, Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        try (ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .clockProvider(clockProvider)
                .buildValidatorFactory()) {

            Validator validator = factory.getValidator();
            return validator.validateValue(beanType, propertyName, value, groups).stream()
                    .sorted(COMPARATOR)
                    .collect(toList());
        }
    }

    void assertAnnotation(ConstraintViolation<?> violation, Class<? extends Annotation> annotationType) {
        assertNotNull(violation.getConstraintDescriptor());
        assertNotNull(violation.getConstraintDescriptor().getAnnotation());
        assertEquals(annotationType, violation.getConstraintDescriptor().getAnnotation().annotationType());
    }
}
