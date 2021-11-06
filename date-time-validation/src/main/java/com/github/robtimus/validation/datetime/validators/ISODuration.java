/*
 * ISODuration.java
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

package com.github.robtimus.validation.datetime.validators;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;

final class ISODuration implements TemporalAmount {

    private final Period period;
    private final Duration duration;

    private ISODuration(Period period, Duration duration) {
        this.period = period;
        this.duration = duration;
    }

    @Override
    public long get(TemporalUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TemporalUnit> getUnits() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        return period.addTo(duration.addTo(temporal));
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return period.subtractFrom(duration.subtractFrom(temporal));
    }

    static TemporalAmount parse(String text) {
        String upperCaseText = text.toUpperCase();

        int timeIndex = upperCaseText.indexOf('T');
        if (timeIndex == -1) {
            return Period.parse(text);
        }
        int periodIndex = upperCaseText.indexOf('P');
        if (timeIndex == periodIndex + 1) {
            // PT; no period
            return Duration.parse(text);
        }

        Period period = Period.parse(text.substring(0, timeIndex));
        Duration duration = Duration.parse(text.substring(0, periodIndex + 1) + text.substring(timeIndex));
        return new ISODuration(period, duration);
    }

    static Instant plus(Instant instant, TemporalAmount amount) {
        if (amount instanceof Duration) {
            return instant.plus(amount);
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        zonedDateTime = zonedDateTime.plus(amount);
        return zonedDateTime.toInstant();
    }

    static Instant minus(Instant instant, TemporalAmount amount) {
        if (amount instanceof Duration) {
            return instant.minus(amount);
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        zonedDateTime = zonedDateTime.minus(amount);
        return zonedDateTime.toInstant();
    }
}
