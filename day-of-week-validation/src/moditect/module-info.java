module com.github.robtimus.validation.dayofweek {
    requires com.github.robtimus.validation.datetime;
    requires transitive java.validation;

    exports com.github.robtimus.validation.dayofweek;
    exports com.github.robtimus.validation.dayofweek.validators;
}
