package com.coupang.jayden.repository;

import com.coupang.jayden.model.CatImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<CatImage, Long> {
    @Query("delete from CatImage where id not in (select min(id) from CatImage c group by url)")
    void deleteSameImages();
}
