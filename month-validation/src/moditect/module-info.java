module com.github.robtimus.validation.month {
    requires com.github.robtimus.validation.datetime;
    requires transitive java.validation;

    exports com.github.robtimus.validation.month;
    exports com.github.robtimus.validation.month.validators;
}
