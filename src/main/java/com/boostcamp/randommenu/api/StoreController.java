package com.boostcamp.randommenu.api;

import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.model.StoreInfo;
import com.boostcamp.randommenu.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(final StoreService storeService) {
        this.storeService = storeService;
    }

    @PutMapping("")
    public ResponseEntity<DefaultRes> saveStoreInfo(@RequestBody @Valid StoreInfo storeInfo) throws Exception {
        DefaultRes saveStoreRes = storeService.saveStoreInfo(storeInfo);

        if(saveStoreRes == null)
            throw new Exception();

        return new ResponseEntity<>(saveStoreRes, HttpStatus.OK);
    }

}
