package com.coupang.jayden.service;

import com.coupang.jayden.model.CatImage;
import com.coupang.jayden.model.CatImageVO;
import com.coupang.jayden.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final ImageRepository imageRepository;

    public void deleteByIds(List<Long> idList) {
        imageRepository.deleteAllById(idList);
    }

    public void save(List<String> urls) {
        List<CatImage> images = urls.stream()
                .filter(Objects::nonNull)
                .map(CatImage::new)
                .collect(Collectors.toList());
        imageRepository.saveAll(images);
    }

    public List<CatImageVO> getAllImages() {
        return imageRepository.findAll()
                .stream()
                .map(CatImageVO::of)
                .collect(Collectors.toList());
    }
}
