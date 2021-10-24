/*
 * DateMaxAfter.java
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

package com.github.robtimus.validation.time;

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
import java.time.LocalDate;
import java.time.ZoneId;
import javax.validation.ClockProvider;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.github.robtimus.validation.time.DateMaxAfter.List;
import com.github.robtimus.validation.time.validators.DateMaxAfterValidator;

/**
 * Validates that the date part of a date/time object is not more than a specific duration after a specific moment in time.
 * More specifically, for a date/time object {@code value}, validates that {@code value.date &lt;= moment + duration}.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@link java.util.Date}</li>
 * <li>{@link java.util.Calendar}</li>
 * <li>{@link java.time.Instant}</li>
 * <li>{@link java.time.LocalDateTime}</li>
 * <li>{@link java.time.OffsetDateTime}</li>
 * <li>{@link java.time.ZonedDateTime}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 * Note that for {@link java.util.Date} and {@link java.time.Instant}, that don't have any time zone information, {@link ZoneId#systemDefault()} is
 * used to determine the date.
 *
 * @author Rob Spoor
 */
@Documented
@Constraint(validatedBy = { DateMaxAfterValidator.ForDate.class,
        DateMaxAfterValidator.ForCalendar.class,
        DateMaxAfterValidator.ForInstant.class,
        DateMaxAfterValidator.ForLocalDateTime.class,
        DateMaxAfterValidator.ForOffsetDateTime.class,
        DateMaxAfterValidator.ForZonedDateTime.class
})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface DateMaxAfter {

    /**
     * The error message.
     */
    String message() default "{com.github.robtimus.validation.time.DateMaxAfter.message}";

    /**
     * The validation groups.
     */
    Class<?>[] groups() default { };

    /**
     * The payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * The maximum amount of time, as an <a href="https://en.wikipedia.org/wiki/ISO_8601" target="_blank">ISO 8601 duration</a>, that the date part of
     * a date/time object can be after the value specified in {@link #moment()}.
     * <p>
     * This duration may only have a date part, not a time part.
     */
    String duration();

    /**
     * The moment against which to validate.
     * This should either be a value that can be parsed to {@link LocalDate}, or {@code now} to use the current moment in time as defined by the
     * {@link ClockProvider} attached to the {@link Validator} or {@link ValidatorFactory}. The default {@link ClockProvider} defines the current time
     * according to the virtual machine, applying the current default time zone if needed.
     */
    String moment();

    /**
     * Defines several {@link DateMaxAfter} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {

        /**
         * The {@link DateMaxAfter} annotations.
         */
        DateMaxAfter[] value();
    }
}
