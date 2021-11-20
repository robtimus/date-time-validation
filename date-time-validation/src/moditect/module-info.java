module com.github.robtimus.validation.datetime {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.datetime;
    exports com.github.robtimus.validation.datetime.validators;
}
