package com.boostcamp.randommenu.mapper;

import com.boostcamp.randommenu.dto.Point;
import com.boostcamp.randommenu.dto.NearMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MapMapper {
    @Select("SELECT m.category, s.longitude, s.latitude, " +
            "ROUND(ST_DISTANCE_SPHERE(POINT(#{longitude}, #{latitude}), POINT(s.longitude, s.latitude))) AS distance " +
            "FROM STORES s inner join MENU m ON (s.store_idx = m.store_idx)" +
            "WHERE abs(ST_DISTANCE_SPHERE(POINT(#{longitude}, #{latitude}), POINT(s.longitude, s.latitude))) <= 1000")
    List<Point> getNearPoints(@Param("latitude") final double latitude, @Param("longitude") final double longitude);

    @Select("SELECT m.menu_idx, m.name, m.photo_url, m.price, m.description, m.category " +
            "FROM STORES s inner join MENU m ON (s.store_idx = m.store_idx) " +
            "WHERE abs(ST_DISTANCE_SPHERE(POINT(#{longitude}, #{latitude}), POINT(s.longitude, s.latitude))) <= #{radius}")
    List<NearMenu> getNearMenu(@Param("latitude") final double latitude, @Param("longitude") final double longitude,
                               @Param("radius") final int radius);

    @Select("SELECT m.menu_idx, m.name, m.photo_url, m.price, m.description, m.category " +
            "FROM STORES s inner join MENU m ON (s.store_idx = m.store_idx) " +
            "WHERE abs(ST_DISTANCE_SPHERE(POINT(#{longitude}, #{latitude}), POINT(s.longitude, s.latitude))) <= #{radius} and m.category = #{category}")
    List<NearMenu> getNearMenuByCategory(@Param("latitude") final double latitude, @Param("longitude") final double longitude,
                               @Param("radius") final int radius, @Param("category") final int category);
}
