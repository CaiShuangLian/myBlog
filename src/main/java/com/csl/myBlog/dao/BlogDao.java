package com.csl.myBlog.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/20 13:03
 * @Version:
 * @Description:博客模块
 */

public class BlogDao {

    /**
     * 通过类型ID查询博客数量
     * @param typeId
     * @return
     */
    public Integer queryByTypeId(String typeId) {
//        通过类型ID查询云记记录的数量，返回云记数量
        String sql="select *from tb_note where typeId=?";
        List<Object> params=new ArrayList<>();
        params.add(typeId);
        Integer row= (Integer) BaseDao.findSingleValue(sql,params);
        if(row==null){
            return 0;
        }
        return row;
    }
}
