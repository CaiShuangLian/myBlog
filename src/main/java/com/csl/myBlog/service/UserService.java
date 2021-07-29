package com.csl.myBlog.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.csl.myBlog.dao.UserDao;
import com.csl.myBlog.po.User;
import com.csl.myBlog.vo.ResultInfo;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/29 9:56
 * @Version:
 * @Description:TODO
 */

public class UserService {
    private UserDao userDao=new UserDao();

    /**
     * 1. 判断参数是否为空
     *             如果为空
     *                 设置ResultInfo对象的状态码和提示信息
     *                 返回resultInfo对象
     *         2. 如果不为空，通过用户名查询用户对象
     *         3. 判断用户对象是否为空
     *                 如果为空
     *                     设置ResultInfo对象的状态码和提示信息
     *                     返回resultInfo对象
     *         4. 如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
     *                如果密码不正确
     *                     设置ResultInfo对象的状态码和提示信息
     *                     返回resultInfo对象
     *         5. 如果密码正确
     *             设置ResultInfo对象的状态码和提示信息
     *         6. 返回resultInfo对象
     * @param userName
     * @param userPwd
     * @return
     */

    public ResultInfo<User> userLogin(String userName,String userPwd){
        ResultInfo<User> resultInfo=new ResultInfo<>();
        //数据回显:当登录失
        //失败时，把失败信息返回给页面显示
        User u=new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        //设置到resultInfo对象中
        resultInfo.setResult(u);

        //1.判断参数是否为空
        //StrUtil.isBlank()既会判断是否为空，也会判断是否为null
        if(StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)){
            resultInfo.setCode(0);
            resultInfo.setMsg("用户姓名或密码不能为空！");//不论前台是否做校验，后台一定要做校验
            return resultInfo;
        }
        //2.如果不为空，通过用户名查询用户对象
        User user=userDao.queryUserByName(userName);
        //3. 判断用户对象是否为空
        if(user==null){
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户不存在！");
        }
        //4.如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
        else {
            //将输入的密码按照MD5进行加密，判断是否和数据库中的一致
            userPwd= DigestUtil.md5Hex(userPwd);
            if(userPwd.equals(user.getUpwd())){
                resultInfo.setCode(1);
                resultInfo.setResult(user);
                resultInfo.setMsg("登录成功！");
            }else{
                resultInfo.setCode(0);
                resultInfo.setMsg("密码错误！");
            }
        }
        return resultInfo;
    }
}
