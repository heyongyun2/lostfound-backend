package org.example.springboot.service;

import org.example.springboot.common.Result;
import org.example.springboot.entity.Item;
import org.example.springboot.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service

public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public int addItem(Item item) {
        return itemMapper.insertItem(item);
    }

    public List<Item> getItemList(String category, String location, String sortDate,String itemName,String intent) {

        // 你原来已有的controller代码
        List<Item> itemList = itemMapper.selectItemList(category,location,sortDate,itemName,intent);

// 直接在这里加打印，就用你已经拿到的 itemList，不要再新建接口
        if(itemList != null && itemList.size()>0){
            Item tempItem = itemList.get(0);
            System.out.println("【controller直接打印】imageUrl = " + tempItem.getImageUrl());
        }
        return itemMapper.selectItemList(category, location, sortDate,itemName,intent);


    }

    // 保存图片，复用你之前的上传工具即可；这里给你备用实现
    public String saveImage(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String savePath = "D:/lost_found_images/" + fileName;
        File dest = new File(savePath);
        dest.getParentFile().mkdirs();
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/images/" + fileName;
    }
    // 1. 根据状态查询物品列表（给后台审核用，例如查 "pending"）
    public List<Item> listByStatus(String status) {
        // 如果你的 ItemMapper 里有通用的查询或根据 status 查询的方法
        // 这里我们可以直接调用 itemMapper 的方法，或者让 itemMapper 提供一个 selectByStatus
        return itemMapper.selectByStatus(status);
    }

    // 2. 更新物品状态（审批通过 approved 或拒绝 rejected）
    public boolean updateItemStatus(Integer itemId, String status) {
        // 这里需要你在 ItemMapper 里面写一个对应的 SQL 更新方法，或者直接调用更新
        int rows = itemMapper.updateStatus(itemId, status);
        return rows > 0;
    }
}
