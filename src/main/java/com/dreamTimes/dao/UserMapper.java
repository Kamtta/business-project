package com.dreamTimes.dao;

import com.dreamTimes.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);


    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);



    /**
     * 根据用户名和密码进行查询
     * @param username
     * @param password
     * @return
     */
    User selectUserByUsernameAndPassword(@Param("username") String username,
                                         @Param("password") String password);


    /**
     * 判断邮箱是否存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 根据用户名获取密保问题
     * @param username
     * @return
     */
    String forget_get_question(String username);

    /**
     * 校验密保答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int forget_check_answer(@Param("username") String username,
                            @Param("question") String question,
                            @Param("answer") String answer);


    /**
     * 根据用户名进行重设密码
     * @param username
     * @param password
     * @return
     */
    int forget_reset_password(@Param("username") String username,
                              @Param("password") String password);


    /**
     * 更新个人信息
     * @param user
     * @return
     */
    int update_information(User user);

}