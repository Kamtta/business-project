package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.CategoryMapper;
import com.dreamTimes.pojo.Category;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.ICategoryService;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public ServerResponse add_category(Integer parentId,String categoryName) {
//        step1:非空校验
        if(StringUtils.isBlank(String.valueOf(parentId)) || StringUtils.isBlank(categoryName)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断是否有权限进行操作
//        step3:判断类别名是否存在
        int result = categoryMapper.check_typeName(categoryName);
        if(result > 0){
            return ServerResponse.createServerResponseByError(ResponseCode.CATEGORY_EXITS.getStatus(),ResponseCode.CATEGORY_EXITS.getMsg());
        }
//        step4:返回结果
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        int insert_result = categoryMapper.insert(category);
        if (insert_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.INSERT_CATEGORY_FAIL.getStatus(),ResponseCode.INSERT_CATEGORY_FAIL.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(Const.INSERT_CATEGORY_SUCCESS);
    }

    @Override
    public ServerResponse get_category(Integer categoryId) {
//        step1:非空校验
        if(StringUtils.isBlank(String.valueOf(categoryId))){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户是否有权限进行操作
//        step3:根据品类id查询品类
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category == null){
            return ServerResponse.createServerResponseByError(ResponseCode.CATEGORY_NOT_FOUND.getStatus(),ResponseCode.CATEGORY_NOT_FOUND.getMsg());
        }
//        step4:查询平级子类
        List<Category> categorys = categoryMapper.findChildCategory(categoryId);
//        step5:返回结果
        return ServerResponse.createServerResponseBySuccess(null,categorys);
    }

    @Override
    public ServerResponse set_category_name( Integer categoryId, String categoryName) {
//        step1：非空校验
        if(StringUtils.isBlank(String.valueOf(categoryId)) || StringUtils.isBlank(categoryName)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2：判断是否有权限
//        step3：根据id进行修改
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int update_result = categoryMapper.updateByPrimaryKey(category);
        if(update_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.CATEGORY_UPDATE_FAIL.getStatus(),ResponseCode.CATEGORY_UPDATE_FAIL.getMsg());
        }
//        step4：返回结果
        return ServerResponse.createServerResponseBySuccess(Const.UPDATE_CATEGORYNAME_SUCCESS);
    }

    @Override
    public ServerResponse get_deep_category(Integer categoryId) {
//        step1：非空校验
        if(StringUtils.isBlank(String.valueOf(categoryId))){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2：查询子节点
        Set<Category> categorySet = Sets.newHashSet();
        categorySet = findAllChildCategory(categorySet,categoryId);

        Set<Integer> integerSet = Sets.newHashSet();

        Iterator<Category> categoryIterator = categorySet.iterator();
        while (categoryIterator.hasNext()){
            Category category = categoryIterator.next();
            integerSet.add(category.getId());
        }
//        step3：返回结果
        return ServerResponse.createServerResponseBySuccess(null,integerSet);
    }

    public Set<Category> findAllChildCategory(Set<Category> set,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            set.add(category);
        }

//        查找categoryId下的子节点
        List<Category> categoryList = categoryMapper.findChildCategory(categoryId);
        if(categoryList != null && categoryList.size() > 0){
            for (Category category1:categoryList) {
                findAllChildCategory(set,category1.getId());
            }
        }
        return set;
    }
}
