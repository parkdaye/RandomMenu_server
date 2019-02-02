package com.boostcamp.randommenu.dto;

import lombok.Data;

@Data
public class NearMenu {
    private int menuIdx;

    private String name;

    private String photoUrl;

    private int price;

    private String description;
}
