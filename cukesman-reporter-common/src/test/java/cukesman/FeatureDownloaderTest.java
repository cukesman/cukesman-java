package cukesman;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FeatureDownloaderTest {

    private MockWebServer server;

    @Before
    public void before() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @After
    public void after() throws IOException {
        server.close();
    }

    @Test
    public void loadFromUrl() throws Exception {
        // Arrange
        final String featureExpected = "hello, world!";
        server.enqueue(new MockResponse().setBody(featureExpected));
        final HttpUrl baseUrl = server.url("/MyTest.feature");

        // Act
        final String feature = new FeatureDownloader().loadFromUrl(baseUrl.toString());

        // Assert
        assertNotNull(feature);
        assertEquals(featureExpected, feature);
    }

}
