<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="User login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" href="${path}/static/Ace/css/bootstrap.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/font-awesome.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/ace-fonts.css" />
    <link rel="stylesheet" href="${path}/static/Ace/css/ace.css" />

    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
    <script type="text/javascript" src="${path}/static/script/artDialog4.1.7/jquery.artDialog.js?skin=default" ></script>
    <script type="text/javascript" src="${path}/static/script/Login/login.js" ></script>
</head>


<body class="login-layout blur-login">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center">
                        <h1>
                            <i class="ace-icon fa fa-leaf green"></i>
                            <span class="red">Ace</span>
                            <span class="white" id="id-text2">Application</span>
                        </h1>
                        <h4 class="blue" id="id-company-text">&copy; Company Name</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        Please Enter Your Information
                                    </h4>

                                    <div class="space-6"></div>
                                        <fieldset>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input type="text" class="form-control" placeholder="Username" id="username" name="username"/>
                                                    <i class="ace-icon fa fa-user"></i>
                                                </span>
                                            </label>

                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input type="password" class="form-control" placeholder="Password" id="password" name="password"/>
                                                    <i class="ace-icon fa fa-lock"></i>
                                                </span>
                                            </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <button type="button" class="width-100 pull-right btn btn-sm btn-primary" id="login">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">Login</span>
                                                </button>
                                            </div>
                                            <div class="space-4"></div>
                                        </fieldset>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>