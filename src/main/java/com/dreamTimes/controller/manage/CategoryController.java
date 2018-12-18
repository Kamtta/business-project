package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/manage/category/")
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    /**
     * 增加类别节点
     * @param parentId
     * @param categoryName
     * @return
     */
    @RequestMapping("add_category.do")
    public ServerResponse add_category(@RequestParam(required = false,defaultValue = "0")Integer parentId, String categoryName){
            return iCategoryService.add_category(parentId,categoryName);
    }

    /**
     * 获取品类子节点（平级）
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category/{categoryId}")
    public ServerResponse get_category(@PathVariable("categoryId") Integer categoryId){
            return iCategoryService.get_category(categoryId);
    }


    /**
     * 修改品类名字
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name/{categoryId}/{categoryName}")
    public ServerResponse set_category_name(@PathVariable("categoryId") Integer categoryId,
                                            @PathVariable("categoryName") String categoryName){
        return iCategoryService.set_category_name(categoryId,categoryName);
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category/{categoryId}")
    public ServerResponse get_deep_category(@PathVariable("categoryId") Integer categoryId){
        return iCategoryService.get_deep_category(categoryId);
    }

}
