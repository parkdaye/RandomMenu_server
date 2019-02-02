package com.boostcamp.randommenu.service;

import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.model.StoreInfo;

public interface StoreService {
    DefaultRes saveStoreInfo(final StoreInfo storeInfo);

    DefaultRes getStoreInfo(final int storeIdx);
}
