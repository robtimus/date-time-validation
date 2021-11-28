module com.github.robtimus.validation.hour {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.hour;
    exports com.github.robtimus.validation.hour.validators;
}
