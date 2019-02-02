package com.boostcamp.randommenu.dto;

import lombok.Data;

import java.util.List;

@Data
public class Store {
    private String name;

    private String time;

    private String description;

    private String address;

    private double latitude = 0;

    private double longitude = 0;

    private List<Menu> menuList;

}
