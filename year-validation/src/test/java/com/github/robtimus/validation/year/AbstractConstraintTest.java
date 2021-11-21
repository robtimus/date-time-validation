/*
 * AbstractConstraintTest.java
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

package com.github.robtimus.validation.year;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import javax.validation.ClockProvider;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

abstract class AbstractConstraintTest {

    private static final Comparator<ConstraintViolation<?>> BY_PATH = comparing((ConstraintViolation<?> v) -> v.getPropertyPath().toString());
    private static final Comparator<ConstraintViolation<?>> BY_ANNOTATION = comparing(
            (ConstraintViolation<?> v) -> v.getConstraintDescriptor().getAnnotation().annotationType().toString());
    private static final Comparator<ConstraintViolation<?>> COMPARATOR = BY_PATH.thenComparing(BY_ANNOTATION);

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

    static Instant utcInstantAtDefaultZone(String text) {
        return Instant.parse(text)
                .atZone(ZoneId.of("UTC")) //$NON-NLS-1$
                .withZoneSameLocal(ZoneId.systemDefault())
                .toInstant();
    }

    static Instant utcInstantAtOffset(String text, int hours) {
        return Instant.parse(text)
                .atZone(ZoneId.of("UTC")) //$NON-NLS-1$
                .withZoneSameLocal(ZoneOffset.ofHours(hours))
                .toInstant();
    }

    static Instant utcInstantAtOffsetAfterSystem(String text, int hours) {
        ZonedDateTime zonedDateTime = Instant.parse(text)
                .atZone(ZoneId.of("UTC")) //$NON-NLS-1$
                .withZoneSameLocal(ZoneId.systemDefault());

        return addOffset(zonedDateTime, hours)
                .toInstant();
    }

    static OffsetDateTime offsetDateTimeAtDefaultZone(String text) {
        return OffsetDateTime.parse(text)
                .atZoneSimilarLocal(ZoneId.systemDefault())
                .toOffsetDateTime();
    }

    static OffsetDateTime offsetDateTimeAtOffsetAfterSystem(String text, int hours) {
        ZonedDateTime zonedDateTime = OffsetDateTime.parse(text)
                .atZoneSimilarLocal(ZoneId.systemDefault());

        return addOffset(zonedDateTime, hours)
                .toOffsetDateTime();
    }

    static ZonedDateTime zonedDateTimeAtDefaultZone(String text) {
        return ZonedDateTime.parse(text)
                .withZoneSameLocal(ZoneId.systemDefault());
    }

    static ZonedDateTime zonedDateTimeAtOffsetAfterSystem(String text, int hours) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(text)
                .withZoneSameLocal(ZoneId.systemDefault());

        int offset = zonedDateTime.getOffset().getTotalSeconds();
        offset += hours * 3600;
        return zonedDateTime.withZoneSameLocal(ZoneOffset.ofTotalSeconds(offset));
    }

    static ZonedDateTime addOffset(ZonedDateTime zonedDateTime, int hours) {
        int offset = zonedDateTime.getOffset().getTotalSeconds();
        offset += hours * 3600;
        return zonedDateTime.withZoneSameLocal(ZoneOffset.ofTotalSeconds(offset));
    }
}
