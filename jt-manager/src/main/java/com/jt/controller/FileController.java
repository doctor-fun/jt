package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVo;
import com.jt.vo.SysResult;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class FileController {

    /**
     * file.jsp
     * 要求实现图片上传，返回上传成功信息
     * 页面元素name是fileImage，必须一致
     * @return
     */
//    @PostMapping("/file")
//    public SysResult file(MultipartFile fileImage){
//        //确定文件上传的位置
//        String fileDir="/Users/king/Desktop/images";
//        File file=new File(fileDir);
//        //校验下文件路径是否正确
//        if(!file.exists()){
//            //如果文件不存在，则新建文件
//            file.mkdirs();
//        }
//        String name=fileImage.getName();
//        System.out.println("name:"+name);
//
//        String fileName=fileImage.getOriginalFilename();
//        String realPath=fileDir+"/"+fileName;
//        try {
//            fileImage.transferTo(new File(realPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return SysResult.ok();
//
//
//    }

    @Autowired
    public FileService fileService  ;

    //实现文件上传
    /**
     * /pic/upload
     * uploadFile文件名
     */
    @RequestMapping("/pic/upload")
    @ResponseBody//前端没收到的话可能这里不是json
    public ImageVo uploadFile(MultipartFile uploadFile){

        return  fileService.uploadFile(uploadFile);
    }

}
