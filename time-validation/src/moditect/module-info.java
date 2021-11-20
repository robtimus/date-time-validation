module com.github.robtimus.validation.time {
    requires com.github.robtimus.validation.datetime;
    requires transitive java.validation;

    exports com.github.robtimus.validation.time;
    exports com.github.robtimus.validation.time.validators;
}
