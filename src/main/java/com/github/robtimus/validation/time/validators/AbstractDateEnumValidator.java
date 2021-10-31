/*
 * AbstractDateEnumValidator.java
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Date} validators that validate only an enumerated part like the month.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <E> The enumerated value type.
 */
public abstract class AbstractDateEnumValidator<A extends Annotation, E extends Enum<E>> extends DateTimeValidator<A, Date> {

    private final Function<A, Set<E>> allowedValuesExtractor;
    private final Function<A, String> zoneIdExtractor;
    private final Function<ZonedDateTime, E> valueExtractor;

    private Set<E> allowedValues;
    private ZoneId zoneId;

    /**
     * Creates a new validator that validates dates against a set of allowed values.
     *
     * @param allowedValuesExtractor A function that extracts the allowed values from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param valueExtractor A function that extracts the value from a {@link ZonedDateTime}..
     */
    protected AbstractDateEnumValidator(Function<A, Set<E>> allowedValuesExtractor,
            Function<A, String> zoneIdExtractor,
            Function<ZonedDateTime, E> valueExtractor) {

        this.allowedValuesExtractor = allowedValuesExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
        this.valueExtractor = valueExtractor;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        initializeAllowedValues(constraintAnnotation);
        initializeZoneId(constraintAnnotation);
    }

    private void initializeAllowedValues(A constraintAnnotation) {
        allowedValues = allowedValuesExtractor.apply(constraintAnnotation);
    }

    private void initializeZoneId(A constraintAnnotation) {
        String zoneIdText = zoneIdExtractor.apply(constraintAnnotation);
        zoneId = toZoneId(zoneIdText);
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Instant instant = value.toInstant();
        ZonedDateTime zonedDateTime = toZonedDateTime(instant, zoneId);
        E enumValue = valueExtractor.apply(zonedDateTime);
        return allowedValues.contains(enumValue);
    }
}
