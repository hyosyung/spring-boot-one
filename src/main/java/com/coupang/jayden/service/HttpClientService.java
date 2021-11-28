package com.coupang.jayden.service;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
@Slf4j
public class HttpClientService {
    private final String CAT_API_HEADER_VALUE = "3fbbc183-8960-4f2b-b108-f7bbfbcec3bb";
    private final String CAT_API_HEADER_KEY = "x-api-key";
    private final String GET_IMAGES_URI = "https://api.thecatapi.com/v1/images/search?limit=";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public HttpResponse<String> getCatImagesApiResponse(Long photoNum) {
        return Try.of(() -> httpClient.send(getHttpRequestForCatImages(photoNum), ofString()))
                .onFailure(e -> log.error("Failed to get response from api", e))
                .getOrNull();
    }

    private HttpRequest getHttpRequestForCatImages(Long photoNum) {
        return HttpRequest.newBuilder()
                .uri(URI.create(GET_IMAGES_URI + photoNum))
                .timeout(Duration.ofMillis(1000))
                .header(CAT_API_HEADER_KEY, CAT_API_HEADER_VALUE)
                .GET()
                .build();
    }
}
