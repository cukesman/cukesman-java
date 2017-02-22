package cukesman;

import java.util.Objects;

public class EnvPropertyReader {

    public static boolean hasCukesmanOneOffFeatureId() {
        try {
            return readCukesmanOneOffFeatureId() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static String readCukesmanOneOffFeatureURL() {
        return String.format(
                "%s/executions/oneoff/%s.feature",
                EnvPropertyReader.readCukesmanUrl(),
                EnvPropertyReader.readCukesmanOneOffFeatureId()
        );
    }

    public static String readCukesmanOneOffFeatureId() {
        return readEnvVarOrProperty("CUKESMAN_ONEOFF_FEATURE_ID", "cukesmanOneOffFeatureId");
    }

    public static String readCukesmanPassword() {
        return readEnvVarOrProperty("CUKESMAN_PASSWORD", "cukesmanPassword");
    }

    public static String readCukesmanUser() {
        return readEnvVarOrProperty("CUKESMAN_USER", "cukesmanUser");
    }

    public static String readCukesmanUrl() {
        return readEnvVarOrProperty("CUKESMAN_URL", "cukesmanUrl");
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
