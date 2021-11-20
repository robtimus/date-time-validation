module com.github.robtimus.validation.month {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.month;
    exports com.github.robtimus.validation.month.validators;
}
