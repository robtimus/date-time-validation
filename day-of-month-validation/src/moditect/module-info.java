module com.github.robtimus.validation.dayofmonth {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.dayofmonth;
    exports com.github.robtimus.validation.dayofmonth.validators;
}
