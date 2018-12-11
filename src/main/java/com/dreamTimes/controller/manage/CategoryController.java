package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    public ServerResponse add_category(HttpSession httpSession,@RequestParam(required = false,defaultValue = "0")Integer parentId, String categoryName){
        Object o = httpSession.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return iCategoryService.add_category(user,parentId,categoryName);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 获取品类子节点（平级）
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    public ServerResponse get_category(HttpSession httpSession,Integer categoryId){
        Object o = httpSession.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return iCategoryService.get_category(user,categoryId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 修改品类名字
     * @param httpSession
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    public ServerResponse set_category_name(HttpSession httpSession,Integer categoryId,String categoryName){
        Object o = httpSession.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return iCategoryService.set_category_name(user,categoryId,categoryName);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    public ServerResponse get_deep_category(HttpSession session,Integer categoryId){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            if(user.getRole() != Const.USER_ROLE_MANAGE){
                return ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
            }
            return iCategoryService.get_deep_category(categoryId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

}
