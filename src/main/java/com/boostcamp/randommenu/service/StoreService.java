package com.boostcamp.randommenu.service;

import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.model.MenuInfo;
import com.boostcamp.randommenu.model.StoreInfo;

public interface StoreService {
    DefaultRes saveStoreInfo(final StoreInfo storeInfo);

    DefaultRes saveMenuInfo(final MenuInfo menuInfo, final int storeIdx);
}
