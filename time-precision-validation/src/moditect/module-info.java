module com.github.robtimus.validation.time.precision {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.time.precision;
    exports com.github.robtimus.validation.time.precision.validators;
}
