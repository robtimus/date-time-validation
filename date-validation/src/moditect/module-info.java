module com.github.robtimus.validation.datetime.date {
    requires com.github.robtimus.validation.datetime;
    requires transitive java.validation;

    exports com.github.robtimus.validation.datetime.date;
    exports com.github.robtimus.validation.datetime.date.validators;
}
