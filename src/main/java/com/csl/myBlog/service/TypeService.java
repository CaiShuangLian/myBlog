package com.csl.myBlog.service;

import cn.hutool.core.util.StrUtil;
import com.csl.myBlog.dao.BlogDao;
import com.csl.myBlog.dao.TypeDao;
import com.csl.myBlog.po.Type;
import com.csl.myBlog.vo.ResultInfo;

import java.util.List;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/20 11:33
 * @Version:
 * @Description:博客类型服务层
 */

public class TypeService {
    private TypeDao typeDao=new TypeDao();
    private BlogDao blogDao=new BlogDao();

    /**
     * 查询类型
     * @param userId
     * @return
     */
    public List queryTypeByUserId(Integer userId) {

        List typeResultInfo=typeDao.queryTypeByUserId(userId);
        return typeResultInfo;
    }

    /**
     * 根据类型id删除
     * @param typeId
     * @return
     */
    public ResultInfo deleteByTypeId(String typeId) {
        ResultInfo resultInfo=new ResultInfo();
//        1. 判断参数是否为空
        if(StrUtil.isBlank(typeId)){
            return resultInfo;
        }
//        2. 调用Dao层，通过类型ID查询相关博客的数量
        Integer row=blogDao.queryByTypeId(typeId);
//        3. 如果数量大于0，说明存在子记录，不可删除
//                code=0，msg="该类型存在子记录，不可删除"，返回resultInfo对象
        if(row>0){
            resultInfo.setCode(0);
            resultInfo.setMsg("存在该类型博客，不可删除");
            return resultInfo;
        }
//        4. 如果不存在子记录，调用Dao层的更新方法，通过类型ID删除指定的类型记录，返回受影响的行数
        Integer rows=typeDao.deleteByTypeId(typeId);
//        5. 判断受影响的行数是否大于0
//        大于0，code=1；否则，code=0，msg="删除失败"
        if(rows>0){
            resultInfo.setCode(1);
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("删除失败");
        }
//        6. 返回ResultInfo对象
        return resultInfo;
    }

    /**
     * 根据用户id修改
     * @param userId
     * @return
     */
    public ResultInfo updateTypeByUserId(Integer userId,String typeId,String typeName) {

        ResultInfo resultInfo=new ResultInfo();
//        1. 判断参数是否为空 （类型名称）
//        如果为空，code=0，msg=xxx，返回ResultInfo对象
        if(typeName==null){
            resultInfo.setCode(0);
            resultInfo.setMsg("名称不能为空！");
            return resultInfo;
        }
//        2. 调用Dao层，查询当前登录用户下，类型名称是否唯一，返回0或1
//          如果不可用，code=0，msg=xxx，返回ResultInfo对象
        Integer flag=typeDao.countType(userId,typeName);
        if(flag>0){
            resultInfo.setCode(0);
            resultInfo.setMsg("当前已有该类型");
            return resultInfo;
        }
//        3. 判断类型ID是否为空
//          如果为空，调用Dao层的添加方法，返回主键 （前台页面需要显示添加成功之后的类型ID）
//          如果不为空，调用Dao层的修改方法，返回受影响的行数
        Integer row=0;
        Integer typekey=-1;
        if(StrUtil.isBlank(typeId)){
            Object o=typeDao.addType(typeName,userId);
            if(o instanceof Type){
                typekey=((Type) o).getTypeId();
            }
            typeId= String.valueOf(o);
        }else {
            row=typeDao.updateByTypeId(typeId,typeName);//typeID != null
        }
//        4. 判断 主键/受影响的行数 是否大于0
//           如果大于0，则更新成功
//                code=1，result=主键
//          如果不大于0，则更新失败
//                code=0，msg=xxx
        if(row>0 && !StrUtil.isBlank(typeId)){
            resultInfo.setCode(1);
            resultInfo.setMsg("修改成功！");
            resultInfo.setResult(typeId);//将typeID传回前台
        }else if(row==0 && !StrUtil.isBlank(typeId)){
            resultInfo.setResult(typekey);
            resultInfo.setCode(1);
            resultInfo.setMsg("添加成功！");
        }else{
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败！");
        }
        return resultInfo;
    }
}
