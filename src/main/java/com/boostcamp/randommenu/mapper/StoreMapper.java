package com.boostcamp.randommenu.mapper;

import com.boostcamp.randommenu.model.MenuInfo;
import com.boostcamp.randommenu.model.StoreInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StoreMapper {
    @Update("UPDATE STORES SET latitude = #{storeInfo.latitude}, longitude = #{storeInfo.longitude}, name = #{storeInfo.name}, " +
            "time = #{storeInfo.time}, address = #{storeInfo.address}, description = #{storeInfo.description}, update_time = now() " +
            "WHERE owner_id = #{storeInfo.ownerId}")
    @Options(useGeneratedKeys = true, keyProperty="storeInfo.storeIdx")
    int saveStoreInfo(@Param("storeInfo") final StoreInfo storeInfo);


    @Update("UPDATE MENU SET name = #{menuInfo.name}, photo = #{photoUrl}, price = #{menuInfo.price}, " +
            "description = #{menuInfo.description}, category = #{menuInfo.category}" +
            "WHERE store_idx = #{storeIdx} and sequence = #{menuInfo.sequence}")
    void saveMenuInfo(@Param("menuInfo") final MenuInfo menuInfo, @Param("storeIdx") final int storeIdx,
                      @Param("photoUrl") final String photoUrl);
}
