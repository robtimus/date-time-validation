/*
 * AbstractSystemOnlyZoneIdTest.java
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

package com.github.robtimus.validation.time.dayofweek;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
abstract class AbstractSystemOnlyZoneIdTest<T> extends AbstractConstraintTest {

    private final Class<?> beanTypeWithProvidedZoneId;
    private final Class<?> beanTypeWithZoneId;
    private final String propertyName;
    private final T value;

    AbstractSystemOnlyZoneIdTest(Class<?> beanTypeWithProvidedZoneId, Class<?> beanTypeWithZoneId, String propertyName, T value) {
        this.beanTypeWithProvidedZoneId = beanTypeWithProvidedZoneId;
        this.beanTypeWithZoneId = beanTypeWithZoneId;
        this.propertyName = propertyName;
        this.value = value;
    }

    @Test
    @DisplayName("zoneId 'provided' not allowed")
    void testZoneIdProvidedNotAllowed() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validate(() -> null, beanTypeWithProvidedZoneId, propertyName, value));

        Throwable cause = exception.getCause();
        assertThat(cause, instanceOf(IllegalStateException.class));
        assertEquals("zoneId should be 'system', is 'provided'", cause.getMessage());
    }

    @Test
    @DisplayName("explicit zoneId not allowed")
    void testZoneIdNotAllowed() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validate(() -> null, beanTypeWithZoneId, propertyName, value));

        Throwable cause = exception.getCause();
        assertThat(cause, instanceOf(IllegalStateException.class));
        assertEquals("zoneId should be 'system', is 'UTC'", cause.getMessage());
    }
}
