package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.springboot.entity.Item;

import java.util.List;

public interface ItemMapper {
    int insertItem(Item item);
    List<Item> selectItemList(@Param("category") String category,
                              @Param("location") String location,
                              @Param("sortDate") String sortDate,
                              @Param("itemName") String itemName,
                              @Param("intent") String intent);

    List<Item> selectByStatus(@Param("status") String status);


    int updateStatus(@Param("itemId") Integer itemId, @Param("status") String status);
    List<Item> selectItemsByUserId(@Param("userId") Long userId);
    int deleteById(@Param("itemId") Long itemId);
}
