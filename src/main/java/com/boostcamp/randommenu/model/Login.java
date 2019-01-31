package com.boostcamp.randommenu.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Login {
    @NotNull
    @NotEmpty
    private String ownerId;
}
