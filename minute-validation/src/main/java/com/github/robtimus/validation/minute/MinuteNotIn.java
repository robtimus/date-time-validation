/*
 * MinuteNotIn.java
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

package com.github.robtimus.validation.minute;

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
import java.time.ZoneId;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.github.robtimus.validation.minute.MinuteNotIn.List;
import com.github.robtimus.validation.minute.validators.MinuteNotInValidator;

/**
 * Validates that the minute of a date/time object is not one of a given list of values.
 * More specifically, for a date/time object {@code object}, validates that {@code !value.contains(object.minute)}.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@link java.util.Date}</li>
 * <li>{@link java.util.Calendar}</li>
 * <li>{@link java.time.Instant}</li>
 * <li>{@link java.time.LocalDateTime}</li>
 * <li>{@link java.time.LocalTime}</li>
 * <li>{@link java.time.OffsetDateTime}</li>
 * <li>{@link java.time.OffsetTime}</li>
 * <li>{@link java.time.ZonedDateTime}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Rob Spoor
 */
@Documented
@Constraint(validatedBy = { MinuteNotInValidator.ForDate.class,
        MinuteNotInValidator.ForCalendar.class,
        MinuteNotInValidator.ForInstant.class,
        MinuteNotInValidator.ForLocalDateTime.class,
        MinuteNotInValidator.ForLocalTime.class,
        MinuteNotInValidator.ForOffsetDateTime.class,
        MinuteNotInValidator.ForOffsetTime.class,
        MinuteNotInValidator.ForZonedDateTime.class
})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface MinuteNotIn {

    /**
     * The error message.
     */
    String message() default "{com.github.robtimus.validation.minute.MinuteNotIn.message}";

    /**
     * The validation groups.
     */
    Class<?>[] groups() default { };

    /**
     * The payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * The disallowed minutes.
     */
    int[] value();

    /**
     * The zone id to use. This should be {@code system} for the value returned by {@link ZoneId#systemDefault()}, {@code provided} for the zone id
     * from the actual value, or otherwise a value that is accepted by {@link java.time.ZoneId#of(String)} for a specific zone id.
     * <ul>
     * <li>For {@link java.util.Calendar}, {@link java.time.OffsetDateTime} and {@link java.time.ZonedDateTime}, if the zone id is not
     *     {@code provided}, the value is converted to the given zone id before extracting the minute.</li>
     * <li>For {@link java.time.OffsetTime}, if the zone id is not {@code provided}, the value is converted to the current date at the the given zone
     *     id before extracting the minute.</li>
     * <li>For {@link java.util.Date} and {@link java.time.Instant}, no zone id is available, so {@code provided} is not allowed.</li>
     * <li>For {@link java.time.LocalDateTime} and {@link java.time.LocalTime}, no zone id is applicable, so only the default value ({@code system})
     *     is allowed.</li>
     * </ul>
     */
    String zoneId() default "system";

    /**
     * Defines several {@link MinuteNotIn} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * The {@link MinuteNotIn} annotations.
         */
        MinuteNotIn[] value();
    }
}
