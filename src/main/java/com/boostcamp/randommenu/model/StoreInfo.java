package com.boostcamp.randommenu.model;

import lombok.Data;
import java.util.List;

@Data
public class StoreInfo {
    private String ownerId;

    private String name;

    private String time;

    private String description;

    private String address;

    private double latitude;

    private double longitude;

    private List<MenuInfo> menuInfoList;

    private int storeIdx;

}
