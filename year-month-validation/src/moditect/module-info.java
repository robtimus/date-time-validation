module com.github.robtimus.validation.yearmonth {
    requires com.github.robtimus.validation.datetime;
    requires transitive java.validation;

    exports com.github.robtimus.validation.yearmonth;
    exports com.github.robtimus.validation.yearmonth.validators;
}
