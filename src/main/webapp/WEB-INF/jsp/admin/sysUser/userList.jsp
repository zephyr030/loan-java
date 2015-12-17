<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>控制面板</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="User login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" href="${path}/static/Ace/css/bootstrap.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/font-awesome.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/ace-fonts.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/ace.css" />
    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
    <script type="text/javascript" src="${path}/static/script/resize/iframeResizer.contentWindow.js" ></script>

</head>
<body class="no-skin" style="background-color: white;">
<div class="row">
    <div class="col-xs-12">
        <form action="sysUserList" id="form" name="form" method="post">
            <a href="<c:url value="init"/>">
                <button type="button" id="addUser" class="btn btn-white btn-info btn-round" style="margin-left: 12px;margin-bottom: 5px;">
                    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
                    新增
                </button>
            </a>
        </form>
        <div class="widget-box widget-color-blue2 ui-sortable-handle">
            <!-- 设置table颜色 -->
            <div class="widget-header">
                <h5 class="widget-title bigger lighter">
                    <i class="ace-icon fa fa-table"></i>
                    会员管理
                </h5>
            </div>

            <!-- table内容 -->
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <table class="table table-striped table-bordered table-hover">
                        <thead class="thin-border-bottom">
                        <tr>
                            <th><i></i>编号</th>
                            <th><i></i>用户名</th>
                            <th><i></i>状态</th>
                            <th><i></i>创建时间</th>
                            <th><i></i>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="obj">
                            <tr>
                                <th>${obj.id}</th>
                                <td>${obj.username}</td>
                                <td>
                                    <c:if test="${obj.available == -1}">删除</c:if>
                                    <c:if test="${obj.available == 0}">停用</c:if>
                                    <c:if test="${obj.available == 1}">可用</c:if>
                                </td>
                                <td><fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                <td>
                                    <div class="hidden-sm hidden-xs action-buttons">
                                        <c:if test="${obj.available <= 0}">
                                            <a href="isUserOpen?userId=${obj.id}&status=1" class="green">启用</a>
                                        </c:if>
                                        <c:if test="${obj.available == 1}">
                                            <a href="isUserOpen?userId=${obj.id}&status=0" class="blue">停用</a>
                                        </c:if>
                                        <c:if test="${obj.available >= 0}">
                                            <a href="isUserOpen?userId=${obj.id}&status=-1" class="red">删除</a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <nav>
            <ul class="pagination" style="float: right;padding-right: 3px;">
                <c:choose>
                    <c:when test="${page.hasPreviousPage}">
                        <li>
                            <a href="sysUserList?pageNumber=${page.pageNumber-1}" aria-label="Previous">
                                <span aria-hidden="true">上一页</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="disabled">
                            <a href="javascript:;" aria-label="Previous">
                                <span aria-hidden="true">上一页</span>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <c:forEach items="${page.navigatepageNums}" var="obj">
                    <c:choose>
                        <c:when test="${obj == page.pageNum}">
                            <li class="active"><a href="javascript:void(0)">${obj}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="sysUserList?pageNumber=${obj}">${obj}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${page.hasNextPage}">
                        <li>
                            <a href="sysUserList?pageNumber=${page.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">下一页</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="disabled">
                            <a href="javascript:;" aria-label="Next">
                                <span aria-hidden="true">下一页</span>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>