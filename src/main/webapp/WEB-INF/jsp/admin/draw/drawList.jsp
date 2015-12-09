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
    <script type="text/javascript" src="${path}/static/script/draw/draw.js?v=2222" ></script>
</head>
<body class="no-skin" style="background-color: white;">
    <div class="row">
        <div class="col-xs-12">
            <form action="drawList" id="form" name="form" method="post">
                <input type="text" name="account" value="${account}" style="margin-left: 12px;"  placeholder="账号"/>
                <input type="text" name="name" value="${name}" style="margin-left: 12px;"  placeholder="姓名"/>
                <input type="text" name="mobile" value="${mobile}" style="margin-left: 12px;"  placeholder="手机号"/>
                <input type="text" name="smoney" value="${smoney}" style="margin-left: 12px;"  placeholder="起始金额"
                       onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                />
                --
                <input type="text" name="emoney" value="${emoney}" placeholder="结束金额"
                       onkeyup="value=value.replace(/[^\d]/g,'') "  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
                />
                <input type="text" id="startTime" name="startTime" size="16" readonly class="form_datetime" value="${startTime}"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d H%:m%' || '#F{$dp.$D(\'endTime\')}'})"
                />
                <input type="text" id="endTime" name="endTime" size="16" readonly class="form_datetime" value="${endTime}"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startTime\')}'})"
                />
                <button type="submit" id="search" class="btn btn-white btn-info btn-round" style="margin-left: 12px;margin-bottom: 5px;">
                    <i class="ace-icon fa fa-search bigger-120 green"></i>
                    查询
                </button>
                <a href="exportRechargeList">
                    <button type="button" id="export" class="btn btn-white btn-info btn-round" style="margin-left: 12px;margin-bottom: 5px;">
                        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
                        导出
                    </button>
                </a>
            </form>
            <div class="widget-box widget-color-blue2 ui-sortable-handle">
                <!-- 设置table颜色 -->
                <div class="widget-header">
                    <h5 class="widget-title bigger lighter">
                        <i class="ace-icon fa fa-table"></i>
                        Tables &amp; Colors
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
                                <th><i></i>提现时间</th>
                                <th><i></i>操作</th>
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
                                        <td>${obj.drawTime}</td>
                                        <td>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <a class="blue" href="javascript:;" onclick="draw(${obj.id})">提现</a>
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
                                <a href="drawList?pageNumber=${page.pageNumber-1}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}" aria-label="Previous">
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
                                <li><a href="drawList?pageNumber=${obj}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}">${obj}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${page.hasNextPage}">
                            <li>
                                <a href="drawList?pageNumber=${page.pageNum+1}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}" aria-label="Next">
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
    <div id="subForm" class="hidden">
        <div class="control-group" style="text-align:center;">
            <span id="drawErrorr" class="red"></span>
        </div>
        <div class="control-group">
            <label class="control-label">交易手数</label>
            <div class="controls">
                <input type="text" placeholder="请输入流水号" class="input-xlarge" id="countsNum"/>
                <p class="help-block red" id="countsNumError" style="display: none;">请输入交易手数</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">提现金额</label>
            <div class="controls">
                <input type="text" placeholder="请输入流水号" class="input-xlarge" id="money"/>
                <p class="help-block red" id="moneyError" style="display: none;">请输入提现金额</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">银行单号</label>
            <div class="controls">
                <input type="text" placeholder="请输入银行单号" class="input-xlarge" id="bankNo"/>
                <p class="help-block red" id="bankNoError" style="display: none;">请输入银行单号</p>
            </div>
        </div>
    </div>
</body>
</html>
