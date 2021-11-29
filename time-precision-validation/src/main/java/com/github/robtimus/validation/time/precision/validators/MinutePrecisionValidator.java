/*
 * MinutePrecisionValidator.java
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

package com.github.robtimus.validation.time.precision.validators;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Function;
import javax.validation.ClockProvider;
import com.github.robtimus.validation.datetime.base.CalendarValidator;
import com.github.robtimus.validation.datetime.base.DateValidator;
import com.github.robtimus.validation.datetime.base.ValueValidator;
import com.github.robtimus.validation.time.precision.MinutePrecision;

/**
 * Container class for constraint validators for {@link MinutePrecision}.
 *
 * @author Rob Spoor
 */
public final class MinutePrecisionValidator {

    private static final BiPredicate<TemporalAccessor, ClockProvider> PREDICATE
            = (value, provider) -> value.get(ChronoField.SECOND_OF_MINUTE) == 0 && value.get(ChronoField.NANO_OF_SECOND) == 0;

    private static final Function<MinutePrecision, BiPredicate<TemporalAccessor, ClockProvider>> PREDICATE_EXTRACTOR = annotation -> PREDICATE;

    private static final BiPredicate<Instant, ClockProvider> INSTANT_PREDICATE = (value, provider)
            -> PREDICATE.test(value.atZone(ZoneOffset.UTC), provider);

    private static final Function<MinutePrecision, BiPredicate<Instant, ClockProvider>> INSTANT_PREDICATE_EXTRACTOR = annotation -> INSTANT_PREDICATE;

    private MinutePrecisionValidator() {
    }

    /**
     * A constraint validator for {@link MinutePrecision} for {@link Date}.
     *
     * @author Rob Spoor
     */
    public static class ForDate extends DateValidator<MinutePrecision> {

        /**
         * Creates a new validator.
         */
        public ForDate() {
            super(new ForInstant());
        }
    }

    /**
     * A constraint validator for {@link MinutePrecision} for {@link Calendar}.
     *
     * @author Rob Spoor
     */
    public static class ForCalendar extends CalendarValidator<MinutePrecision> {

        /**
         * Creates a new validator.
         */
        public ForCalendar() {
            super(new ForTemporalAccessor());
        }
    }

    /**
     * A constraint validator for {@link MinutePrecision} for {@link Instant}.
     *
     * @author Rob Spoor
     */
    public static class ForInstant extends ValueValidator<MinutePrecision, Instant> {

        /**
         * Creates a new validator.
         */
        public ForInstant() {
            super(INSTANT_PREDICATE_EXTRACTOR);
        }
    }

    /**
     * A constraint validator for {@link MinutePrecision} for {@link TemporalAccessor}.
     *
     * @author Rob Spoor
     */
    public static class ForTemporalAccessor extends ValueValidator<MinutePrecision, TemporalAccessor> {

        /**
         * Creates a new validator.
         */
        public ForTemporalAccessor() {
            super(PREDICATE_EXTRACTOR);
        }
    }
}
