package com.csl.myBlog.dao;

import com.csl.myBlog.po.User;
import com.csl.myBlog.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/29 9:55
 * @Version:
 * @Description:TODO
 */

public class UserDao {

    /**
     * 通过用户名查询用户对象，返回用户对象
     *  1.获取数据库连接
     *  2.定义SQL语句
     *  3.预编译
     *  4.设置参数
     *  5.执行查询，返回结果集
     *  6.判断并分析结果集
     *  7.关闭资源
     *
     * @param userName
     * @return
     */

    public User queryUserByName(String userName){

        User user=null;
        ResultSet resultSet=null;

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            //1.获取数据库连接
            connection=DBUtil.getConnection();
            //2.定义数据库语句
            String sql="select * from tb_user where uname=?";
            //3.预编译
            preparedStatement=connection.prepareStatement(sql);
            //4.设置参数
            preparedStatement.setString(1,userName);
            //5.执行查询，返回结果集
            resultSet=preparedStatement.executeQuery();
            //6.判断并分析结果集
            if (resultSet.next())
            {
                user=new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
                user.setNick(resultSet.getString("nick"));
                user.setUname(resultSet.getString("uname"));
                user.setUpwd(resultSet.getString("upwd"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //7.关闭资源
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }
}
