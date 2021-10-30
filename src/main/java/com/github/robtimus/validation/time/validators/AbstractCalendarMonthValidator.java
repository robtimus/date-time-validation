/*
 * AbstractCalendarMonthValidator.java
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
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintValidatorContext;

/**
 * The base for all {@link Calendar} validators that validate only the month.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 */
public abstract class AbstractCalendarMonthValidator<A extends Annotation> extends DateTimeValidator<A, Calendar> {

    private final Function<A, Set<Month>> allowedMonthsExtractor;
    private final Function<A, String> zoneIdExtractor;

    private Set<Month> allowedMonths;
    private ZoneId zoneId;

    /**
     * Creates a new validator that validates dates against a set of allowed months.
     *
     * @param allowedMonthsExtractor A function that extracts the allowed months from a constraint annotation.
     * @param zoneIdExtractor A function that extracts the zone id from a constraint annotation.
     */
    protected AbstractCalendarMonthValidator(Function<A, Set<Month>> allowedMonthsExtractor,
            Function<A, String> zoneIdExtractor) {

        this.allowedMonthsExtractor = allowedMonthsExtractor;
        this.zoneIdExtractor = zoneIdExtractor;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        initializeAllowedMonths(constraintAnnotation);
        initializeZoneId(constraintAnnotation);
    }

    private void initializeAllowedMonths(A constraintAnnotation) {
        allowedMonths = allowedMonthsExtractor.apply(constraintAnnotation);
    }

    private void initializeZoneId(A constraintAnnotation) {
        String zoneIdText = zoneIdExtractor.apply(constraintAnnotation);
        zoneId = toZoneId(zoneIdText);
    }

    @Override
    public boolean isValid(Calendar value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        ZonedDateTime zonedDateTime = toZonedDateTime(value, zoneId);
        Month month = zonedDateTime.getMonth();
        return allowedMonths.contains(month);
    }
}
