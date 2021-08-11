package com.csl.myBlog.filter;

import cn.hutool.core.util.StrUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:CaiShuangLian
 * @FileName:
 * @Date:Created in  2021/8/11 15:09
 * @Version:
 * @Description:字符乱码处理过滤器
 *
 *请求乱码解决
 *    乱码原因：
 *      服务器默认的解析编码为：ISO-8859-1，不支持中文
 *    乱码情况：
 *       Post请求：
 *          Tomcat7及以下版本   乱码
 *          Tomcat8及以上版本    乱码
 *       Get请求：
 *          Tomcat7及以下版本   乱码
 *          Tomcat8及以上版本   不乱码
 *
 *    解决方案：
 *          POST请求：
 *              无论是什么版本都会乱码，需要通过设置request.setCharacterEncoding("UTF-8")---只对post请求有效
 *          GET请求：
 *              Tomcat8以上版本不会乱码，不需要处理，处理反而会出现乱码
 *              Tomcat7及以下版本需要单独处理
 *                  new String(request.getParamater("xxx").getBytes("ISO_8859-1"),"UTF-8")
 */

@WebFilter("/*")//过滤所有资源
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //基于HTTP
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        //处理POST请求  只针对post请求有效  get请求无效
        request.setCharacterEncoding("UTF-8");

        //得到请求类型
        String method=request.getMethod();
        //如果是get请求 则需要判断服务器版本
        //equalsIgnoreCase忽略大小写
        if("GET".equalsIgnoreCase(method)){
            //得到服务器版本
            String serverInfo=request.getServletContext().getServerInfo();//Apache Tomcat/7.0.79

            //通过截取字符串得到具体的版本号
            String version=serverInfo.substring(serverInfo.indexOf("/")+1,serverInfo.indexOf("."));
            //判断服务器版本是否是7以下
            if(version!=null&&Integer.parseInt(version)<8){
                //Tomcat7以及以下的服务器的get请求
                MyWapper myWapper=new MyWapper(request);
                //放行资源
                filterChain.doFilter(myWapper,response);
                return;
            }
        }

        filterChain.doFilter(request,response);

    }

    /**
     * 1.定义内部类  类的本质是一个request对象
     * 2.继承HttpServletRequestWrapper包装类
     * 3.重写getParamater()方法
     */
    class MyWapper extends HttpServletRequestWrapper{

        //定义成员变量  HttpServletRequest对象（提升构造器中request对象的作用域）
        private HttpServletRequest request;
        /**
         * //构造器
         * 带参构造
         * 可以得到需要处理的request对象
         * @param request
         */
        public MyWapper(HttpServletRequest request) {

            super(request);
            this.request=request;
        }

        /**
         * 重写getParameter方法，处理乱码对象
         * @param name
         * @return
         */
        @Override
        public String getParameter(String name) {
            //获取参数  乱码的参数值
            String value=request.getParameter(name);
            if(StrUtil.isBlank(value)){
                return value;
            }
            //通过new String（）处理乱码
            try {
                value=new String(value.getBytes("ISO-8859-1"),"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }

            return value;
        }
    }
}
