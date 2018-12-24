package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.CategoryMapper;
import com.dreamTimes.dao.ProductMapper;
import com.dreamTimes.pojo.Category;
import com.dreamTimes.pojo.Product;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.ICategoryService;
import com.dreamTimes.service.IProductService;
import com.dreamTimes.utils.DateUtils;
import com.dreamTimes.utils.FTPUtils;
import com.dreamTimes.utils.PropertiesUtils;
import com.dreamTimes.vo.ProductDetailVO;
import com.dreamTimes.vo.ProductListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ICategoryService categoryService;

    @Override
    public ServerResponse save( Product product) {
//        step1：非空校验
        if(product == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2：权限的判断

//        step3：获取主图
        if (!StringUtils.isBlank(product.getSubImages())){
            String[] subImgs = product.getSubImages().split(",");
            if (subImgs.length > 0){
                String mainImg = subImgs[0];
                product.setMainImage(mainImg);
            }
        }
//        step4：新增或者更新，并返回相应的结果
        if(product.getId() == null){
            int insert_result = productMapper.insert(product);
            if(insert_result <= 0){
                return ServerResponse.createServerResponseByError(ResponseCode.INSERT_PRODUCT_FAIL.getStatus(),ResponseCode.INSERT_PRODUCT_FAIL.getMsg());
            }else {
                return ServerResponse.createServerResponseBySuccess(Const.INSERT_PRODUCT_SUCCESS);
            }
        }else {
            int update_result = productMapper.updateByPrimaryKey(product);
            if (update_result <= 0){
                return ServerResponse.createServerResponseByError(ResponseCode.UPDATE_PRODUCT_FAIL.getStatus(),ResponseCode.UPDATE_PRODUCT_FAIL.getMsg());
            }else {
                return ServerResponse.createServerResponseBySuccess(Const.UPDATE_PRODUCT_SUCCESS);
            }
        }
    }

    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
//        step1：非空校验
        if(StringUtils.isBlank(String.valueOf(productId)) || StringUtils.isBlank(String.valueOf(status))){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2：权限判断
//        step3：状态的更新
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int result = productMapper.updateByPrimaryKey(product);
        if(result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.UPDATE_PRODUCT_FAIL.getStatus(),ResponseCode.UPDATE_PRODUCT_FAIL.getMsg());
        }
//        step4：返回结果
        return ServerResponse.createServerResponseBySuccess(Const.UPDATE_PRODUCT_SUCCESS);
    }

    @Override
    public ServerResponse detailManage(Integer productId) {
//        step1：非空校验
        if(StringUtils.isBlank(String.valueOf(productId))){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2：判断权限
//        step3：根据id查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_FOUND_PRODUCT.getStatus(),ResponseCode.NOT_FOUND_PRODUCT.getMsg());
        }
        ProductDetailVO productDetailVO = changeToVo(product);
//        step4：返回结果
        return ServerResponse.createServerResponseBySuccess(null,productDetailVO);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
//        step1:判断权限
//        step2:加载分页插件
       PageHelper.startPage(pageNum,pageSize);
//        step3：查询信息
        List<Product> productList = productMapper.selectAll();
        List<ProductListVO> productListVOList = new ArrayList<>();
        if(productList != null){
            for (Product product:productList) {
                ProductListVO productListVO = assembleToProductVO(product);
                productListVOList.add(productListVO);
            }
        }
//        step4：返回结果
        PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse search( Integer productId, String productName, Integer pageNum, Integer pageSize) {
//        step1:权限判断
//        step2：非空判断处理
        if(!StringUtils.isBlank(productName)){
            productName = "%" + productName + "%";
        }
//        step3：分页搜索
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.findByProductIdAndproductName(productId,productName);
        List<ProductListVO> productListVOList = new ArrayList<>();
        if(productList != null){
            if(productList.size() > 0){
                for (Product product:
                     productList) {
                    ProductListVO productListVO = assembleToProductVO(product);
                    productListVOList.add(productListVO);
                }
            }
        }
//        step4：返回结果
        PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse uploadPic(MultipartFile file, String path) {
//        step1：非空判断
        if(file==null){
            return ServerResponse.createServerResponseByError(ResponseCode.UPLOAD_PIC_FAIL.getStatus(),ResponseCode.UPLOAD_PIC_FAIL.getMsg());
        }
//        step2：创建上传目录
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdir();
        }
        String oldFileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString();
        newFileName += oldFileName.substring(oldFileName.lastIndexOf("."));
        File result = new File(path,newFileName);
        try {
            file.transferTo(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        将图片上传到FTP服务器上
        List<File> fileList = Lists.newArrayList();
        fileList.add(result);
        try {
            FTPUtils.uploadFile(fileList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> result_map = Maps.newHashMap();
        result_map.put("uri",newFileName);
        result_map.put("url",PropertiesUtils.getKey("imagesHost")+newFileName);
        result.delete();
        return ServerResponse.createServerResponseBySuccess(null,result_map);
    }

    @Override
    public ServerResponse detail(Integer productId) {
        if(StringUtils.isBlank(String.valueOf(productId))){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_FOUND_PRODUCT.getStatus(),ResponseCode.NOT_FOUND_PRODUCT.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(null,product);
    }

    @Override
    public ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        if(StringUtils.isBlank(String.valueOf(categoryId)) && StringUtils.isBlank(keyword)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        Set<Integer> integerSet = Sets.newHashSet();
        if(!StringUtils.isBlank(String.valueOf(categoryId))){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVO> list = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(list);
                return ServerResponse.createServerResponseBySuccess(null,pageInfo);
            }

            ServerResponse serverResponse = categoryService.get_deep_category(categoryId);

            if (serverResponse.isSucess()){
                integerSet = (Set<Integer>) serverResponse.getData();
            }
        }
            if(!StringUtils.isBlank(keyword)){
                keyword = "%" + keyword + "%";
            }
            if(StringUtils.isBlank(orderBy)){
               PageHelper.startPage(pageNum,pageSize);
            }else {
                String[] order = orderBy.split("_");
                if(order.length > 1){
                    PageHelper.startPage(pageNum,pageSize,order[0]+" "+order[1]);
                }
            }
            List<Product> result = productMapper.searchProduct(integerSet,keyword);
            List<ProductListVO> productListVOList = Lists.newArrayList();
            if(result != null && result.size() > 0){
                for (Product product:
                     result) {
                    ProductListVO productListVO = assembleToProductVO(product);
                    productListVOList.add(productListVO);
                }
            }
            PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    public ProductListVO assembleToProductVO(Product product){
        ProductListVO productListVO = new ProductListVO();
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setId(product.getId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        return productListVO;
    }

    public ProductDetailVO changeToVo(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setId(product.getId());
        productDetailVO.setImageHost(PropertiesUtils.getKey("imagesHost"));
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setName(product.getName());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category != null){
            productDetailVO.setParentCategoryId(category.getParentId());
        }
        return productDetailVO;
    }
}
