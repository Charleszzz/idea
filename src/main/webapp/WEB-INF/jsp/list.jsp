<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>列表</title>
    <meta charset="utf-8">
    <%@include file="common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover table-condensed table-bordered">
                <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>库存</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${seckillList}" var="s">
                        <tr>
                            <td>${s.seckillId}</td>
                            <td>${s.name}</td>
                            <td>${s.number}</td>
                            <td><fmt:formatDate value="${s.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                            <td><fmt:formatDate value="${s.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                            <td><fmt:formatDate value="${s.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                            <td>
                                <a class="btn btn-info" href="/seckill/${s.seckillId}/detail" target="_blank">link</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</html>
