package com.boostcamp.randommenu.mapper;

import com.boostcamp.randommenu.dto.Menu;
import com.boostcamp.randommenu.dto.Store;
import com.boostcamp.randommenu.model.MenuInfo;
import com.boostcamp.randommenu.model.StoreInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreMapper {
    @Update("UPDATE STORES SET latitude = #{storeInfo.latitude}, longitude = #{storeInfo.longitude}, name = #{storeInfo.name}, " +
            "time = #{storeInfo.time}, address = #{storeInfo.address}, description = #{storeInfo.description}, update_time = now() " +
            "WHERE owner_id = #{storeInfo.ownerId}")
    @Options(useGeneratedKeys = true, keyProperty="storeInfo.storeIdx")
    int saveStoreInfo(@Param("storeInfo") final StoreInfo storeInfo);


    @Update("UPDATE MENU SET name = #{menuInfo.name}, photo_url = #{photoUrl}, price = #{menuInfo.price}, " +
            "description = #{menuInfo.description}, category = #{menuInfo.category} " +
            "WHERE store_idx = #{storeIdx} and sequence = #{menuInfo.sequence}")
    void saveMenuInfo(@Param("menuInfo") final MenuInfo menuInfo, @Param("storeIdx") final int storeIdx,
                      @Param("photoUrl") final String photoUrl);

    @Select("SELECT latitude, longitude, name, time, address, update_time, description FROM STORES " +
            "WHERE store_idx = #{storeIdx}")
    Store getStoreInfo(final int storeIdx);

    @Select("SELECT menu_idx, photo_url, name, price, description, category, sequence FROM MENU " +
            "WHERE store_idx = #{storeIdx}")
    List<Menu> getMenuListInfo(final int storeIdx);

    @Select("SELECT count(*) FROM STORES WHERE owner_id = #{ownerId}")
    int checkStoreByOwnerId(final String ownerId);

    @Select("SELECT count(*) FROM STORES WHERE store_idx = #{storeIdx}")
    int checkStoreByStoreIdx(final int storeIdx);
}
