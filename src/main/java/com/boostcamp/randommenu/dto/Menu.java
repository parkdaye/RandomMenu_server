package com.boostcamp.randommenu.dto;

import lombok.Data;

@Data
public class Menu {
    private String name;

    private String photoUrl;

    private int price;

    private String description;

    private int category ;

    private int sequence;
}
