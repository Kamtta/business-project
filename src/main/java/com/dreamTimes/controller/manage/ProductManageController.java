package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.Product;
import com.dreamTimes.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    IProductService productService;
    /**
     * 新增OR更新产品
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do")
    public ServerResponse save( Product product){
            return productService.save(product);
    }

    /**
     * 产品上下架
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status/{productId}/{status}")
    public ServerResponse set_sale_status(@PathVariable("productId") Integer productId,
                                          @PathVariable("status") Integer status){
            return productService.set_sale_status(productId,status);
    }


    /**
     * 查看商品的详情
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail/{productId}")
    public ServerResponse detail(@PathVariable("productId") Integer productId){
            return productService.detailManage(productId);
    }


    @RequestMapping(value = "list.do")
    public ServerResponse list(@RequestParam(value = "pageNum" ,required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        return productService.list(pageNum,pageSize);
    }


    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(value = "productId" ,required = false)Integer productId,
                               @RequestParam(value = "productName",required = false)String productName,
                               @RequestParam(value = "pageNum" ,required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
            return productService.search(productId,productName,pageNum,pageSize);
    }



}
