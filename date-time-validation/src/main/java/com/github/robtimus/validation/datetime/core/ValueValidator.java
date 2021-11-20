/*
 * ValueValidator.java
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

package com.github.robtimus.validation.datetime.core;

import java.lang.annotation.Annotation;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate the entire value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 */
public abstract class ValueValidator<A extends Annotation, T extends TemporalAccessor> extends BaseValidator<A, T> {

    /**
     * Creates a new validator.
     *
     * @param predicateExtractor A function that extracts a predicate from a constraint annotation.
     *                               This predicate will be called from {@link #isValid(Object, ConstraintValidatorContext)},
     *                               with as arguments the value to validate and the {@link ClockProvider} returned by
     *                               {@link ConstraintValidatorContext#getClockProvider()}.
     */
    protected ValueValidator(Function<A, BiPredicate<T, ClockProvider>> predicateExtractor) {
        super(predicateExtractor);
    }
}
