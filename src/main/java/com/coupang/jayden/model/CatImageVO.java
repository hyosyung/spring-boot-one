package com.coupang.jayden.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatImageVO {
    private Long id;
    private String url;

    public static CatImageVO of(CatImage image){
        CatImageVO vo = new CatImageVO();
        vo.setId(image.getId());
        vo.setUrl(image.getUrl());
        return vo;
    }
}
