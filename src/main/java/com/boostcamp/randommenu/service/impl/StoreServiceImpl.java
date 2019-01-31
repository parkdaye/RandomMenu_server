package com.boostcamp.randommenu.service.impl;

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

import javax.validation.Valid;


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
            //업주 정보만 저장
            storeMapper.saveStoreInfo(storeInfo);

            int storeIdx = storeInfo.getStoreIdx();

            //사진이 있는 경우 메뉴 정보도 저장
            for(MenuInfo menuInfo : storeInfo.getMenuInfoList()) {
                if(menuInfo.getPhoto() != null)
                    saveMenuInfo(menuInfo, storeIdx);
            }

            return DefaultRes.res(HttpStatus.OK.value(), Message.SAVE_STORE_SUCCESS);
         } catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(DefaultRes.DB_ERROR, Message.DB_ERROR);
        }
    }

    @Transactional
    @Override
    public DefaultRes saveMenuInfo(@Valid final MenuInfo menuInfo, final int storeIdx) {
        try {
            String url = s3FileUploadService.upload(menuInfo.getPhoto());
            storeMapper.saveMenuInfo(menuInfo, storeIdx, url);

            return DefaultRes.res(HttpStatus.OK.value(), Message.SAVE_STORE_MENU_SUCCESS);
        } catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(DefaultRes.DB_ERROR, Message.DB_ERROR);
        }

    }
}
