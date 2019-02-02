package com.boostcamp.randommenu.service.impl;

import com.boostcamp.randommenu.dto.Store;
import com.boostcamp.randommenu.mapper.StoreMapper;
import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.model.MenuInfo;
import com.boostcamp.randommenu.model.StoreInfo;
import com.boostcamp.randommenu.service.S3FileUploadService;
import com.boostcamp.randommenu.service.StoreService;
import com.boostcamp.randommenu.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreMapper storeMapper;
    private final S3FileUploadService s3FileUploadService;

    public StoreServiceImpl(final StoreMapper storeMapper, final S3FileUploadService s3FileUploadService) {
        this.storeMapper = storeMapper;
        this.s3FileUploadService = s3FileUploadService;
    }

    @Transactional
    @Override
    public DefaultRes saveStoreInfo(final StoreInfo storeInfo) {
        try {
            int storeNum = storeMapper.checkStoreByOwnerId(storeInfo.getOwnerId());

            if(storeNum > 0) {
                //업주 정보만 저장
                storeMapper.saveStoreInfo(storeInfo);

                int storeIdx = storeInfo.getStoreIdx();

                if (storeInfo.getMenuInfoList() != null) {
                    //사진이 있는 경우 메뉴 정보도 저장
                    for (MenuInfo menuInfo : storeInfo.getMenuInfoList()) {
                        if (menuInfo.getPhoto() != null) {
                            String url = s3FileUploadService.upload(menuInfo.getPhoto());
                            storeMapper.saveMenuInfo(menuInfo, storeIdx, url);
                        }
                    }
                    return DefaultRes.res(HttpStatus.OK.value(), Message.SAVE_STORE_MENU_SUCCESS);
                }
                return DefaultRes.res(HttpStatus.OK.value(), Message.SAVE_STORE_SUCCESS);
            }
            else
                return DefaultRes.res(HttpStatus.NOT_FOUND.value(), Message.NOT_FOUND_STORE);

         } catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(DefaultRes.DB_ERROR, Message.DB_ERROR);
        }
    }

    @Override
    public DefaultRes getStoreInfo(final int storeIdx) {
        int storeNum = storeMapper.checkStoreByStoreIdx(storeIdx);

        if(storeNum > 0 ) {
            final Store store = storeMapper.getStoreInfo(storeIdx);
            store.setMenuList(storeMapper.getMenuListInfo(storeIdx));

            return DefaultRes.res(HttpStatus.OK.value(), Message.GET_STORE_MENULIST_SUCCESS, store);
        }
        else
            return DefaultRes.res(HttpStatus.NOT_FOUND.value(), Message.NOT_FOUND_STORE);
    }


}
