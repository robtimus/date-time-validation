/*
 * MinutePrecision.java
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

package com.github.robtimus.validation.time.precision;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.github.robtimus.validation.time.precision.MinutePrecision.List;
import com.github.robtimus.validation.time.precision.validators.MinutePrecisionValidator;

/**
 * Validates that a date/time object should have minute precision.
 * More specifically, for a date/time object {@code object}, validates that {@code object.second == 0 && object.nanosecond == 0}.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@link java.util.Date}</li>
 * <li>{@link java.util.Calendar}</li>
 * <li>{@link java.time.Instant}</li>
 * <li>{@link java.time.temporal.TemporalAccessor}, as long as the implementation supports both {@link ChronoField#SECOND_OF_MINUTE} and
 *     {@link ChronoField#NANO_OF_SECOND} as argument {@link TemporalAccessor#get(TemporalField)}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Rob Spoor
 */
@Documented
@Constraint(validatedBy = { MinutePrecisionValidator.ForDate.class,
        MinutePrecisionValidator.ForCalendar.class,
        MinutePrecisionValidator.ForInstant.class,
        MinutePrecisionValidator.ForTemporalAccessor.class
})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface MinutePrecision {

    /**
     * The error message.
     */
    String message() default "{com.github.robtimus.validation.time.precision.MinutePrecision.message}";

    /**
     * The validation groups.
     */
    Class<?>[] groups() default { };

    /**
     * The payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@link MinutePrecision} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * The {@link MinutePrecision} annotations.
         */
        MinutePrecision[] value();
    }
}
