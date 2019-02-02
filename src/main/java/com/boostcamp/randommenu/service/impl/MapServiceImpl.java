package com.boostcamp.randommenu.service.impl;


import com.boostcamp.randommenu.dto.DefaultRes;
import com.boostcamp.randommenu.dto.Point;
import com.boostcamp.randommenu.dto.NearMenu;
import com.boostcamp.randommenu.mapper.MapMapper;
import com.boostcamp.randommenu.service.MapService;
import com.boostcamp.randommenu.utils.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {
    private final MapMapper mapMapper;

    public MapServiceImpl (final MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    @Override
    public DefaultRes getNearPoints(final double lat, final double lon) {
        List<Point> pointList = mapMapper.getNearPoints(lat, lon);

        if(pointList.isEmpty())
            return DefaultRes.res(HttpStatus.NO_CONTENT.value(), Message.NOT_FOUND_NEAR_POINT);
        else
            return DefaultRes.res(HttpStatus.OK.value(), Message.GET_NEAR_POINTS_SUCCESS, pointList);

    }

    @Override
    public DefaultRes getNearMenu(final double lat, final double lon, final int radius, final int category) {
        List<NearMenu> menuList;

        if(category == 0)
            menuList = mapMapper.getNearMenu(lat, lon, radius);
        else
            menuList = mapMapper.getNearMenuByCategory(lat, lon, radius, category);

        if(menuList.isEmpty())
            return DefaultRes.res(HttpStatus.NO_CONTENT.value(), Message.NOT_FOUND_NEAR_POINT);
        else
            return DefaultRes.res(HttpStatus.OK.value(), Message.GET_NEAR_MENU_SUCCESS, menuList);
    }
}
