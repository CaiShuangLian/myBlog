package com.csl.myBlog.web;

import com.csl.myBlog.po.User;
import com.csl.myBlog.service.UserService;
import com.csl.myBlog.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/7/29 9:58
 * @Version:
 * @Description:TODO
 */
//web层调用service层

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService service=new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);

        //接收用户行为
        String actionName=req.getParameter("actionName");
        //判断用户行为，调用对应的方法
        if("login".equals(actionName)){
            //用户登录
            userLogin(req,resp);
        }
    }

    /**
     * @param req
     * @param resp
     */
    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取参数 （姓名、密码）
        String userName=req.getParameter("userName");//从表单（jsp）获取
        String userPwd=req.getParameter("userPwd");
        //2. 调用Service层的方法，返回ResultInfo对象
        ResultInfo<User> resultInfo=service.userLogin(userName,userPwd);
        //3. 判断是否登录成功
        if(resultInfo.getCode()==1){
            //将用户信息设置到session作用域中
            req.getSession().setAttribute("user",resultInfo.getResult());
            //判断用户是否选择记住密码（rem的值是1）
            String rem=req.getParameter("rem");
            // 如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
            if("1".equals(rem)){
                Cookie cookie=new Cookie("user",userName+"-"+userPwd);//中间一个分隔符方便取值
                //设置失效时间
                cookie.setMaxAge(3*24*60*60);
                //响应给客户端
                resp.addCookie(cookie);
            }else{
                // 如果否，清空原有的cookie对象
                Cookie cookie=new Cookie("user",null);
                //删除cookie，设置maxage为0
                cookie.setMaxAge(0);
            }
            //重定向跳转到index页面
            resp.sendRedirect("index.jsp");

        }else {
            //将resultInfo对象设置到request作用域中
            req.setAttribute("resultInfo",resultInfo);
            //请求转发跳转到登录页面
            req.getRequestDispatcher("login.jsp").forward(req,resp);//forward需要抛异常
        }
    }
}
