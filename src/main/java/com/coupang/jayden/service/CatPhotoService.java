package com.coupang.jayden.service;

import com.coupang.jayden.model.CatImageVO;
import com.coupang.jayden.model.ImageUrlDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatPhotoService {

    private final HttpClientService httpClientService;
    private final DatabaseService databaseService;
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public List<String> getImages(Long photoNum) {
        HttpResponse<String> response = httpClientService.getCatImagesApiResponse(photoNum);
        if (response == null || response.statusCode() != 200) {
            return Collections.emptyList();
        }
        return Try.of(() -> objectMapper.readValue(response.body(), new TypeReference<List<ImageUrlDto>>() {
        }))
                .onFailure(e -> log.error("failed to read by objectMapper", e))
                .recover(e -> Collections.emptyList())
                .map(list -> list.stream().filter(Objects::nonNull).map(ImageUrlDto::getUrl).collect(Collectors.toList()))
                .get();
    }

    public List<CatImageVO> getAllImagesFromDb() {
        return databaseService.getAllImages();
    }

    public void saveAllImages(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) return;
        databaseService.save(urls);
    }

    public void deleteImagesByIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) return;
        databaseService.deleteByIds(idList);
    }
}
