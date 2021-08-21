package com.csl.myBlog.dao;

import com.csl.myBlog.po.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/20 11:28
 * @Version:
 * @Description:博客类型模块
 */

public class TypeDao {

    /**
     * 通过用户id查询类型
     * @param userId
     * @return
     */
    public List queryTypeByUserId(Integer userId) {

//        1. 定义SQL语句
        String sql = "select typeId,typeName,userId from tb_note_type where userId = ? ";
//        2. 设置参数列表
        List<Object> params=new ArrayList<>();
        params.add(userId);
//        3. 调用BaseDao的查询方法，返回集合
        List resultInfo=BaseDao.queryRows(sql,params,Type.class);
//        4. 返回集合
        return resultInfo;
    }

    /**
     * 通过类型ID删除记录
     * @param typeId
     * @return
     */
    public Integer deleteByTypeId(String typeId) {
//        通过类型ID删除指定的类型记录，返回受影响的行数
        String sql="delete from tb_note_type where typeId=?";
        List<Object>params=new ArrayList<>();
        params.add(typeId);
        return BaseDao.excuteUpdate(sql,params);

    }


    /**
     * 查询当前登录用户下，类型名称是否唯一
     * @param userId
     * @return
     */
    public Integer countType(Integer userId,String typeName) {
        String sql="select * from tb_note_type where userId=? and typeName=?";
        List<Object>params=new ArrayList<>();
        params.add(userId);
        params.add(typeName);
        return BaseDao.queryRows(sql,params,Type.class).size();
    }

    /**
     * 根据typeID和typeName修改方法，返回受影响的行数
     * @param typeId
     * @param typeName
     * @return
     */
    public Integer updateByTypeId(String typeId, String typeName) {
        String sql="update tb_note_type set typeName = ? where typeId = ?";
        List<Object>params=new ArrayList<>();
        params.add(typeName);
        params.add(typeId);
        return BaseDao.excuteUpdate(sql,params);
    }

    /**
     * 添加类型，返回主键
     * @param typeName
     * @return
     */
    public Object  addType(String typeName,Integer userId) {
        String sql="insert into tb_note_type(typeName,userId) values (?,?)";
        String sql1="select typeId from tb_note_type where typeName=? and userId=?";
        List<Object>params=new ArrayList<>();
        params.add(typeName);
        params.add(userId);
        int row = BaseDao.excuteUpdate(sql,params);
        Object typeId=null;
        if(row>0){
             typeId=  BaseDao.queryRow(sql1,params,Type.class);
        }
        return typeId;
    }
}
