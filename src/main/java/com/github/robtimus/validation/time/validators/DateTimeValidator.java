/*
 * DateTimeValidator.java
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

package com.github.robtimus.validation.time.validators;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.BiPredicate;
import javax.validation.ConstraintValidator;

/**
 * The base for all date/time object validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The type to validate.
 */
public abstract class DateTimeValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    /** A string representing the current date/time. */
    public static final String NOW = "now"; //$NON-NLS-1$

    /**
     * Creates a new validator.
     */
    protected DateTimeValidator() {
        // no fields
    }

    static LocalDate toLocalDate(Instant instant) {
        return toZonedDateTime(instant).toLocalDate();
    }

    static LocalTime toLocalTime(Instant instant) {
        return toZonedDateTime(instant).toLocalTime();
    }

    private static ZonedDateTime toZonedDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault());
    }

    static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }
}
