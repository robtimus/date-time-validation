/*
 * DateTimeValidator.java
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

import java.lang.annotation.Annotation;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.function.BiPredicate;
import javax.validation.ConstraintValidator;
import com.github.robtimus.validation.time.DateAfter;
import com.github.robtimus.validation.time.DateBefore;
import com.github.robtimus.validation.time.DateMaxAfter;
import com.github.robtimus.validation.time.DateMaxBefore;
import com.github.robtimus.validation.time.DateMinAfter;
import com.github.robtimus.validation.time.DateMinBefore;
import com.github.robtimus.validation.time.DateNotAfter;
import com.github.robtimus.validation.time.DateNotBefore;
import com.github.robtimus.validation.time.TimeAfter;
import com.github.robtimus.validation.time.TimeBefore;
import com.github.robtimus.validation.time.TimeMaxAfter;
import com.github.robtimus.validation.time.TimeMaxBefore;
import com.github.robtimus.validation.time.TimeMinAfter;
import com.github.robtimus.validation.time.TimeMinBefore;
import com.github.robtimus.validation.time.TimeNotAfter;
import com.github.robtimus.validation.time.TimeNotBefore;

/**
 * The base for all date/time object validators.
 *
 * @author Rob Spoor
 * @param <A> The constraint annotation type.
 * @param <T> The type to validate.
 */
public abstract class DateTimeValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    /** A string representing the current date/time. */
    public static final String NOW = "now"; //$NON-NLS-1$

    /**
     * A string representing the system zone id.
     *
     * @see DateAfter#zoneId()
     * @see DateNotAfter#zoneId()
     * @see DateBefore#zoneId()
     * @see DateNotBefore#zoneId()
     * @see DateMinAfter#zoneId()
     * @see DateMaxAfter#zoneId()
     * @see DateMinBefore#zoneId()
     * @see DateMaxBefore#zoneId()
     * @see TimeAfter#zoneId()
     * @see TimeNotAfter#zoneId()
     * @see TimeBefore#zoneId()
     * @see TimeNotBefore#zoneId()
     * @see TimeMinAfter#zoneId()
     * @see TimeMaxAfter#zoneId()
     * @see TimeMinBefore#zoneId()
     * @see TimeMaxBefore#zoneId()
     */
    public static final String SYSTEM_ZONE_ID = "system"; //$NON-NLS-1$

    /**
     * A string representing the provided zone id.
     *
     * @see DateAfter#zoneId()
     * @see DateNotAfter#zoneId()
     * @see DateBefore#zoneId()
     * @see DateNotBefore#zoneId()
     * @see DateMinAfter#zoneId()
     * @see DateMaxAfter#zoneId()
     * @see DateMinBefore#zoneId()
     * @see DateMaxBefore#zoneId()
     * @see TimeAfter#zoneId()
     * @see TimeNotAfter#zoneId()
     * @see TimeBefore#zoneId()
     * @see TimeNotBefore#zoneId()
     * @see TimeMinAfter#zoneId()
     * @see TimeMaxAfter#zoneId()
     * @see TimeMinBefore#zoneId()
     * @see TimeMaxBefore#zoneId()
     */
    public static final String PROVIDED_ZONE_ID = "provided"; //$NON-NLS-1$

    /**
     * Creates a new validator.
     */
    protected DateTimeValidator() {
        // no fields
    }

    static ZoneId toZoneId(String zoneIdText) {
        switch (zoneIdText) {
        case SYSTEM_ZONE_ID:
            return ZoneId.systemDefault();
        case PROVIDED_ZONE_ID:
            return null;
        default:
            return ZoneId.of(zoneIdText);
        }
    }

    static LocalDate toLocalDate(Instant instant, ZoneId zoneId) {
        return toZonedDateTime(instant, zoneId).toLocalDate();
    }

    static LocalDate toLocalDate(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? offsetDateTime.toLocalDate() : offsetDateTime.atZoneSameInstant(zoneId).toLocalDate();
    }

    static LocalDate toLocalDate(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? zonedDateTime.toLocalDate() : zonedDateTime.withZoneSameInstant(zoneId).toLocalDate();
    }

    static LocalTime toLocalTime(Instant instant, ZoneId zoneId) {
        return toZonedDateTime(instant, zoneId).toLocalTime();
    }

    static LocalTime toLocalTime(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? offsetDateTime.toLocalTime() : offsetDateTime.atZoneSameInstant(zoneId).toLocalTime();
    }

    static LocalTime toLocalTime(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? zonedDateTime.toLocalTime() : zonedDateTime.withZoneSameInstant(zoneId).toLocalTime();
    }

    static Month toMonth(Instant instant, ZoneId zoneId) {
        return toZonedDateTime(instant, zoneId).getMonth();
    }

    static Month toMonth(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? offsetDateTime.getMonth() : offsetDateTime.atZoneSameInstant(zoneId).getMonth();
    }

    static Month toMonth(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? zonedDateTime.getMonth() : zonedDateTime.withZoneSameInstant(zoneId).getMonth();
    }

    static DayOfWeek toDayOfWeek(Instant instant, ZoneId zoneId) {
        return toZonedDateTime(instant, zoneId).getDayOfWeek();
    }

    static DayOfWeek toDayOfWeek(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? offsetDateTime.getDayOfWeek() : offsetDateTime.atZoneSameInstant(zoneId).getDayOfWeek();
    }

    static DayOfWeek toDayOfWeek(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        // zoneId == null means use provided zoneId
        return zoneId == null ? zonedDateTime.getDayOfWeek() : zonedDateTime.withZoneSameInstant(zoneId).getDayOfWeek();
    }

    static ZonedDateTime toZonedDateTime(Instant instant, ZoneId zoneId) {
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }
        return instant.atZone(zoneId);
    }

    static ZonedDateTime toZonedDateTime(Calendar calendar, ZoneId zoneId) {
        // This is exactly what GregorianCalendar.toZonedDateTime() does
        if (zoneId == null) {
            zoneId = calendar.getTimeZone().toZoneId();
        }
        return ZonedDateTime.ofInstant(calendar.toInstant(), zoneId);
    }

    static <T> BiPredicate<T, T> not(BiPredicate<T, T> predicate) {
        return predicate.negate();
    }
}
