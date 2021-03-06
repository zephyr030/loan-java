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
    <script type="text/javascript" src="${path}/static/script/recharge/recharge.js" ></script>
</head>
<body class="no-skin" style="background-color: white;">
    <div class="row">
        <div class="col-xs-12">
            <form action="rechargeList" id="form" name="form" method="post">
                <select id="type" name="type" style="height: 34px;">
                    <option value="0" ${type==0?'selected':''}>全部</option>
                    <option value="1" ${type==1?'selected':''}>充值</option>
                    <option value="2" ${type==2?'selected':''}>转账</option>
                </select>
                <input type="text" name="flow" value="${flow}" style="margin-left: 12px;"  placeholder="银行流水号"/>
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
                <input type="text" id="startTime" name="startTime" size="16" class="form_datetime" value="${startTime}"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
                />
                <input type="text" id="endTime" name="endTime" size="16" class="form_datetime" value="${endTime}"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
                />
                <button type="submit" id="search" class="btn btn-white btn-info btn-round" style="margin-left: 12px;margin-bottom: 5px;">
                    <i class="ace-icon fa fa-search bigger-120 green"></i>
                    查询
                </button>
                <a href="exportRechargeList?type=${type}&flow=${flow}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}">
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
                        充值列表
                    </h5>
                </div>

                <!-- table内容 -->
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <table class="table table-striped table-bordered table-hover">
                            <thead class="thin-border-bottom">
                            <tr>
                                <th><i></i>编号</th>
                                <th><i></i>充值方式</th>
                                <th><i></i>第三方流水号</th>
                                <th><i></i>账号</th>
                                <th><i></i>姓名</th>
                                <th><i></i>交易银行</th>
                                <th><i></i>银行账号</th>
                                <th><i></i>手机号</th>
                                <th><i></i>充值金额</th>
                                <th><i></i>充值时间</th>
                                <th><i></i>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${page.list}" var="obj">
                                    <tr>
                                        <th>${obj.id}</th>
                                        <td>${obj.recTypes}</td>
                                        <td>${obj.flowNo}</td>
                                        <td>${obj.account}</td>
                                        <td>${obj.customername}</td>
                                        <td>${obj.rechargeBankName}</td>
                                        <td>${obj.cardnumber}</td>
                                        <td>${obj.mobile}</td>
                                        <td>${obj.amount}</td>
                                        <td>${obj.recTime}</td>
                                        <td>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <a class="blue" href="javascript:;" onclick="isSend(${obj.id},'${obj.flowNo}',${obj.amount},${obj.user_id})">到账</a>
                                                <a class="red" href="javascript:;" onclick="refused(${obj.id})">拒绝</a>
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
                                <a href="rechargeList?pageNumber=${page.pageNum-1}&type=${type}&flow=${flow}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}" aria-label="Previous">
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
                                <li><a href="rechargeList?pageNumber=${obj}&type=${type}&flow=${flow}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}">${obj}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${page.hasNextPage}">
                            <li>
                                <a href="rechargeList?pageNumber=${page.pageNum+1}&type=${type}&flow=${flow}&account=${account}&name=${name}&mobile=${mobile}&smoney=${smoney}&emoney=${emoney}&startTime=${startTime}&endTime=${endTime}" aria-label="Next">
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
            <span id="rechargeErrorr" class="red"></span>
        </div>
        <div class="control-group">
            <label class="control-label">流水号</label>
            <div class="controls">
                <input type="text" placeholder="请输入流水号" class="input-xlarge" id="rechargeNo"/>
                <p class="help-block red" id="fowError" style="display: none;">请输入流水号</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">到账时间</label>
            <div class="controls">
                <input type="text" class="form-control" placeholder="请输入名称" readonly id="rechargeTime"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d'})"
                />
                <p class="help-block red" id="timeError" style="display: none;">请选择时间</p>
            </div>
        </div>
    </div>
    <div id="remarkBox" class="hidden">
        <div class="control-group" style="text-align:center;">
            <span id="remarkErrorr" class="red"></span>
        </div>
        <div class="control-group">
            <label class="control-label">备注</label>
            <div class="controls">
                <input type="text" placeholder="备注" class="input-xlarge" id="remark"/>
            </div>
        </div>
    </div>
</body>
</html>
