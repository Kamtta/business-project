package com.dreamTimes.dao;

import com.dreamTimes.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated
     */
    List<Cart> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cart
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Cart record);


    /**
     * 根据userID和productID判断是否存在购物车商品
     * @param userId
     * @param productId
     * @return
     */
    Cart findCartByUserIdAndProductId(@Param("userId") Integer userId,
                                     @Param("productId") Integer productId);


    /**
     * 根据userID查找相关的商品项
     * @param userId
     * @return
     */
    List<Cart> findCartByUserId(@Param("userId") Integer userId);

    /**
     * 查询是否全部选中
     * @param userId
     * @return
     */
    int checkedAll(Integer userId);


    /**
     * 删除购物车中的某个商品
     * @param userId
     * @param productIds
     * @return
     */
    int deleteByProductIds(@Param("userId") Integer userId,
                           @Param("productIds") Set<Integer> productIds);


    /**
     * 选中购物车某个商品，取消购物车某个商品，全选，全部取消
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    int updateChecked(@Param("userId") Integer userId,
                      @Param("productId") Integer productId,
                      @Param("checked") Integer checked);


    /**
     * 查询购物车中商品的数量
     * @param userId
     * @return
     */
    int countProducts(Integer userId);


    /**
     * 根据userID查找选中的商品项
     * @param userId
     * @return
     */
    List<Cart> findCheckedCartByUserId(@Param("userId") Integer userId);


    /**
     * 批量删除
     * @param cartList
     * @return
     */
    int deleteBatch(@Param("cartList") List<Cart> cartList);
}