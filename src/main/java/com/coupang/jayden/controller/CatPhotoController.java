package com.coupang.jayden.controller;

import com.coupang.jayden.service.CatPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CatPhotoController {

    private final CatPhotoService catPhotoService;

    @GetMapping("/get/cat-photos")
    public String getCatPhotos(@RequestParam(required = false, defaultValue = "0") Long num, Model model) {
        model.addAttribute("catTitle", "Cat Photos");
        model.addAttribute("images", catPhotoService.getImages(num));
        return "cat.html";
    }

    @GetMapping("/get/favorites")
    public String getFavorites(Model model) {
        model.addAttribute("catTitle", "Collection - Favorites");
        model.addAttribute("favorites", catPhotoService.getAllImagesFromDb());
        return "cat.html";
    }

    @PostMapping("/post/cat-photos")
    public String postCatPhotos(@RequestParam(required = false) List<String> urls, Model model) {
        urls = Optional.ofNullable(urls).orElse(Collections.EMPTY_LIST);
        catPhotoService.saveAllImages(urls);
        model.addAttribute("catTitle", "Cat Photos");
        return "cat.html";
    }

    @PostMapping("/delete/cat-photos")
    public String deleteCatPhotos(@RequestParam(required = false) List<Long> ids, Model model) {
        ids = Optional.ofNullable(ids).orElse(Collections.EMPTY_LIST);
        catPhotoService.deleteImagesByIds(ids);
        model.addAttribute("catTitle", "Collection - Favorites");
        model.addAttribute("favorites", catPhotoService.getAllImagesFromDb());
        return "cat.html";
    }
}
