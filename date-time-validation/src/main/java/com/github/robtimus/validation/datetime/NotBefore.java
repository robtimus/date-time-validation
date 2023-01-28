/*
 * NotBefore.java
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

package com.github.robtimus.validation.datetime;

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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.validation.ClockProvider;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.FutureOrPresent;
import com.github.robtimus.validation.datetime.NotBefore.List;
import com.github.robtimus.validation.datetime.validators.NotBeforeValidator;

/**
 * Validates that a date/time object is not before a specific moment in time.
 * More specifically, for a date/time object {@code object}, validates that {@code object >= moment}.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@link java.util.Date}</li>
 * <li>{@link java.util.Calendar}</li>
 * <li>{@link java.time.Instant}</li>
 * <li>{@link java.time.LocalDate}</li>
 * <li>{@link java.time.LocalDateTime}</li>
 * <li>{@link java.time.LocalTime}</li>
 * <li>{@link java.time.MonthDay}</li>
 * <li>{@link java.time.OffsetDateTime}</li>
 * <li>{@link java.time.OffsetTime}</li>
 * <li>{@link java.time.Year}</li>
 * <li>{@link java.time.YearMonth}</li>
 * <li>{@link java.time.ZonedDateTime}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 * Note that {@code @NotBefore("now")} is equivalent to {@code @}{@link FutureOrPresent}.
 *
 * @author Rob Spoor
 */
@Documented
// Cannot delegate to MinAfter because that's not supported for MonthDay
@Constraint(validatedBy = { NotBeforeValidator.ForDate.class,
        NotBeforeValidator.ForCalendar.class,
        NotBeforeValidator.ForInstant.class,
        NotBeforeValidator.ForLocalDate.class,
        NotBeforeValidator.ForLocalDateTime.class,
        NotBeforeValidator.ForLocalTime.class,
        NotBeforeValidator.ForMonthDay.class,
        NotBeforeValidator.ForOffsetDateTime.class,
        NotBeforeValidator.ForOffsetTime.class,
        NotBeforeValidator.ForYear.class,
        NotBeforeValidator.ForYearMonth.class,
        NotBeforeValidator.ForZonedDateTime.class
})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface NotBefore {

    /**
     * The error message.
     */
    String message() default "{com.github.robtimus.validation.datetime.NotBefore.message}";

    /**
     * The validation groups.
     */
    Class<?>[] groups() default { };

    /**
     * The payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * The moment against which to validate.
     * This should either be a value that can be parsed to the type of value to validate, or {@code now} to use the current moment in time as defined
     * by the {@link ClockProvider} attached to the {@link Validator} or {@link ValidatorFactory}. The default {@link ClockProvider} defines the
     * current time according to the virtual machine, applying the current default time zone if needed.
     * <p>
     * For {@link Date}, this value will be parsed using {@link DateTimeFormatter#ISO_INSTANT}; for {@link Calendar}, using
     * {@link DateTimeFormatter#ISO_ZONED_DATE_TIME}.
     */
    String moment();

    /**
     * Defines several {@link NotBefore} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * The {@link NotBefore} annotations.
         */
        NotBefore[] value();
    }
}
