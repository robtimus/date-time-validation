/*
 * AbstractTemporalAccessorPartValidator.java
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link TemporalAccessor} validators that validate only part of the value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The {@link TemporalAccessor} type to validate.
 * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
 */
public abstract class AbstractTemporalAccessorPartValidator<A extends Annotation, T extends TemporalAccessor, P extends TemporalAccessor>
        extends DateTimeValidator<A, T> {

    private final Function<A, String> momentExtractor;
    private final Function<A, String> durationExtractor;
    private final Function<A, String> zoneIdExtractor;
    private final BiFunction<T, ZoneId, P> partExtractor;
    private final AbstractTemporalAccessorValidator<?, P> partValidator;

    private ZoneId zoneId;

    /**
     * Creates a new validator that only validates temporal accessors against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}..
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected AbstractTemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            AbstractTemporalAccessorValidator<?, P> partValidator) {

        this.momentExtractor = momentExtractor;
        this.durationExtractor = null;
        this.zoneIdExtractor = zoneIdExtractor;
        this.partExtractor = partExtractor;
        this.partValidator = partValidator;
    }

    /**
     * Creates a new validator that only validates dates against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment value from a constraint annotation.
     * @param durationExtractor A function that extracts the duration value from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a {@link TemporalAccessor}..
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected AbstractTemporalAccessorPartValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor,
            Function<A, String> zoneIdExtractor,
            BiFunction<T, ZoneId, P> partExtractor,
            AbstractTemporalAccessorValidator<?, P> partValidator) {

        this.momentExtractor = momentExtractor;
        this.durationExtractor = durationExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
        this.partExtractor = partExtractor;
        this.partValidator = partValidator;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        String momentText = momentExtractor.apply(constraintAnnotation);
        String durationText = durationExtractor != null ? durationExtractor.apply(constraintAnnotation) : null;

        partValidator.initialize(momentText, durationText);

        initializeZoneId(constraintAnnotation);
    }

    private void initializeZoneId(A constraintAnnotation) {
        String zoneIdText = zoneIdExtractor.apply(constraintAnnotation);
        switch (zoneIdText) {
        case SYSTEM_ZONE_ID:
            zoneId = ZoneId.systemDefault();
            break;
        case PROVIDED_ZONE_ID:
            zoneId = null;
            break;
        default:
            zoneId = ZoneId.of(zoneIdText);
            break;
        }
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        P part = partExtractor.apply(value, zoneId);
        return partValidator.isValid(part, context);
    }

    static LocalTime toLocalTime(LocalDateTime localDateTime, @SuppressWarnings("unused") ZoneId zoneId) {
        // zoneId is ignored
        return localDateTime.toLocalTime();
    }

    static LocalTime toLocalTime(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? offsetDateTime.toLocalTime() : offsetDateTime.atZoneSameInstant(zoneId).toLocalTime();
    }

    static LocalTime toLocalTime(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? zonedDateTime.toLocalTime() : zonedDateTime.withZoneSameInstant(zoneId).toLocalTime();
    }

    static LocalDate toLocalDate(LocalDateTime localDateDate, @SuppressWarnings("unused") ZoneId zoneId) {
        // zoneId is ignored
        return localDateDate.toLocalDate();
    }

    static LocalDate toLocalDate(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? offsetDateTime.toLocalDate() : offsetDateTime.atZoneSameInstant(zoneId).toLocalDate();
    }

    static LocalDate toLocalDate(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? zonedDateTime.toLocalDate() : zonedDateTime.withZoneSameInstant(zoneId).toLocalDate();
    }
}
