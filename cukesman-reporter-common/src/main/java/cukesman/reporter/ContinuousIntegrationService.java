package cukesman.reporter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

public class ContinuousIntegrationService {

    private static final List<String> BUILD_NUMBER_ENV_VARS = asList(
            "BUILD_NUMBER",
            "CIRCLE_BUILD_NUM",
            "TRAVIS_JOB_NUMBER",
            "bamboo.buildNumber",
            "CI_BUILD_NUMBER"
    );

    public static boolean isContinousIntegrationRun() {
        return readBuildNumber().isPresent();
    }

    public static Optional<String> readBuildNumber() {
        final Optional<Map.Entry<String, String>> buildNumberEntry = System.getenv().entrySet().stream()
                .filter(e -> BUILD_NUMBER_ENV_VARS.contains(e.getKey()))
                .findFirst();

        if (buildNumberEntry.isPresent()) {
            return Optional.of(buildNumberEntry.get().getValue());
        } else {
            return Optional.empty();
        }
    }

}
