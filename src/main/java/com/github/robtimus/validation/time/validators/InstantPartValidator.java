/*
 * InstantPartValidator.java
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
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

/**
 * The base for all {@link Instant} validators that validate only part of the value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
 */
public abstract class InstantPartValidator<A extends Annotation, P extends TemporalAccessor> extends TemporalAccessorPartValidator<A, Instant, P> {

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected InstantPartValidator(Function<A, String> momentExtractor,
            Function<A, String> zoneIdExtractor,
            Function<ZonedDateTime, P> partExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        super(momentExtractor, zoneIdExtractor, (t, z) -> partExtractor.apply(t.atZone(z)), partValidator);
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected InstantPartValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor,
            Function<A, String> zoneIdExtractor,
            Function<ZonedDateTime, P> partExtractor,
            TemporalAccessorValidator<?, P> partValidator) {

        super(momentExtractor, durationExtractor, zoneIdExtractor, (t, z) -> partExtractor.apply(t.atZone(z)), partValidator);
    }
}
