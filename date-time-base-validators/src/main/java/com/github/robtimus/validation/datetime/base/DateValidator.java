/*
 * DateValidator.java
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
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Date} validators. These delegate to {@link Instant} validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 */
public abstract class DateValidator<A extends Annotation> implements ConstraintValidator<A, Date> {

    private final ConstraintValidator<A, ? super Instant> validator;

    /**
     * Creates a new validator.
     *
     * @param validator The backing validator.
     */
    protected DateValidator(ConstraintValidator<A, ? super Instant> validator) {
        this.validator = Objects.requireNonNull(validator);
    }

    @Override
    public void initialize(A constraintAnnotation) {
        validator.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return validator.isValid(value.toInstant(), context);
    }
}
