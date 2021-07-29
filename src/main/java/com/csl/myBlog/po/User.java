package com.csl.myBlog.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/29 9:51
 * @Version:
 * @Description:TODO
 */

//导入lombok插件和依赖
@Getter
@Setter
public class User {

    private Integer userId;//用户ID
    private String uname;//用户名称
    private String upwd;//用户密码
    private String nick;//用户昵称
    private String head;//用户头像
    private String mood;//用户签名



}
