package com.boostcamp.randommenu.api;

import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.dto.Point;
import com.boostcamp.randommenu.dto.Store;
import com.boostcamp.randommenu.model.StoreInfo;
import com.boostcamp.randommenu.service.StoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(final StoreService storeService) {
        this.storeService = storeService;
    }

    @ApiOperation(value = "상점정보 및 메뉴정보 편집 후 저장")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "상점정보 및 메뉴정보 저장 성공"),
            @ApiResponse(code = 400, message = "잘못된 쿼리로 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류"),
            @ApiResponse(code = 600, message = "DB 에러")})
    @PutMapping("")
    public ResponseEntity<DefaultRes> saveStoreInfo(StoreInfo storeInfo) throws Exception {
        DefaultRes saveStoreRes = storeService.saveStoreInfo(storeInfo);

        if(saveStoreRes == null)
            throw new Exception();

        return new ResponseEntity<>(saveStoreRes, HttpStatus.OK);
    }

    @ApiOperation(value = "상점정보 및 메뉴정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "상점정보 및 메뉴정보 가져오기 성공", response = Store.class),
            @ApiResponse(code = 400, message = "잘못된 쿼리로 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류"),
            @ApiResponse(code = 600, message = "DB 에러")})
    @GetMapping("/{storeIdx}")
    public ResponseEntity<DefaultRes> getStoreInfo(@PathVariable(value = "storeIdx") final int storeIdx) throws Exception {
        DefaultRes getStoreRes = storeService.getStoreInfo(storeIdx);

        if(getStoreRes == null)
            throw new Exception();

        return new ResponseEntity<>(getStoreRes, HttpStatus.OK);
    }

}
