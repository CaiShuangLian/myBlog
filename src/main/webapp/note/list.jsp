<%--
  Created by IntelliJ IDEA.
  User: Dijkstra
  Date: 2021/8/11
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<%--引入标签库--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-md-9">

    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
            博客列表
        </div>
        <!--判断列表是否存在-->
        <c:if test="${empty page}">
            <h2>暂未查询到记录</h2>
        </c:if>
        <c:if test="${!empty page}">
            <!--遍历获取列表-->
            <div class="note_datas">
                <ul>
                    <C:forEach items="${page.dataList}" var="item">
                        <li>
                            『<fmt:formatDate value="${item.pubTime}" pattern="yyyy-MM-dd"/>』
                            <a href="note?actionName=detail&noteId=${item.noteId}">${item.title}</a>
                        </li>
                    </C:forEach>
                </ul>
            </div>
            <nav style="text-align: center">
                <ul class="pagination   center">
                    <!--如果当前不是第一页则显示上一页的按钮-->
                    <c:if test="${page.pageNum >1}">
                        <li>
                            <a href="index?pageNum=${page.prePage}&actionName=${action}&title=${title}&date=${date}&typeId=${typeId}">
                                <span>《</span></a>
                        </li>
                    </c:if>
                    <c:forEach begin="${page.startNavPage}" end="${page.endNavPage}" var="p">
                        <li <c:if test="${page.pageNum == p}">class="active"
                        </c:if> >
                            <a href="index?pageNum=${p}&actionName=${action}&title=${title}&date=${date}&typeId=${typeId}">${p}</a>
                        </li>
                    </c:forEach>
                    <!--如果当前不是最后一页则显示下一页的按钮-->
                    <c:if test="${page.pageNum < page.totalPages}">
                        <li>
                            <a href="index?pageNum=${page.nextPage}&actionName=${action}&title=${title}&date=${date}&typeId=${typeId}">
                                <span>》</span></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
    </div>

</div>