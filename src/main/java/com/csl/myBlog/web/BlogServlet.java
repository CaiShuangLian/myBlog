package com.csl.myBlog.web;

import com.csl.myBlog.po.User;
import com.csl.myBlog.service.BlogService;
import com.csl.myBlog.service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/21 12:23
 * @Version:
 * @Description:博客模块
 */

@WebServlet("/note")
public class BlogServlet extends HttpServlet {

    private BlogService blogService=new BlogService();
    private TypeService typeService=new TypeService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置发表博客高亮
        req.setAttribute("menu_page","note");
        //接收用户行为
        String actionName=req.getParameter("actionName");
        if("view".equals(actionName)){
            view(req,resp);
        }
    }

    /**
     *页面显示
     * @param req
     * @param resp
     */
    private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //    1. 从Session对象中获取用户对象
        User user= (User) req.getSession().getAttribute("user");
        //        2. 通过用户ID查询对应的类型列表
        List typeList=typeService.queryTypeByUserId(user.getUserId());
        //        3. 将类型列表设置到request请求域中
        req.setAttribute("typeList",typeList);
        //        4. 设置首页动态包含的页面值
        req.setAttribute("changePage","note/view.jsp");
        //        5. 请求转发跳转到index.jsp
        req.getRequestDispatcher("index.jsp").forward(req,resp);//不使用重定向，避免请求域失效

    }

}
