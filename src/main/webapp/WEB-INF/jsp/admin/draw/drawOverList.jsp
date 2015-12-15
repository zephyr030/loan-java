<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <script type="text/javascript" src="${path}/static/script/My97DatePicker/WdatePicker.js" ></script>
</head>
<body class="no-skin" style="background-color: white;">
<div class="row">
    <div class="col-xs-12">
        <form action="drawOverList" id="form" name="form" method="post">
            <input type="text" name="account" value="${account}" placeholder="账号"/>
            <input type="text" name="name" value="${name}" style="margin-left: 12px;"  placeholder="姓名"/>
            <input type="text" name="mobile" value="${mobile}" style="margin-left: 12px;"  placeholder="手机号"/>
            <input type="text" name="flowNo" value="${flowNo}" style="margin-left: 12px;"  placeholder="银行单号"/>
            <input type="text" name="counts" value="${counts}" style="margin-left: 12px;"  placeholder="交易手数"/>
            <p style="margin-top: 5px;">
            提现金额:
            <input type="text" name="smoney" value="${smoney}" style="margin-left: 12px;"  placeholder="起始金额"
                   onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
            />—
            <input type="text" name="emoney" value="${emoney}" placeholder="结束金额"
                   onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
            />
            提现时间:
            <input type="text" id="startTime" name="startTime" size="16" readonly class="form_datetime" value="${startTime}"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d H%:m%' || '#F{$dp.$D(\'endTime\')}'})"
            />—
            <input type="text" id="endTime" name="endTime" size="16" readonly class="form_datetime" value="${endTime}"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startTime\')}'})"
            />
            操作时间:
            <input type="text" id="stTime" name="stTime" size="16" readonly class="form_datetime" value="${stTime}"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d H%:m%' || '#F{$dp.$D(\'enTime\')}'})"
            />—
            <input type="text" id="enTime" name="enTime" size="16" readonly class="form_datetime" value="${enTime}"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'stTime\')}'})"
            />
            <button type="submit" id="search" class="btn btn-white btn-info btn-round" style="margin-left: 12px;margin-bottom: 5px;">
                <i class="ace-icon fa fa-search bigger-120 green"></i>
                查询
            </button>
            <a href="ExcelDrawOverList?account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}&flowNo=${flowNo}&counts=${counts}&stTime=${stTime}&enTime=${enTime}">
                <button type="button" id="export" class="btn btn-white btn-info btn-round" style="margin-left: 12px;margin-bottom: 5px;">
                    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
                    导出
                </button>
            </a>
            </p>
        </form>
        <div class="widget-box widget-color-blue2 ui-sortable-handle">
            <!-- 设置table颜色 -->
            <div class="widget-header">
                <h5 class="widget-title bigger lighter">
                    <i class="ace-icon fa fa-table"></i>
                    放款列表
                </h5>
            </div>

            <!-- table内容 -->
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <table class="table table-striped table-bordered table-hover">
                        <thead class="thin-border-bottom">
                        <tr>
                            <th><i></i>编号</th>
                            <th><i></i>账号</th>
                            <th><i></i>姓名</th>
                            <th><i></i>开户行</th>
                            <th><i></i>银行账号</th>
                            <th><i></i>手机号</th>
                            <th><i></i>提现金额</th>
                            <th><i></i>提现时间</th>
                            <th><i></i>操作时间</th>
                            <th><i></i>交易手数</th>
                            <th><i></i>银行单号</th>
                            <th><i></i>操作人</th>
                            <th><i></i>状态</th>
                            <th><i></i>备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="obj">
                            <tr>
                                <th>${obj.id}</th>
                                <td>${obj.account}</td>
                                <td>${obj.customername}</td>
                                <td>${obj.bankname}</td>
                                <td>${obj.cardnumber}</td>
                                <td>${obj.mobile}</td>
                                <td>${obj.amount}</td>
                                <td>${obj.drawTime}</td>
                                <td>${obj.lastUpdateTime}</td>
                                <td>${obj.counts}</td>
                                <td>${obj.flowNo}</td>
                                <td>${obj.exeUser}</td>
                                <td>${obj.description}</td>
                                <td title="${obj.remark}">
                                    <c:if test="${fn:length(obj.remark) > 10}">
                                        ${fn:substring(obj.remark,0,10)}...
                                    </c:if>
                                    <c:if test="${fn:length(obj.remark) <= 10}">
                                        ${obj.remark}
                                    </c:if>
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
                            <a href="drawList?pageNumber=${page.pageNum-1}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}&flowNo=${flowNo}&counts=${counts}&stTime=${stTime}&enTime=${enTime}" aria-label="Previous">
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
                            <li><a href="drawList?pageNumber=${obj}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}&flowNo=${flowNo}&counts=${counts}&stTime=${stTime}&enTime=${enTime}">${obj}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${page.hasNextPage}">
                        <li>
                            <a href="drawList?pageNumber=${page.pageNum+1}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}&flowNo=${flowNo}&counts=${counts}&stTime=${stTime}&enTime=${enTime}" aria-label="Next">
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
</div>
</body>
</html>
