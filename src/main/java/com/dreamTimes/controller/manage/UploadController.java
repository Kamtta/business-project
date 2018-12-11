package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/manage/product/")
public class UploadController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "upload")
    public String upload(){
        return "upload";
    }

    @RequestMapping(value = "upload.do")
    @ResponseBody
    public ServerResponse uploadpic(@RequestParam(value = "upload_file",required = false) MultipartFile file){

        String path = Const.UPLOAD_PATH;
        return productService.uploadPic(file,path);
    }
}
