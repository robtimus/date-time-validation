# date-time-validation

Validation constraints for date/time objects.

## Modules

Because this repository contains a lot of constraints, and each applies to several date/time object types, it is split into several modules. This allows you to only use those modules you need without getting a single, large dependency.

The following sections each describe a module.

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
* For classes from the `java.time` package, `moment` must be valid according to class' `parse` method.
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

<sup>1</sup>: `MinAfter`, `MaxAfter`, `MinBefore` and `MaxBefore` cannot be applied to `MonthDay` because (from [MonthDay](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/MonthDay.html)) "it is not possible to define whether February 29th is valid or not without external information". This makes it impossible to apply durations to `MonthDay` moments.

### date-validation

Validation constraints for date/time objects that validate only the date part. These work just like the constraints of the `date-time-validation` module, except it ignores any time part. The `moment` must be valid according to `LocalDate.parse`, and the `duration` may not contain any time elements.

Because the date depends on the time zone, each annotation has an optional `zoneId` parameter. This can have the following values:
* `system` (default): the system time zone, as returned by `ZoneId.systemDefault()`, should be used.
* `provided`: the time zone or offset information from the actual value should be used.
* A value that is valid according to `ZoneId.of` for an explicit time zone.

These annotations apply to the following types:

| Type                     | DateAfter | DateNotAfter | DateMinAfter | DateMaxAfter | DateBefore | DateNotBefore | DateMinBefore | DateMaxBefore |
|--------------------------|:---------:|:------------:|:------------:|:------------:|:----------:|:-------------:|:-------------:|:-------------:|
| Date<sup>1</sup>         |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| Calendar                 |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| DayOfWeek                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Instant<sup>1</sup>      |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalDate                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| LocalDateTime<sup>2</sup>|✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalTime                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Month                    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| MonthDay                 |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| OffsetDateTime           |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| OffsetTime               |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Year                     |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| YearMonth                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| ZonedDateTime            |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### time-validation

Validation constraints for date/time objects that validate only the time part. These work just like the constraints of `date-time-validation` module, except it ignores any date part. The `moment` must be valid according to `LocalTime.parse`, and the `duration` may not contain any date elements.

Because the time depends on the time zone, each annotation has an optional `zoneId` parameter. This can have the following values:
* `system` (default): the system time zone, as returned by `ZoneId.systemDefault()`, should be used.
* `provided`: the time zone or offset information from the actual value should be used.
* A value that is valid according to `ZoneId.of` for an explicit time zone.

These annotations apply to the following types:

| Type                     | TimeAfter | TimeNotAfter | TimeMinAfter | TimeMaxAfter | TimeBefore | TimeNotBefore | TimeMinBefore | TimeMaxBefore |
|--------------------------|:---------:|:------------:|:------------:|:------------:|:----------:|:-------------:|:-------------:|:-------------:|
| Date<sup>1</sup>         |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| Calendar                 |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| DayOfWeek                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Instant<sup>1</sup>      |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalDate                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| LocalDateTime<sup>2</sup>|✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| LocalTime                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Month                    |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| MonthDay                 |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| OffsetDateTime           |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |
| OffsetTime               |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| Year                     |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| YearMonth                |❌         |❌            |❌            |❌            |❌          |❌             |❌             |❌             |
| ZonedDateTime            |✅         |✅            |✅            |✅            |✅          |✅             |✅             |✅             |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### day-of-week-validation

Validation constraints for date/time objects that validate only the day of the week. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint         | Meaning                   |
|--------------------|---------------------------|
| DayOfWeekAfter     | object.dayOfWeek > value  |
| DayOfWeekNotAfter  | object.dayOfWeek <= value |
| DayOfWeekBefore    | object.dayOfWeek < value  |
| DayOfWeekNotBefore | object.dayOfWeek >= value |
| DayOfWeekIs        | object.dayOfWeek == value |
| DayOfWeekIn        | object.dayOfWeek in value |

Note that days of the week are ordered from Monday until Sunday.

Because the date, and therefore also the day of the week, depends on the time zone, each annotation has an optional `zoneId` parameter. This can have the following values:
* `system` (default): the system time zone, as returned by `ZoneId.systemDefault()`, should be used.
* `provided`: the time zone or offset information from the actual value should be used.
* A value that is valid according to `ZoneId.of` for an explicit time zone.

These annotations apply to the following types:

