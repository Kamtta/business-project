package com.dreamTimes.controller.portal;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/portal/product/")
public class ProductController {

    @Autowired
    IProductService productService;
    /**
     * 产品detail
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(Integer productId){
       return productService.detail(productId);
    }


    /**
     * 产品搜索及动态排序List
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                               @RequestParam(value = "orderBy" ,required = false,defaultValue = "") String orderBy){
        return productService.list(categoryId,keyword,pageNum,pageSize,orderBy);

    }
}
