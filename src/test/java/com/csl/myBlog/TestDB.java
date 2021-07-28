package com.csl.myBlog;

import com.csl.myBlog.util.DBUtil;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

//import java.util.logging.Logger;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/27 10:32
 * @Version:
 * @Description:测试数据库连接工具类
 */

public class TestDB {

    //使用日志工厂类 主要是记录日志，获取日志对象
    private Logger logger= LoggerFactory.getLogger(TestDB.class);

    /**
     * 单元测试方法：
     * 1.方法的返回值，建议使用void 一般没有返回值
     * 2.参数列表，建议空参，一般是没有参数
     * 3.方法上需要设置@Test注解
     * 4.每个方法都能独立运行
     *
     * 判定结果：
     *      绿色：成功
     *      红色：失败
     */

    @Test
    public void testDB(){
        System.out.println(DBUtil.getConnection());

        //使用日志
        logger.info("获取数据库连接："+DBUtil.getConnection());
        logger.info("获取数据库{}连接",DBUtil.getConnection());



    }
}
