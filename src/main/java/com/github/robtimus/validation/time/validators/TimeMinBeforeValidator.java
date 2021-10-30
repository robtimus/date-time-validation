/*
 * TimeMinBeforeValidator.java
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
import com.github.robtimus.validation.time.TimeMinBefore;

/**
 * Container class for constraint validators for {@link TimeMinBefore}.
 *
 * @author Rob Spoor
 */
public final class TimeMinBeforeValidator {

    private TimeMinBeforeValidator() {
    }

    /**
     * A constraint validator for {@link TimeMinBefore} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends AbstractDatePartValidator<TimeMinBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(TimeMinBefore::moment, TimeMinBefore::duration, TimeMinBefore::zoneId, DateTimeValidator::toLocalTime,
                    new MinBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMinBefore} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends AbstractCalendarPartValidator<TimeMinBefore, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(TimeMinBefore::moment, TimeMinBefore::duration, TimeMinBefore::zoneId, ZonedDateTime::toLocalTime,
                    new MinBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMinBefore} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends AbstractTemporalAccessorPartValidator<TimeMinBefore, Instant, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(TimeMinBefore::moment, TimeMinBefore::duration, TimeMinBefore::zoneId, DateTimeValidator::toLocalTime,
                    new MinBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMinBefore} for {@link LocalDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForLocalDateTime extends AbstractTemporalAccessorPartValidator<TimeMinBefore, LocalDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForLocalDateTime() {
            super(TimeMinBefore::moment, TimeMinBefore::duration, TimeMinBefore::zoneId, LocalDateTime::toLocalTime,
                    new MinBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMinBefore} for {@link OffsetDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForOffsetDateTime extends AbstractTemporalAccessorPartValidator<TimeMinBefore, OffsetDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForOffsetDateTime() {
            super(TimeMinBefore::moment, TimeMinBefore::duration, TimeMinBefore::zoneId, DateTimeValidator::toLocalTime,
                    new MinBeforeValidator.ForLocalTime());
        }
    }

    /**
     * A constraint validator for {@link TimeMinBefore} for {@link ZonedDateTime}.
     *
     * @author Rob Spoor
     */
    public static class ForZonedDateTime extends AbstractTemporalAccessorPartValidator<TimeMinBefore, ZonedDateTime, LocalTime> {

        /**
         * Creates a new validator.
         */
        public ForZonedDateTime() {
            super(TimeMinBefore::moment, TimeMinBefore::duration, TimeMinBefore::zoneId, DateTimeValidator::toLocalTime,
                    new MinBeforeValidator.ForLocalTime());
        }
    }
}