| Type                     | DayOfWeekAfter | DayOfWeekNotAfter | DayOfWeekBefore | DayOfWeekNotBefore | DayOfWeekIs | DayOfWeekIn |
|--------------------------|:--------------:|:-----------------:|:---------------:|:------------------:|:-----------:|:-----------:|
| Date<sup>1</sup>         |✅              |✅                 |✅               |✅                  |✅           |✅             |
| Calendar                 |✅              |✅                 |✅               |✅                  |✅           |✅             |
| DayOfWeek<sup>2</sup>    |✅              |✅                 |✅               |✅                  |✅           |✅             |
| Instant<sup>1</sup>      |✅              |✅                 |✅               |✅                  |✅           |✅             |
| LocalDate<sup>2</sup>    |✅              |✅                 |✅               |✅                  |✅           |✅             |
| LocalDateTime<sup>2</sup>|✅              |✅                 |✅               |✅                  |✅           |✅             |
| LocalTime                |❌              |❌                 |❌               |❌                  |❌           |❌             |
| Month                    |❌              |❌                 |❌               |❌                  |❌           |❌             |
| MonthDay                 |❌              |❌                 |❌               |❌                  |❌           |❌             |
| OffsetDateTime           |✅              |✅                 |✅               |✅                  |✅           |✅             |
| OffsetTime               |❌              |❌                 |❌               |❌                  |❌           |❌             |
| Year                     |❌              |❌                 |❌               |❌                  |❌           |❌             |
| YearMonth                |❌              |❌                 |❌               |❌                  |❌           |❌             |
| ZonedDateTime            |✅              |✅                 |✅               |✅                  |✅           |✅             |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

### month-validation

Validation constraints for date/time objects that validate only the month. These validate the following, where `object` is the object to be validated, and `value` is the value specified in the constraint:

| Constraint         | Meaning                   |
|--------------------|---------------------------|
| MonthAfter     | object.month > value  |
| MonthNotAfter  | object.month <= value |
| MonthBefore    | object.month < value  |
| MonthNotBefore | object.month >= value |
| MonthIs        | object.month == value |
| MonthIn        | object.month in value |

Because the date, and therefore also the month, depends on the time zone, each annotation has an optional `zoneId` parameter. This can have the following values:
* `system` (default): the system time zone, as returned by `ZoneId.systemDefault()`, should be used.
* `provided`: the time zone or offset information from the actual value should be used.
* A value that is valid according to `ZoneId.of` for an explicit time zone.

These annotations apply to the following types:

| Type                     | MonthAfter | MonthNotAfter | MonthBefore | MonthNotBefore | MonthIs | MonthIn |
|--------------------------|:----------:|:-------------:|:-----------:|:--------------:|:-------:|:-------:|
| Date<sup>1</sup>         |✅          |✅             |✅           |✅              |✅       |✅       |
| Calendar                 |✅          |✅             |✅           |✅              |✅       |✅       |
| DayOfWeek                |❌          |❌             |❌           |❌              |❌       |❌       |
| Instant<sup>1</sup>      |✅          |✅             |✅           |✅              |✅       |✅       |
| LocalDate<sup>2</sup>    |✅          |✅             |✅           |✅              |✅       |✅       |
| LocalDateTime<sup>2</sup>|✅          |✅             |✅           |✅              |✅       |✅       |
| LocalTime                |❌          |❌             |❌           |❌              |❌       |❌       |
| Month<sup>2</sup>        |✅          |✅             |✅           |✅              |✅       |✅       |
| MonthDay<sup>2</sup>     |✅          |✅             |✅           |✅              |✅       |✅       |
| OffsetDateTime           |✅          |✅             |✅           |✅              |✅       |✅       |
| OffsetTime               |❌          |❌             |❌           |❌              |❌       |❌       |
| Year                     |❌          |❌             |❌           |❌              |❌       |❌       |
| YearMonth<sup>2</sup>    |✅          |✅             |✅           |✅              |✅       |✅       |
| ZonedDateTime            |✅          |✅             |✅           |✅              |✅       |✅       |

<sup>1</sup>: because the type has no time zone information, the `zoneId` may not be defined as `provided`.\
<sup>2</sup>: because no time zone is applicanle for the type, the `zoneId` must be defined as `system`. Since this is the default, the `zoneId` parameter can simply be omitted.

## Examples

To specify that a date of birth, specified as a `LocalDate`, must be at least 18 years in the past:
```
@MinBefore(moment = "now", duration = "P18Y")
```

To specify that a date/time object like `ZonedDateTime` must be on a weekday between 9:00 and 18:00:
```
@DayOfWeekIn({ MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY })
// alternatively:
//@DayOfWeekNotAfter(FRIDAY)
//@DayOfWeekBefore(SATURDAY)
@TimeNotBefore(moment = "09:00:00")
@TimeNotAfter(moment = "18:00:00")
```
