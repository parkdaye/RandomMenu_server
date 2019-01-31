package com.boostcamp.randommenu.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StoreInfo {
    @NotNull
    @NotEmpty
    private String ownerId;

    @NotNull
    private String name;

    @NotNull
    private String time;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    private List<MenuInfo> menuInfoList;

    private int storeIdx;

}
