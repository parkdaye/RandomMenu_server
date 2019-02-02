package com.boostcamp.randommenu.service.impl;

import com.boostcamp.randommenu.model.Owner;
import com.boostcamp.randommenu.mapper.OwnerMapper;
import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.service.OwnerService;
import com.boostcamp.randommenu.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Random;

@Slf4j
@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl (final OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    @Override
    public DefaultRes login(final String ownerId) {
        int storeCount = ownerMapper.checkStoreExisted(ownerId);
        if(storeCount == 0)
            return DefaultRes.res(HttpStatus.NO_CONTENT.value(), Message.NOT_FOUND_ID);

        return DefaultRes.res(HttpStatus.OK.value(), Message.LOGIN_SUCCESS);
    }

    @Transactional
    @Override
    public DefaultRes createOwner(final String ownerId) {
        try {
            Owner owner = new Owner();
            //Integer storeIdx;
            ownerMapper.createStore(ownerId, owner);

            int storeIdx = owner.getStoreIdx();
            for(int i = 1; i <= 3; i++)
                ownerMapper.createMenu(storeIdx, i);

            String accesskey = makeAccessKey();
            while (true) {
                //이전 키와 중복되지 않는 키만 새로 등록
                int keyCount = ownerMapper.countKey(accesskey);
                if (keyCount < 1) {
                    ownerMapper.createAccessKey(accesskey);
                    break;
                }
            }

            return DefaultRes.res(HttpStatus.CREATED.value(), Message.CREATE_OWNER_INFO_SUCCESS);

        } catch(Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(DefaultRes.DB_ERROR, Message.DB_ERROR);
        }
    }

    @Override
    public DefaultRes matchKey(final String accessKey, final String ownerId) {
        Boolean isUsed = false;

        int keyCount = ownerMapper.countKey(accessKey);
        if(ownerMapper.checkKeyUsed(accessKey) == 1)
            isUsed = true;

        //이미 사용된 키이거나 아예 존재하지 않을 경우
        if(isUsed || keyCount < 1) {
            return DefaultRes.res(HttpStatus.NO_CONTENT.value(), Message.MATCH_KEY_FAIL);
        } else {
            ownerMapper.setkeyUsed(1, accessKey);
            return createOwner(ownerId);
        }
    }

    //6자리 난수 생성
    @Override
    public String makeAccessKey() {
        final int len = 6;
        Random rand = new Random();
        StringBuilder accessKey;
        accessKey = new StringBuilder();

        for(int i=0; i<len; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            accessKey.append(ran);
        }
        return accessKey.toString();
    }

}
