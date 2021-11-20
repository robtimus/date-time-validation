module com.github.robtimus.validation.year {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.year;
    exports com.github.robtimus.validation.year.validators;
}
