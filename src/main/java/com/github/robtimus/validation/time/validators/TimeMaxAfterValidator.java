/*
 * TimeMaxAfterValidator.java
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import com.github.robtimus.validation.time.TimeMaxAfter;

/**
 * Container class for constraint validators for {@link TimeMaxAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeMaxAfterValidator {

    private TimeMaxAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<TimeMaxAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeMaxAfter::moment, TimeMaxAfter::duration, DateTimeValidator::toLocalTime, new MaxAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<TimeMaxAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeMaxAfter::moment, TimeMaxAfter::duration, ZonedDateTime::toLocalTime, new MaxAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<TimeMaxAfter, Instant, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeMaxAfter::moment, TimeMaxAfter::duration, DateTimeValidator::toLocalTime, new MaxAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<TimeMaxAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeMaxAfter::moment, TimeMaxAfter::duration, LocalDateTime::toLocalTime, new MaxAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<TimeMaxAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeMaxAfter::moment, TimeMaxAfter::duration, OffsetDateTime::toLocalTime, new MaxAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMaxAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<TimeMaxAfter, ZonedDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeMaxAfter::moment, TimeMaxAfter::duration, ZonedDateTime::toLocalTime, new MaxAfterValidator.ForLocalTime());
        }
    }
}
