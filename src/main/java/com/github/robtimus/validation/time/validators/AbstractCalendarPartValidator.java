/*
 * AbstractCalendarPartValidator.java
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Calendar} validators that validate only part of the value.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <P> The {@link TemporalAccessor} type that describes the part to validate.
 */
public abstract class AbstractCalendarPartValidator<A extends Annotation, P extends TemporalAccessor> extends DateTimeValidator<A, Calendar> {

    private final Function<A, String> momentExtractor;
    private final Function<A, String> durationExtractor;
    private final Function<A, String> zoneIdExtractor;
    private final Function<ZonedDateTime, P> partExtractor;
    private final AbstractTemporalAccessorValidator<?, P> partValidator;

    private ZoneId zoneId;

    /**
     * Creates a new validator that only validates calendars against a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a zoned date/time.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected AbstractCalendarPartValidator(Function<A, String> momentExtractor,
            Function<A, String> zoneIdExtractor,
            Function<ZonedDateTime, P> partExtractor,
            AbstractTemporalAccessorValidator<?, P> partValidator) {

        this.momentExtractor = momentExtractor;
        this.durationExtractor = null;
        this.zoneIdExtractor = zoneIdExtractor;
        this.partExtractor = partExtractor;
        this.partValidator = partValidator;
    }

    /**
     * Creates a new validator that only validates calendars against a specific duration before or after a specific moment in time.
     *
     * @param momentExtractor A function that extracts the moment from a constraint annotation.
     * @param durationExtractor A function that extracts the duration from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     * @param partExtractor A function that extracts a part from a zoned date/time.
     * @param partValidator The validator to use for validating extracted parts.
     */
    protected AbstractCalendarPartValidator(Function<A, String> momentExtractor,
            Function<A, String> durationExtractor,
            Function<A, String> zoneIdExtractor,
            Function<ZonedDateTime, P> partExtractor,
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
    public boolean isValid(Calendar value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        ZonedDateTime zonedDateTime = toZonedDateTime(value, zoneId);
        P part = partExtractor.apply(zonedDateTime);
        return partValidator.isValid(part, context);
    }

    private static ZonedDateTime toZonedDateTime(Calendar calendar, ZoneId zoneId) {
        // This is exactly what GregorianCalendar.toZonedDateTime() does
        if (zoneId == null) {
            zoneId = calendar.getTimeZone().toZoneId();
        }
        return ZonedDateTime.ofInstant(calendar.toInstant(), zoneId);
    }
}
