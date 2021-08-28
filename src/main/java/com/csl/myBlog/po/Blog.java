package com.csl.myBlog.po;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/20 12:53
 * @Version:
 * @Description:博客模块
 */
@Setter
@Getter
public class Blog {

    private Integer noteId;//博客id
    private Integer title;//博客标题
    private String content;//博客内容
    private Integer typeId;//博客类型id
    private Timestamp timestamp;//博客发布时间
    private Double lon;//经度
    private Double lat;//维度

}
