/*
 * CalendarValidator.java
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

import java.lang.annotation.Annotation;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Calendar} validators. These delegate to {@link ZonedDateTime} validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 */
public abstract class CalendarValidator<A extends Annotation> implements ConstraintValidator<A, Calendar> {

    private final ConstraintValidator<A, ZonedDateTime> validator;

    /**
     * Creates a new validator.
     *
     * @param validator The backing validator.
     */
    protected CalendarValidator(ConstraintValidator<A, ZonedDateTime> validator) {
        this.validator = Objects.requireNonNull(validator);
    }

    @Override
    public void initialize(A constraintAnnotation) {
        validator.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Calendar value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return validator.isValid(toZonedDateTime(value), context);
    }

    private ZonedDateTime toZonedDateTime(Calendar calendar) {
        // This is exactly what GregorianCalendar does
        ZoneId zoneId = calendar.getTimeZone().toZoneId();
        return ZonedDateTime.ofInstant(calendar.toInstant(), zoneId);
    }
}
