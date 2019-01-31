package com.boostcamp.randommenu.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class MenuInfo {
    @NotNull
    private String name;

    @NotNull
    private MultipartFile photo;

    @NotNull
    private int price;

    @NotNull
    private String description;

    @NotNull
    private int category;

    @NotNull
    private int sequence;
}
