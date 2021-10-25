/*
 * TimeNotAfterValidator.java
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
import com.github.robtimus.validation.time.TimeNotAfter;

/**
 * Container class for constraint validators for {@link TimeNotAfter}.
 *
 * @author Rob Spoor
 */
public final class TimeNotAfterValidator {

    private TimeNotAfterValidator() {
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<TimeNotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, DateTimeValidator::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<TimeNotAfter, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, ZonedDateTime::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<TimeNotAfter, Instant, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, DateTimeValidator::toLocalTime, new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<TimeNotAfter, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, AbstractTemporalAccessorPartValidator::toLocalTime,
                    new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<TimeNotAfter, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, AbstractTemporalAccessorPartValidator::toLocalTime,
                    new NotAfterValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeNotAfter} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<TimeNotAfter, ZonedDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeNotAfter::moment, TimeNotAfter::zoneId, AbstractTemporalAccessorPartValidator::toLocalTime,
                    new NotAfterValidator.ForLocalTime());
        }
    }
}
