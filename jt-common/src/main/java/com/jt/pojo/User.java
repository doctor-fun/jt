package com.jt.pojo;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_user")
@Data
@Accessors(chain = true)
public class User extends BasePojo  {
//    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Long id;


    private String username;
    private String password;
    private String phone;
    private String email;


}
