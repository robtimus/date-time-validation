# date-time-validation

Validation constraints for date/time objects.

## Modules

Because this repository contains a lot of constraints, and each applies to several date/time object types, it is split into several modules. This allows you to only use those modules you need without getting a single, large dependency.

The following sections each describe a module. Most of these modules validate only a part of the value. Because the exact date and time depends on the time zone, each annotation in these modules has an optional `zoneId` parameter. This can have the following values:

* `system` (default): the system time zone, as returned by `ZoneId.systemDefault()`, should be used.
* `provided`: the time zone or offset information from the actual value should be used.
* A value that is valid according to `ZoneId.of` for an explicit time zone.

### date-time-validation

Validation constraints for date/time objects that validate entire values. These validate the following, where `object` is the object to be validated, `moment` is the value specified in the constraint, and `duration` is a [ISO 8601 duration](https://en.wikipedia.org/wiki/ISO_8601):

| Constraint | Meaning                     |
|------------|-----------------------------|
| After      | object > moment             |
| NotAfter   | object <= moment            |
| MinAfter   | object >= moment + duration |
| MaxAfter   | object <= moment + duration |
| Before     | object < moment             |
| NotBefore  | object >= moment            |
| MinBefore  | object <= moment - duration |
| MaxBefore  | object >= moment - duration |

The format of `moment` and `duration` depends on the type to validate:

* For classes from the `java.time` package, `moment` must be valid according to the class' `parse` method.
* For `Date`, `moment` must be valid according to `Instant.parse`.
* For `Calendar`, `moment` must be valid according to `ZonedDateTime.parse`.
* `duration` may not contain parts that are not present in the type. For instance, for `LocalDate` `duration` may not contain any time elements, for `LocalTime` it may not contain any date elements, for `YearMonth` it may only contain year and/or month elements, etc.

In addition, for all annotations the `moment` can be defined as literal value `now` to indicate the current date/time must be used.

These annotations apply to the following types:

| Type                | After | NotAfter | MinAfter | MaxAfter | Before | NotBefore | MinBefore | MaxBefore |
|---------------------|:-----:|:--------:|:--------:|:--------:|:------:|:---------:|:---------:|:---------:|
| Date                |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| Calendar            |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| DayOfWeek           |❌     |❌        |❌        |❌        |❌      |❌         |❌         |❌         |
| Instant             |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| LocalDate           |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| LocalDateTime       |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| LocalTime           |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| Month               |❌     |❌        |❌        |❌        |❌      |❌         |❌         |❌         |
| MonthDay<sup>1</sup>|✅     |✅        |❌        |❌        |✅      |✅         |❌         |❌         |
| OffsetDateTime      |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| OffsetTime          |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| Year                |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| YearMonth           |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |
| ZonedDateTime       |✅     |✅        |✅        |✅        |✅      |✅         |✅         |✅         |

<sup>1</sup>: `MinAfter`, `MaxAfter`, `MinBefore` and `MaxBefore` cannot be applied to [MonthDay](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/MonthDay.html) because "it is not possible to define whether February 29th is valid or not without external information". This makes it impossible to apply durations to `MonthDay` moments.

### date-validation

Validation constraints for date/time objects that validate only the date part. These work just like the constraints of the `date-time-validation` module, except they ignore any time part. The `moment` must be `now` or valid according to `LocalDate.parse`, and the `duration` may not contain any time elements.

These annotations apply to the following types:

| Type                     | DateAfter | DateNotAfter | DateMinAfter | DateMaxAfter | DateBefore | DateNotBefore | DateMinBefore | DateMaxBefore |
|--------------------------|:---------:|:------------:|:------------:|:------------:|:----------:|:-------------:|:-------------:|:-------------:|
| Date<sup>1</sup>         |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| Calendar                 |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| DayOfWeek                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Instant<sup>1</sup>      |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalDate<sup>2</sup>    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| LocalDateTime<sup>3</sup>|✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalTime                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Month                    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| MonthDay                 |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| OffsetDateTime           |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| OffsetTime               |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Year                     |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| YearMonth                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| ZonedDateTime            |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: can be validated with annotations from `date-time-validation`.\
<sup>3</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### time-validation

Validation constraints for date/time objects that validate only the time part. These work just like the constraints of `date-time-validation` module, except they ignore any date part. The `moment` must be `now` or valid according to `LocalTime.parse`, and the `duration` may not contain any date elements.

These annotations apply to the following types:

| Type                     | TimeAfter | TimeNotAfter | TimeMinAfter | TimeMaxAfter | TimeBefore | TimeNotBefore | TimeMinBefore | TimeMaxBefore |
|--------------------------|:---------:|:------------:|:------------:|:------------:|:----------:|:-------------:|:-------------:|:-------------:|
| Date<sup>1</sup>         |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| Calendar                 |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| DayOfWeek                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Instant<sup>1</sup>      |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalDate                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| LocalDateTime<sup>2</sup>|✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalTime<sup>3</sup>    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Month                    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| MonthDay                 |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| OffsetDateTime           |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| OffsetTime<sup>3</sup>   |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Year                     |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| YearMonth                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| ZonedDateTime            |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.\
<sup>3</sup>: can be validated with annotations from `date-time-validation`.

### year-month-validation

Validation constraints for date/time objects that validate only the year and month. These work just like the constraints of the `date-time-validation` module, except they ignore the day of the month and any time part. The `moment` must be `now` or valid according to `YearMonth.parse`, and the `duration` may only contain year and month elements.

These annotations apply to the following types:

| Type                     | YearMonthAfter | YearMonthNotAfter | YearMonthMinAfter | YearMonthMaxAfter | YearMonthBefore | YearMonthNotBefore | YearMonthMinBefore | YearMonthMaxBefore |
|--------------------------|:--------------:|:-----------------:|:-----------------:|:-----------------:|:---------------:|:------------------:|:------------------:|:------------------:|
| Date<sup>1</sup>         |✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |
| Calendar                 |✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |
| DayOfWeek                |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| Instant<sup>1</sup>      |✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |
| LocalDate<sup>2</sup>    |✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |
| LocalDateTime<sup>2</sup>|✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |
| LocalTime                |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| Month                    |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| MonthDay                 |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| OffsetDateTime           |✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |
| OffsetTime               |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| Year                     |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| YearMonth<sup>3</sup>    |❌              |❌                 |❌                 |❌                 |❌               |❌                  |❌                  |❌                  |
| ZonedDateTime            |✅              |✅                 |✅                 |✅                 |✅               |✅                  |✅                  |✅                  |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.\
<sup>3</sup>: can be validated with annotations from `date-time-validation`.

### year-validation

Validation constraints for date/time objects that validate only the year. These work just like the constraints of the `date-time-validation` module, except they ignore the month, the day of the month and any time part. The `moment` must be `now` or valid according to `Year.parse`, and the `duration` (renamed to `years`) must be specified as a number of years only. The latter prevents having to write `"P1Y"` instead of just `1`.

These annotations apply to the following types:

| Type                     | YearAfter | YearNotAfter | YearMinAfter | YearMaxAfter | YearBefore | YearNotBefore | YearMinBefore | YearMaxBefore |
|--------------------------|:---------:|:------------:|:------------:|:------------:|:----------:|:-------------:|:-------------:|:-------------:|
| Date<sup>1</sup>         |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| Calendar                 |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| DayOfWeek                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Instant<sup>1</sup>      |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalDate<sup>2</sup>    |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalDateTime<sup>2</sup>|✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalTime                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Month                    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| MonthDay                 |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| OffsetDateTime           |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| OffsetTime               |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Year<sup>3</sup>         |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| YearMonth<sup>2</sup>    |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| ZonedDateTime            |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.\
<sup>3</sup>: can be validated with annotations from `date-time-validation`.

### day-of-week-validation

Validation constraints for date/time objects that validate only the day of the week. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint     | Meaning                           |
|----------------|-----------------------------------|
| DayOfWeekIs    | object.dayOfWeek == value         |
| DayOfWeekIn    | value.contains(object.dayOfWeek)  |
| DayOfWeekNotIn | !value.contains(object.dayOfWeek) |

Note that days of the week are ordered from Monday until Sunday.

These annotations apply to the following types:

| Type                     | DayOfWeekIs | DayOfWeekIn | DayOfWeekNotIn |
|--------------------------|:-----------:|:-----------:|:--------------:|
| Date<sup>1</sup>         |✅           |✅           |✅              |
| Calendar                 |✅           |✅           |✅              |
| Instant<sup>1</sup>      |✅           |✅           |✅              |
| LocalDate<sup>2</sup>    |✅           |✅           |✅              |
| DayOfWeek<sup>2</sup>    |✅           |✅           |✅              |
| LocalDateTime<sup>2</sup>|✅           |✅           |✅              |
| LocalTime                |❌           |❌           |❌              |
| Month                    |❌           |❌           |❌              |
| MonthDay                 |❌           |❌           |❌              |
| OffsetDateTime           |✅           |✅           |✅              |
| OffsetTime               |❌           |❌           |❌              |
| Year                     |❌           |❌           |❌              |
| YearMonth                |❌           |❌           |❌              |
| ZonedDateTime            |✅           |✅           |✅              |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### month-validation

Validation constraints for date/time objects that validate only the month. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint | Meaning                       |
|------------|-------------------------------|
| MonthIs    | object.month == value         |
| MonthIn    | value.contains(object.month)  |
| MonthNotIn | !value.contains(object.month) |

These annotations apply to the following types:

| Type                     | MonthIs | MonthIn | MonthNotIn |
|--------------------------|:-------:|:-------:|:----------:|
| Date<sup>1</sup>         |✅       |✅       |✅          |
| Calendar                 |✅       |✅       |✅          |
| DayOfWeek                |❌       |❌       |❌          |
| Instant<sup>1</sup>      |✅       |✅       |✅          |
| LocalDate<sup>2</sup>    |✅       |✅       |✅          |
| LocalDateTime<sup>2</sup>|✅       |✅       |✅          |
| LocalTime                |❌       |❌       |❌          |
| Month<sup>2</sup>        |✅       |✅       |✅          |
| MonthDay<sup>2</sup>     |✅       |✅       |✅          |
| OffsetDateTime           |✅       |✅       |✅          |
| OffsetTime               |❌       |❌       |❌          |
| Year                     |❌       |❌       |❌          |
| YearMonth<sup>2</sup>    |✅       |✅       |✅          |
| ZonedDateTime            |✅       |✅       |✅          |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

#### year-month-validation vs month-validation

`year-month-validation` validates the combination of the year and the month. This allows it to be used for cases like credit card validation. `month-validation` on the other hand ignores the year.

### day-of-month-validation

Validation constraints for date/time objects that validate only the day of the month. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint       | Meaning                            |
|------------------|------------------------------------|
| DayOfMonthIs     | object.dayOfMonth == value         |
| DayOfMonthIn     | value.contains(object.dayOfMonth)  |
| DayOfMonthNotIn  | !value.contains(object.dayOfMonth) |

These annotations apply to the following types:

| Type                      | DayOfMonthIs | DayOfMonthIn | DayOfMonthNotIn |
|---------------------------|:------------:|:------------:|:---------------:|
| Date<sup>1</sup>          |✅            |✅            |✅               |
| Calendar                  |✅            |✅            |✅               |
| DayOfWeek                 |❌            |❌            |❌               |
| Instant<sup>1</sup>       |✅            |✅            |✅               |
| LocalDate<sup>2</sup>     |✅            |✅            |✅               |
| LocalDateTime<sup>2</sup> |✅            |✅            |✅               |
| LocalTime                 |❌            |❌            |❌               |
| Month                     |❌            |❌            |❌               |
| MonthDay<sup>2</sup>      |✅            |✅            |✅               |
| OffsetDateTime            |✅            |✅            |✅               |
| OffsetTime                |❌            |❌            |❌               |
| Year                      |❌            |❌            |❌               |
| YearMonth                 |❌            |❌            |❌               |
| ZonedDateTime             |✅            |✅            |✅               |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### hour-validation

Validation constraints for date/time objects that validate only the hour. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint | Meaning                      |
|------------|------------------------------|
| HourIs     | object.hour == value         |
| HourIn     | value.contains(object.hour)  |
| HourNotIn  | !value.contains(object.hour) |

These annotations apply to the following types:

| Type                      | HourIs | HourIn | HourNotIn |
|---------------------------|:------:|:------:|:---------:|
| Date<sup>1</sup>          |✅      |✅      |✅         |
| Calendar                  |✅      |✅      |✅         |
| DayOfWeek                 |❌      |❌      |❌         |
| Instant<sup>1</sup>       |✅      |✅      |✅         |
| LocalDate                 |❌      |❌      |❌         |
| LocalDateTime<sup>2</sup> |✅      |✅      |✅         |
| LocalTime<sup>2</sup>     |✅      |✅      |✅         |
| Month                     |❌      |❌      |❌         |
| MonthDay                  |❌      |❌      |❌         |
| OffsetDateTime            |✅      |✅      |✅         |
| OffsetTime                |✅      |✅      |✅         |
| Year                      |❌      |❌      |❌         |
| YearMonth                 |❌      |❌      |❌         |
| ZonedDateTime             |✅      |✅      |✅         |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### minute-validation

Validation constraints for date/time objects that validate only the minute. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint  | Meaning                        |
|-------------|--------------------------------|
| MinuteIs    | object.minute == value         |
| MinuteIn    | value.contains(object.minute)  |
| MinuteNotIn | !value.contains(object.minute) |

These annotations apply to the following types:

| Type                      | MinuteIs | MinuteIn | MinuteNotIn |
|---------------------------|:--------:|:--------:|:-----------:|
| Date<sup>1</sup>          |✅        |✅        |✅           |
| Calendar                  |✅        |✅        |✅           |
| DayOfWeek                 |❌        |❌        |❌           |
| Instant<sup>1</sup>       |✅        |✅        |✅           |
| LocalDate                 |❌        |❌        |❌           |
| LocalDateTime<sup>2</sup> |✅        |✅        |✅           |
| LocalTime<sup>2</sup>     |✅        |✅        |✅           |
| Month                     |❌        |❌        |❌           |
| MonthDay                  |❌        |❌        |❌           |
| OffsetDateTime            |✅        |✅        |✅           |
| OffsetTime                |✅        |✅        |✅           |
| Year                      |❌        |❌        |❌           |
| YearMonth                 |❌        |❌        |❌           |
| ZonedDateTime             |✅        |✅        |✅           |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

## Examples

To specify that a date of birth must be at least 18 years in the past:
```
@MinBefore(moment = "now", duration = "P18Y")
LocalDate dateOfBirth;
```

To specify that a date/time object like `ZonedDateTime` must be on a weekday between 9:00 and 18:00:
```
@DayOfWeekIn({ MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY })
// alternatively:
//@DayOfWeekNotAfter(FRIDAY)
//@DayOfWeekBefore(SATURDAY)
@TimeNotBefore(moment = "09:00:00")
@TimeNotAfter(moment = "18:00:00")
ZonedDateTime appointmentDateTime;
```

To specify that a credit card must not have expired:
```
@NotBefore(moment = "now")
YearMonth expiryDate;
```

To specify that a credit card must not expire within the next 6 months:
```
@MinAfter(duration = "P6M", moment = "now")
YearMonth expiryDate;
```

To specify that a date must be next month or later, where the day of the month is irrelevant:
```
@YearMonthMinAfter(duration = "P1M", moment = "now")
LocalDate date;
```

To specify that a date must be next year or later, where the actual date is irrelevant:
```
@YearMinAfter(years = 1, moment = "now")
LocalDate date;
```
