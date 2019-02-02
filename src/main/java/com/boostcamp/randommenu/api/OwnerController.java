package com.boostcamp.randommenu.api;

import com.boostcamp.randommenu.model.Access;
import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.model.Login;
import com.boostcamp.randommenu.service.OwnerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @ApiOperation(value = "로그인")
    //@ApiImplicitParam(name = "ownerId", value = "업주 아이디", required = true, dataType = "string", paramType = "query")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 204, message = "일치하는 아이디가 없음"),
            @ApiResponse(code = 400, message = "잘못된 쿼리 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류"),
            @ApiResponse(code = 600, message = "DB 에러")})
    @PostMapping("login")
    public ResponseEntity<DefaultRes> login(@RequestBody @Valid final Login login) throws Exception {
        DefaultRes loginRes = ownerService.login(login.getOwnerId());

        if(loginRes == null)
            throw new Exception();

        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }

    @ApiOperation(value = "인증하기 및 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "인증 및 회원가입 성공"),
            @ApiResponse(code = 204, message = "이미 사용된 키이거나 존재하지 않는 키입니다."),
            @ApiResponse(code = 400, message = "잘못된 쿼리 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류"),
            @ApiResponse(code = 600, message = "DB 에러")})
    @PostMapping("access")
    public ResponseEntity<DefaultRes> matchKey(@RequestBody @Valid final Access access) throws Exception {
        DefaultRes accessRes = ownerService.matchKey(access.getAccessKey(), access.getOwnerId());

        if(accessRes == null)
            throw new Exception();

        return new ResponseEntity<>(accessRes, HttpStatus.OK);
    }

}
