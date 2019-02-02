package com.boostcamp.randommenu.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class MenuInfo {
    private String name;

    private MultipartFile photo;

    private int price;

    private String description;

    private int category;

    private int sequence;
}
