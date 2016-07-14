package cukesman.reporter;

import cukesman.reporter.model.Build;

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

    public static Build readBuild() {
        final Build build = new Build();
        build.setBuildNumber(ContinuousIntegrationService.readBuildNumber());
        return build;
    }

    public static boolean isContinousIntegrationRun() {
        return readBuildNumber() != null;
    }

    public static String readBuildNumber() {
        final Optional<Map.Entry<String, String>> buildNumberEntry = System.getenv().entrySet().stream()
                .filter(e -> BUILD_NUMBER_ENV_VARS.contains(e.getKey()))
                .findFirst();
        return buildNumberEntry.orElse(null).getValue();
    }

}
