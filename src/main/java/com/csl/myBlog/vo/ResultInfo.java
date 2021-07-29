package com.csl.myBlog.vo;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/29 10:01
 * @Version:
 * @Description:TODO
 */

import lombok.Getter;
import lombok.Setter;

/**
 * 封装返回结果的类
 *      状态码
 *          成功1，失败0
 *      提示信息
 *      返回的对象（字符串，JavaBean，集合，map等）
 */
@Getter
@Setter
public class ResultInfo<T>{

    private  Integer code;//状态码，成功=1，失败=0
    private String msg;//提示信息
    private T result;//返回的对象（字符串，JavaBean，集合，Map等）
}
