module com.github.robtimus.validation.minute {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.minute;
    exports com.github.robtimus.validation.minute.validators;
}
