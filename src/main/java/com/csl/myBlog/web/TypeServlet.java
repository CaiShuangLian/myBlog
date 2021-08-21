package com.csl.myBlog.web;

import com.alibaba.fastjson.JSON;
import com.csl.myBlog.po.User;
import com.csl.myBlog.service.TypeService;
import com.csl.myBlog.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/20 11:23
 * @Version:
 * @Description:类型模块
 */
@WebServlet("/type")
public class TypeServlet extends HttpServlet {

    private TypeService typeService=new TypeService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","type");

        //接收用户行为
        String actionName=req.getParameter("actionName");

        if("list".equals(actionName)){
            list(req, resp);
        }
        else if("delete".equals(actionName)){
            delete(req,resp);
        }else if("addOrUpdate".equals(actionName)){
            addOrUpdate(req,resp);
        }

    }

    /**
     * 添加或者修改
     * @param req
     * @param resp
     */
    private void addOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        1. 接收参数 （类型名称、类型ID）
        String typeName=req.getParameter("typeName");
        String  typeId=req.getParameter("typeId");
//        2. 获取Session作用域中的user对象，得到用户ID
        User user= (User) req.getSession().getAttribute("user");
//        3. 调用Service层的更新方法，返回ResultInfo对象
        ResultInfo resultInfo=typeService.updateTypeByUserId(user.getUserId(),typeId,typeName);
//        4. 将ResultInfo转换成JSON格式的字符串，响应给ajax的回调函数
        resp.setContentType("application/json;charset=utf-8");
        //打印测试一下

        System.out.println(JSON.toJSONString(resultInfo));
        resp.getWriter().write(JSON.toJSONString(resultInfo));
        resp.getWriter().close();
    }

    /**
     * 显示列表
     * @param req
     * @param resp
     */
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        1. 获取Session作用域设置的user对象
        User user= (User) req.getSession().getAttribute("user");
//        2. 调用Service层的查询方法，查询当前登录用户的类型集合，返回集合
//        ResultInfo<List> typeResultInfo=typeService.queryTypeByUserId(user.getUserId());
        List typeList=typeService.queryTypeByUserId(user.getUserId());
//        3. 将类型列表设置到request请求域中
        req.setAttribute("typeList",typeList);
//        4. 设置首页动态包含的页面值
        req.setAttribute("changePage","type/list.jsp");
//        5. 请求转发跳转到index.jsp页面
        req.getRequestDispatcher("index.jsp").forward(req,resp);//不使用重定向，避免请求域失效

    }

    /**
     * 删除类型操作
     * @param req
     * @param resp
     */
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //        1. 接收参数（类型ID）
        String typeId=req.getParameter("typeId");
        //        2. 调用Service的更新操作，返回ResultInfo对象
        ResultInfo resultInfo=typeService.deleteByTypeId(typeId);
        //        3. 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter=resp.getWriter();
        String json= JSON.toJSONString(resultInfo);
        printWriter.write(json);
        printWriter.close();

    }
}
