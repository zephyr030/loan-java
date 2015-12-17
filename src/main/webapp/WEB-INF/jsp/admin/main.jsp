<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>居间商管理平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="User login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" href="${path}/static/Ace/css/bootstrap.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/font-awesome.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/ace-fonts.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/ace.css" />
    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
    <script type="text/javascript" src="${path}/static/script/resize/iframeResizer.js" ></script>
    <script type="text/javascript" src="${path}/static/script/My97DatePicker/WdatePicker.js" ></script>
    <script type="text/javascript" src="${path}/static/script/artDialog4.1.7/jquery.artDialog.js?skin=default" ></script>
    <script type="text/javascript" src="${path}/static/script/Main/main.js" ></script>
</head>

<body class="no-skin">
<div class="navbar" id="navbar">
    <div class="navbar-header pull-left">
        <a href="main" class="navbar-brand">
            <small>
                <i class="fa fa-leaf"></i>
                居间商管理平台
            </small>
        </a>
    </div>
    <div class="navbar-buttons navbar-header pull-right" role="navigation">
        <ul class="nav ace-nav">
            <li class="light-blue">
                <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                    <img class="nav-user-photo" src="${path}/static/Ace/avatars/avatar4.png" alt="Jason's Photo" />
                        <span class="user-info">
                            <small>Welcome,</small>
                            ${user.username}
                        </span>
                    <i class="ace-icon fa fa-caret-down"></i>
                </a>

                <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                    <li>
                        <a href="logout">
                            <i class="ace-icon fa fa-power-off"></i>
                            Logout
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>

<div class="main-container" id="main-container">
    <div class="sidebar responsive" id="sidebar">
        <ul class="nav nav-list" style="top: 0px;">
            <c:if test="${user.username == 'admin'}">
                <li>
                    <a href="javascript:;" class="dropdown-toggle" onclick="url('sysUserList')">
                        <i class="menu-icon fa fa-users"></i>
                    <span class="menu-text">
                        账户管理
                    </span>
                    </a>
                </li>
            </c:if>
            <li>
                <a href="javascript:;" class="dropdown-toggle" onclick="url('userList')">
                    <i class="menu-icon fa fa-user"></i>
                    <span class="menu-text">
                        会员管理
                    </span>
                </a>
            </li>
            <li>
                <a href="javascript:;" class="dropdown-toggle" onclick="url('rechargeList')">
                    <i class="menu-icon fa fa-list-alt"></i>
                    <span class="menu-text">
                        充值列表
                    </span>
                </a>
            </li>
            <li>
                <a href="javascript:;" class="dropdown-toggle" onclick="url('rechargeOverList')">
                    <i class="menu-icon fa fa-list"></i>
                    <span class="menu-text">
                        充值上账信息
                    </span>
                </a>
            </li>
            <li>
                <a href="javascript:;" class="dropdown-toggle" onclick="url('drawList')">
                    <i class="menu-icon fa fa-credit-card"></i>
                    <span class="menu-text">
                        提现列表
                    </span>
                </a>
            </li>
            <li>
                <a href="javascript:;" class="dropdown-toggle" onclick="url('drawOverList')">
                    <i class="menu-icon fa fa-money"></i>
                    <span class="menu-text">
                        提现放款信息
                    </span>
                </a>
            </li>
        </ul>
    </div>

    <div class="main-content">
        <div class="breadcrumbs"></div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12" id="iframeWindow">
                    <iframe id="iframe_id" width="100%" scrolling="no"  frameborder="0" name="iframe_id" src=""></iframe>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-inner">
                <div class="footer-content">
                    <span class="bigger-120"><span class="blue bolder">Ace</span>Application &copy; 2013-2014</span>
                    &nbsp; &nbsp;
                    <span class="action-buttons">
                        <a href="javascript:;">
                            <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                        </a>

                        <a href="javascript:;">
                            <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                        </a>

                        <a href="javascript:;">
                            <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                        </a>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>