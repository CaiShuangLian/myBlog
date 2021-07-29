package com.csl.myBlog;

import com.csl.myBlog.dao.UserDao;
import com.csl.myBlog.po.User;
import org.junit.Test;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/29 10:39
 * @Version:
 * @Description:TODO
 */

public class TestUser {

    @Test
    public void testQueryUserByName(){
        UserDao userDao=new UserDao();
        User user=userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }
}


