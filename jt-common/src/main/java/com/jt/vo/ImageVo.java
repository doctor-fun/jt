package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//为了符合easyUI文件上传接口的vo
import java.awt.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class ImageVo
{
    //单词一定要写对，不然前后端绑定不了
    private Integer error;//0 正常，1失败
    private  String url;//图片虚拟路径
    private Integer width;//宽度
    private Integer height;//高度

    public static ImageVo fail(){
        return new ImageVo(1,null,null,null);
    }
    public static ImageVo success(String url,Integer width,Integer height){

        return  new ImageVo(0,url,width,height);
    }


}
