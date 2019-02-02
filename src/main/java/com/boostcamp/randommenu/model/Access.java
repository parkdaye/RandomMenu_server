package com.boostcamp.randommenu.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Access {
    @NotNull
    @NotEmpty
    private String accessKey;

    @NotNull
    @NotEmpty
    private String ownerId;
}
