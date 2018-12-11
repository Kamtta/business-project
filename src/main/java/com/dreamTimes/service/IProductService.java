package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.Product;
import com.dreamTimes.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {

    /**
     * 新增or更新
     * @param user
     * @param product
     * @return
     */
    ServerResponse save(User user, Product product);

    /**
     *产品上下架
     * @param user
     * @param productId
     * @param status
     * @return
     */
    ServerResponse set_sale_status(User user,Integer productId,Integer status);


    /**
     * 查看商品的详情
     * @param user
     * @param productId
     * @return
     */
    ServerResponse detail(User user,Integer productId);


    /**
     * 分页查询商品信息
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse list(User user,Integer pageNum,Integer pageSize);

    /**
     * 产品搜索
     * @param user
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse search(User user,Integer productId,String productName,Integer pageNum,Integer pageSize);


    /**
     * 上传图片
     * @param file
     * @param path
     * @return
     */
    ServerResponse uploadPic(MultipartFile file,String path);


    /**
     *产品detail
     * @param productId
     * @return
     */
    ServerResponse detail(Integer productId);


    /**
     * 产品搜索及动态排序List
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    ServerResponse list( Integer categoryId,String keyword, Integer pageNum, Integer pageSize, String orderBy);

}
