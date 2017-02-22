package cukesman;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static cukesman.EnvPropertyReader.readCukesmanPassword;
import static cukesman.EnvPropertyReader.readCukesmanUser;

public class FeatureDownloader {

    private final OkHttpClient client;

    public FeatureDownloader() {
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.authenticator((route, response) -> {
            String credential = Credentials.basic(readCukesmanUser(), readCukesmanPassword());
            return response.request().newBuilder().header("Authorization", credential).build();
        });
        client = clientBuilder.build();
    }

    public String loadFromUrl(final String url) {
        try {
            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            final Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                final String message = String.format("Could not download feature from url %s (message: %s).", url, response.message());
                throw new IllegalStateException(message);
            }

            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
