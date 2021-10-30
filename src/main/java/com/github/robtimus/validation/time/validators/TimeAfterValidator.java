/*
 * TimeAfterValidator.java
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
import com.github.robtimus.validation.time.TimeAfter;

/**
 * Container class for constraint validators for {@link TimeAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeAfterValidator {

    private TimeAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeAfter::moment, TimeAfter::zoneId, DateTimeValidator::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<TimeAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeAfter::moment, TimeAfter::zoneId, ZonedDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<TimeAfter, Instant, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeAfter::moment, TimeAfter::zoneId, DateTimeValidator::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<TimeAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeAfter::moment, TimeAfter::zoneId, LocalDateTime::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<TimeAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeAfter::moment, TimeAfter::zoneId, DateTimeValidator::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<TimeAfter, ZonedDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeAfter::moment, TimeAfter::zoneId, DateTimeValidator::toLocalTime, new AfterValidator.ForLocalTime());
        }
    }
}
