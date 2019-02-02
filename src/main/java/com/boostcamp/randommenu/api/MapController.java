package com.boostcamp.randommenu.api;

import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.dto.NearMenu;
import com.boostcamp.randommenu.dto.Point;
import com.boostcamp.randommenu.service.MapService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("maps")
public class MapController {

    private final MapService mapService;

    public MapController(final MapService mapService) {
        this.mapService = mapService;
    }

    @ApiOperation(value = "내 좌표 주위 위치 검색")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "좌표 주위 위치 검색 성공", response = Point.class),
            @ApiResponse(code = 204, message = "주변에 매장이 존재하지 않습니다."),
            @ApiResponse(code = 400, message = "잘못된 쿼리로 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류"),
            @ApiResponse(code = 600, message = "DB 에러")})
    @GetMapping("")
    public ResponseEntity<DefaultRes> getNearPoints(@RequestParam(value = "latitude") final double latitude,
                                                    @RequestParam(value = "longitude") final double longitude) throws Exception {

        DefaultRes getNearPoints = mapService.getNearPoints(latitude, longitude);

        if(getNearPoints == null)
            throw new Exception();

        return new ResponseEntity<>(getNearPoints, HttpStatus.OK);
    }

    @ApiOperation(value = "내 좌표 주위 메뉴 검색")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "좌표 주위 메뉴정보 검색 성공", response = NearMenu.class),
            @ApiResponse(code = 204, message = "주변에 매장이 존재하지 않습니다."),
            @ApiResponse(code = 400, message = "잘못된 쿼리로 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류"),
            @ApiResponse(code = 600, message = "DB 에러")})
    @GetMapping("/menu")
    public ResponseEntity<DefaultRes> getNearMenu(@RequestParam(value = "latitude") final double latitude,
                                                    @RequestParam(value = "longitude") final double longitude,
                                                    @RequestParam(value = "radius", required = false, defaultValue = "1000") final int radius,
                                                    @RequestParam(value = "category", required = false, defaultValue = "0") final int category) throws Exception {
        DefaultRes getNearMenu = mapService.getNearMenu(latitude, longitude, radius, category);

        if(getNearMenu == null)
            throw new Exception();

        return new ResponseEntity<>(getNearMenu, HttpStatus.OK);
    }

}
