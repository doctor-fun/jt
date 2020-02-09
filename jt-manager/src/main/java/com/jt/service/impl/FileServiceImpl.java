package com.jt.service.impl;

import com.jt.service.FileService;
import com.jt.vo.ImageVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
//classpath是resources
@Controller
@PropertySource("classpath:/properties/image.properties")//找到文件
public class FileServiceImpl implements FileService {
    ImageVo imageVo=null;


    @Value("${image.localDirPath}")
    private  String localDirPath;

    @Value("${image.urlPath}")
    private String urlPath;

    @Override
    public ImageVo uploadFile(MultipartFile uploadFile) {
        //获取文件名称abc.jpg
        String fileName = uploadFile.getOriginalFilename();
        //校验是否为图片类型
        fileName = fileName.toLowerCase();//先转化为小写
        if (!fileName.matches("^.+\\.(jpg|png|gif|jpeg)$")) {
            //^表示开头，$标记为结尾\\.表示字符".",而没转义的.表示任意个数的字符
            //说明不是图片
            return ImageVo.fail();
        }
        //校验程序是否为恶意程序，检验图片是否有宽高
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            if (height == 0 || width == 0) {
                return ImageVo.fail();
            }


            //分目录存储
            String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());

            String fileLocalPath = localDirPath + datePath;
            File fileDir = new File(fileLocalPath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //1.按照类型
            //2.按照时间
            //文件名称不能重复 UUID
            String uuid = UUID.randomUUID().toString().replace("-","");
            //拿到文件路径
            int index = fileName.lastIndexOf(".");
            String type = fileName.substring(index);
            String uuidName = uuid + type;


            //实现文件上传 目录路径+文件名称
            String realFilePath = fileLocalPath + uuidName;
            //完成文件上传
            uploadFile.transferTo(new File(realFilePath));


            //拼接url路径 http+date路径
            String url=urlPath+datePath+uuidName;


            //将文件信息回传给页面
            imageVo=ImageVo.success(url,width,height);

        } catch (IOException e) {
            e.printStackTrace();
            return ImageVo.fail();
        }
        return  imageVo;

    }
}

