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
    <link rel="stylesheet" href="${path}/static/Ace/css/bootstrapValidator.css" />
    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
    <script type="text/javascript" src="${path}/static/script/resize/iframeResizer.contentWindow.js" ></script>
    <script type="text/javascript" src="${path}/static/script/bootstrap.min.js" ></script>
    <script type="text/javascript" src="${path}/static/script/bootstrapValidator.js" ></script>
    <script type="text/javascript" src="${path}/static/script/admin/user/add.js" ></script>
</head>
<body class="no-skin" style="background-color: white;">
<div class="row">
    <div class="col-xs-12">
        <form class="form-horizontal" role="form" action="addSysUser" id="form" name="form" method="post">
            <div class="form-group">
                <label for="username" class="col-xs-12 col-sm-3 control-label no-padding-right">账号</label>
                <div class="col-xs-12 col-sm-5">
                    <span class="block input-icon input-icon-right">
                        <input type="text" id="username" name="username" class="width-100" placeholder="账号"/>
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-xs-12 col-sm-3 control-label no-padding-right">密码</label>
                <div class="col-xs-12 col-sm-5">
                    <span class="block input-icon input-icon-right">
                        <input type="text" id="password" name="password" class="width-100" placeholder="密码"/>
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label for="available" class="col-xs-12 col-sm-3 control-label no-padding-right">是否可用</label>
                <div class="col-xs-12 col-sm-5">
                    <span class="block input-icon input-icon-right">
                        <input type="hidden" id="available" name="available" value="1"/>
                        <input id="check" name="check" checked="checked" class="ace ace-switch ace-switch-4 btn-empty" type="checkbox"/>
                        <span class="lbl" style="margin-top: 5px;"></span>
                    </span>
                </div>
            </div>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="submit" id="sub">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        提交
                    </button>
                    &nbsp; &nbsp; &nbsp;
                    <button class="btn" type="button" id="reset" onclick="history.back();">
                        <i class="ace-icon fa fa-undo bigger-110"></i>
                        取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>