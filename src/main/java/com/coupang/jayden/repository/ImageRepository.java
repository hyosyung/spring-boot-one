package com.coupang.jayden.repository;

import com.coupang.jayden.model.CatImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<CatImage, Long> {
}
