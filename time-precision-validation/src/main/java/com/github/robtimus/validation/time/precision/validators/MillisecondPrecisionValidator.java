/*
 * MillisecondPrecisionValidator.java
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

package com.github.robtimus.validation.time.precision.validators;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiPredicate;
import java.util.function.Function;
import jakarta.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.ValueValidator;
import com.github.robtimus.validation.time.precision.MillisecondPrecision;

/**
 * Container class for constraint validators for {@link MillisecondPrecision}.
 *
 * @author Rob Spoor
 */
public final class MillisecondPrecisionValidator {

    private static final BiPredicate<TemporalAccessor, ClockProvider> PREDICATE
            = (value, provider) -> value.get(ChronoField.NANO_OF_SECOND) % 1000_000 == 0;

    private static final Function<MillisecondPrecision, BiPredicate<TemporalAccessor, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> PREDICATE;

    private MillisecondPrecisionValidator() {
    }

    /**
     * A constraint validator for {@link MillisecondPrecision} for {@link TemporalAccessor}.
     *
     * @author Rob Spoor
     */
    public static class ForTemporalAccessor extends ValueValidator<MillisecondPrecision, TemporalAccessor> {

        /**
         * Creates a new validator.
         */
        public ForTemporalAccessor() {
            super(PREDICATE_EXTRACTOR);
        }
    }
}
