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
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
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
     * A string representing the system zone id.
     *
     * @see ZoneId#systemDefault()
     */
    public static final String SYSTEM_ZONE_ID = "system"; //$NON-NLS-1$

    /**
     * A string representing the provided zone id.
     */
    public static final String PROVIDED_ZONE_ID = "provided"; //$NON-NLS-1$

    /**
     * Creates a new validator.
     */
    protected DateTimeValidator() {
        // no fields
    }

    /**
     * Returns a {@link ZoneId} for a specific text value.
     *
     * @param text The text value for which to return a {@link ZoneId}.
     * @return {@link ZoneId#systemDefault()} if the given text equals {@link DateTimeValidator#SYSTEM_ZONE_ID},
     *         {@code null} if the given text equals {@link DateTimeValidator#PROVIDED_ZONE_ID},
     *         or the result of calling {@link ZoneId#of(String)} otherwise.
     * @throws NullPointerException If the given text value is {@code null}.
     */
    protected static ZoneId toZoneId(String text) {
        Objects.requireNonNull(text);
        switch (text) {
        case SYSTEM_ZONE_ID:
            return ZoneId.systemDefault();
        case PROVIDED_ZONE_ID:
            return null;
        default:
            return ZoneId.of(text);
        }
    }

    /**
     * Wraps a zone id extraction function so it will throw an {@link IllegalStateException} if the extracted zone id (as a text value) does not equal
     * {@link DateTimeValidator#SYSTEM_ZONE_ID}. This can be used for validators of types that don't support zone information like {@link LocalDate}.
     *
     * @param <A> The constraint annotation type from which to extract the zone id.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @return A function that extracts the zone id from a constraint annotation, but throws an {@link IllegalStateException} if the extracted zone id
     *         does not equal {@link DateTimeValidator#SYSTEM_ZONE_ID}.
     * @throws NullPointerException If the given function is {@code null}.
     */
    protected static <A extends Annotation> Function<A, String> systemOnlyZoneId(Function<A, String> zoneIdExtractor) {
        Objects.requireNonNull(zoneIdExtractor);
        return constraint -> {
            String zoneId = zoneIdExtractor.apply(constraint);
            if (!SYSTEM_ZONE_ID.equals(zoneId)) {
                throw new IllegalStateException(String.format("zoneId should be '%s', is '%s'", SYSTEM_ZONE_ID, zoneId)); //$NON-NLS-1$
            }
            return zoneId;
        };
    }

    /**
     * Wraps a zone id extraction function so it will throw an {@link IllegalStateException} if the extracted zone id (as a text value) equals
     * {@link DateTimeValidator#PROVIDED_ZONE_ID}. This can be used for validators of types that don't contain any zone information like {@link Date}
     * or {@link Instant}.
     *
     * @param <A> The constraint annotation type from which to extract the zone id.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @return A function that extracts the zone id from a constraint annotation, but throws an {@link IllegalStateException} if the extracted zone id
     *         equals {@link DateTimeValidator#PROVIDED_ZONE_ID}.
     * @throws NullPointerException If the given function is {@code null}.
     */
    protected static <A extends Annotation> Function<A, String> nonProvidedZoneId(Function<A, String> zoneIdExtractor) {
        Objects.requireNonNull(zoneIdExtractor);
        return constraint -> {
            String zoneId = zoneIdExtractor.apply(constraint);
            if (PROVIDED_ZONE_ID.equals(zoneId)) {
                throw new IllegalStateException(String.format("zoneId should not be '%s'", PROVIDED_ZONE_ID)); //$NON-NLS-1$
            }
            return zoneId;
        };
    }

    /**
     * Negates a predicate. This is a utility method that simply returns {@code predicate.negate()}, and is equivalent to the static method that was
     * added to {@link Predicate} in Java 11.
     * <p>
     * This method can be used with method references. Example: {@code not(LocalDate::isAfter)}.
     *
     * @param <T> The type to test.
     * @param predicate The predicate to negate.
     * @return The negated predicate.
     * @throws NullPointerException If the given predicate is {@code null}.
     */
    protected static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }
}
