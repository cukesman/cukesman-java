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

    public String loadFromUrl(final String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        final Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
