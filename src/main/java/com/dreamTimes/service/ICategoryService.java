package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;

public interface ICategoryService {

    /**
     * 增加类别节点
     * @Param category
     * @return
     */
    ServerResponse add_category(User user, Integer parentId, String categoryName);


    /**
     * 获取品类子节点（平级）
     * @param user
     * @param categoryId
     * @return
     */
    ServerResponse get_category(User user, Integer categoryId);


    /**
     * 修改品类的名称
     * @param user
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse set_category_name(User user,Integer categoryId,String categoryName);


    /**
     * 获取当前分类id及递归子节点categoryId
     * @param categoryId
     * @return
     */
    ServerResponse get_deep_category(Integer categoryId);
}
