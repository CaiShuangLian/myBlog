package com.csl.myBlog.dao;

import com.csl.myBlog.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/30 16:13
 * @Version:
 * @Description:基础的JDBC操作类
 *                  更新操作（添加，修改，删除）
 *                  查询操作
 *                      1.查询一个字段（只会返回一条记录且只有一个字段；常用场景：查询总数量）
 *                      2.查询集合
 *                      3.查询某个对象
 */

public class BaseDao {

    /**
     * 更新操作
     * 添加，修改，删除
     * 1.得到数据库连接
     * 2.定义SQL语句
     * 3.预编译
     * 4.如果有参数，则设置参数，下标从1开始（数组，集合，循环设置参数）
     * 5.执行更新，返回受影响的行数
     * 6.关闭资源
     *
     *  注：需要两个参数：SQL语句，所需参数的集合
     *
     * @param sql
     * @param params
     * @return
     */
    public static int excuteUpdate(String sql, List<Object> params){
        int row=0;//受影响的行数

        Connection connection=null;
        PreparedStatement preparedStatement=null;

        try {
            //得到数据库连接
            connection= DBUtil.getConnection();
            //预编译
            preparedStatement=connection.prepareStatement(sql);
            //如果有参数，则设置参数，夏标聪1开始
            if(params!=null&&params.size()>0){
                //循环设置参数
                for(int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            row=preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(null,preparedStatement,connection);
        }

        return row;
    }

    /**
     * 查询一个字段  （只会返回一条记录且只有一个字段；常用场景：查询总数量）
     * 1.得到数据库连接
     * 2.定义SQL语句
     * 3.预编译
     * 4.如果有参数 则设置参数 下标从1开始
     * 6.执行查询
     * 6.判断并分析
     * @param sql
     * @param params
     * @return
     */
    public static Object findSingleValue(String sql,List<Object> params){
        Object object=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {
            //1.建立数据库连接
            connection=DBUtil.getConnection();
            //2.预编译
            preparedStatement=connection.prepareStatement(sql);
            //3.如果有参数 则设置参数
            if(params!=null&& params.size()>0){
                //循环设置参数
                for(int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //4.执行查询 返回结果集
            resultSet=preparedStatement.executeQuery();
            //5.判断并分析结果集
            if(resultSet.next())
            {
                object=resultSet.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }

        return object;

    }

    /**
     * 查询集合  （Javabean中的字段与数据库中表的字段对应）
     * 1.获取数据库连接
     * 2.定义SQL语句
     * 3.预编译
     * 4.如果有参数，则设置参数，小标从1开始（数组或集合，循环设置参数）
     * 5.执行查询，得到结果集
     * 6.得到结果集的元数据对象（查询到的字段数与查询了哪些字段）
     * 7.判断并分析结果集
     *      8.实例化对象
     *      9.遍历查询的字段数量，得到数据库中查询到的每一个列名
     *      10.通过反射，使用列名得到对应的field对象
     *      11.拼接set方法，得到字符串
     *      12.通过反射，将set方法的字符串反射成类中的指定set方法
     *      13.通过invoke调用set方法
     *      14.将对应的Javabean设置到集合中
     * 15.关闭资源
     * @param sql
     * @param params
     * @param cls
     * @return
     */
    public static List queryRows(String sql,List<Object> params,Class cls){
        ResultSet resultSet=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        List list = new ArrayList();
        try {
            //1.获取数据库连接
            connection=DBUtil.getConnection();
            //2.预编译
            preparedStatement=connection.prepareStatement(sql);
            //4.如果有参数，则设置参数，小标从1开始（数组或集合，循环设置参数）
            if(params!=null&& params.size()>0){
                for(int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //5.执行查询，得到结果集
            resultSet=preparedStatement.executeQuery();
            //6.得到结果集的元数据对象（查询到的字段数与查询了哪些字段）
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
            //7.得到查询的字段数量
            int fieldNum=resultSetMetaData.getColumnCount();
            //8.判断并分析结果集
            while (resultSet.next()){
                //9.实例化对象
                Object object=cls.newInstance();
                //10.遍历查询的字段数量，得到数据库中查询的每一个列名
                for(int i=1;i<=fieldNum;i++){
                    //11.得到查询到的每一个列名
                    //getColumnName()：获取列名
                    //getColumnLabel()：获取列名或别名
                    String columnName=resultSetMetaData.getColumnLabel(i);
                    //12.通过反射，使用列名得到对应的字段
                    Field field=cls.getDeclaredField(columnName);
                    //13.拼接set方法，得到字符串
                    String setMethod="set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);
                    //14.通过反射，将set方法字符串反射成类中对应的set方法
                    Method method=cls.getDeclaredMethod(setMethod,field.getType());
                    //15.得到查询的每一个字段对应的值
                    Object value=resultSet.getObject(columnName);
                    //16.通过invoke方法调用set方法
                    method.invoke(object,value);
                }
                //将Javabean设置到集合中
                list.add(object);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }

        return list;

    }

    /**
     * 查询对象
     * @param sql
     * @param params
     * @param cls
     * @return
     */
    public static Object queryRow(String sql,List<Object> params,Class cls){
        List list=queryRows(sql,params,cls);
//        Connection connection=null;
//        PreparedStatement preparedStatement=null;
//        ResultSet resultSet=null;
        Object object=null;
        if(list!=null && list.size()>0){
            object=list.get(0);
        }

//
//        try {
//            //1.获取数据库连接
//            connection=DBUtil.getConnection();
//            //2.预编译
//            preparedStatement=connection.prepareStatement(sql);
//            //3.如果有参数，则设置参数
//            if(params!=null && params.size()>0){
//               for(int i=0;i<params.size();i++){
//                   //循环设置参数
//                    preparedStatement.setObject(i,params.get(i));
//                }
//            }
//            //4.执行查询，得到结果集
//            resultSet=preparedStatement.executeQuery(sql);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            DBUtil.close(resultSet,preparedStatement,connection);
//        }
//
        return object;

    }
}
