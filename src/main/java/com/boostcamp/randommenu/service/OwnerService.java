package com.boostcamp.randommenu.service;


import com.boostcamp.randommenu.dto.DefaultRes;

public interface OwnerService  {
    DefaultRes login(final String ownerId);

    DefaultRes createOwner(final String ownerId);

    String makeAccessKey();

    DefaultRes matchKey(final String accessKey, final String ownerId);
}
