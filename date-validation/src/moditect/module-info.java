module com.github.robtimus.validation.date {
    requires com.github.robtimus.validation.datetime.base;
    requires transitive java.validation;

    exports com.github.robtimus.validation.date;
    exports com.github.robtimus.validation.date.validators;
}
