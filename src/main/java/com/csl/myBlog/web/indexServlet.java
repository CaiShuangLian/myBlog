package com.csl.myBlog.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/11 14:12
 * @Version:
 * @Description:首页
 */

@WebServlet("/index")
public class indexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置导航首页高亮
        req.setAttribute("menu_page","index");

        //设置首页动态包含的页面值  (和index.jsp中的changePage对应)
        req.setAttribute("changePage","type/list.jsp");
        //请求跳转index.jsp
        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }
}
