/*
 * InstantEnumValidator.java
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
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.function.Function;

/**
 * The base for all {@link Instant} validators that validate only an enumerated part like the month.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <E> The enumerated part type.
 */
public abstract class InstantEnumValidator<A extends Annotation, E extends Enum<E>> extends TemporalAccessorEnumValidator<A, Instant, E> {

    /**
     * Creates a new validator that validates dates against a set of allowed values.
     *
     * @param allowedValuesExtractor A function that extracts the allowed values from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts the enumerated part from an instant.
     */
    protected InstantEnumValidator(Function<A, Set<E>> allowedValuesExtractor,
            Function<A, String> zoneIdExtractor,
            Function<ZonedDateTime, E> partExtractor) {

        super(allowedValuesExtractor, zoneIdExtractor, (t, z) -> partExtractor.apply(t.atZone(z)));
    }
}
