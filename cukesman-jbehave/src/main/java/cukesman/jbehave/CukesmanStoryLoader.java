package cukesman.jbehave;

import cukesman.EnvPropertyReader;
import cukesman.FeatureDownloader;
import org.jbehave.core.io.ResourceLoader;
import org.jbehave.core.io.StoryLoader;

public class CukesmanStoryLoader implements ResourceLoader, StoryLoader {

    private final FeatureDownloader featureDownloader = new FeatureDownloader();

    @Override
    public String loadStoryAsText(final String url) {
        return featureDownloader.loadFromUrl(url);
    }

    @Override
    public String loadResourceAsText(final String url) {
        return featureDownloader.loadFromUrl(url);
    }

    public static String readCukesmanOneOffFeatureURL() {
        return EnvPropertyReader.readCukesmanOneOffFeatureURL();
    }

}
