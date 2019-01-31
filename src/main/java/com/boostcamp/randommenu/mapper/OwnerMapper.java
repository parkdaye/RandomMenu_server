package com.boostcamp.randommenu.mapper;


import com.boostcamp.randommenu.model.Owner;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OwnerMapper {
    @Insert("INSERT INTO STORES(owner_id) VALUES(#{ownerId})")
    @Options(useGeneratedKeys = true, keyProperty="owner.storeIdx")
    int createStore(@Param("ownerId") final String ownerId, @Param("owner") final Owner owner);

    @Insert("INSERT INTO ACCESSKEY(access_key) VALUES(#{accessKey})")
    void createAccessKey(final String accessKey);

    @Select("SELECT count(*) FROM STORES WHERE STORES.owner_id = #{ownerId}")
    int checkStoreExisted(final String ownerId);

    @Select("SELECT count(*) FROM ACCESSKEY WHERE ACCESSKEY.access_key = #{accessKey}")
    int countKey(final String accessKey);

    @Select("SELECT ACCESSKEY.use FROM ACCESSKEY WHERE ACCESSKEY.access_key = #{accessKey}")
    int checkKeyUsed(final String accessKey);

    @Select("UPDATE ACCESSKEY SET ACCESSKEY.use = #{isUsed} WHERE ACCESSKEY.access_key = #{accessKey}")
    int setkeyUsed(@Param("isUsed") final int isUsed, @Param("accessKey") final String accessKey);

    @Insert("INSERT INTO MENU(store_idx, sequence) VALUES(#{storeIdx}, #{sequence})")
    void createMenu(@Param("storeIdx") final int storeIdx, @Param("sequence") final int sequence);
}
