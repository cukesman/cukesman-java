package cukesman;

import java.util.Objects;

public class EnvPropertyReader {

    public static String readCukesmanPassword() {
        return readEnvVarOrProperty("CUKESMAN_PASSWORD", "cukesmanPassword");
    }

    public static String readCukesmanUser() {
        return readEnvVarOrProperty("CUKESMAN_USER", "cukesmanUser");
    }

    public static String readEnvVarOrProperty(final String envVarName, final String propertyName) {
        String value = System.getenv(envVarName);
        if (value == null) {
            value = System.getProperty(propertyName);
        }
        return Objects.requireNonNull(
                value,
                String.format("Missing environment variable %s or System Property %s.", envVarName, propertyName)
        );
    }

}
