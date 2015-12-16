<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.TreeMap" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    TreeMap<String,Object> treeMap = (TreeMap)request.getAttribute("params");
%>
    <%@ include file="/WEB-INF/jsp/common/import-js.jsp" %>
</head>
<body>
<div class="page-container row-fluid" style="text-align: center;height: 400px;line-height: 400px;font-size: 30px;">
    页面正在跳转，请稍后......
</div>
<div style="display: none">
    <form action="${url}" method="${method}" id="payAction" >
        <%
            if(treeMap != null){
                for (Entry<String, Object> entry : treeMap.entrySet()) {
        %>
        <input type="text" name="<%=entry.getKey() %>" value="<%=entry.getValue() %>"/><br/>
        <%}}%>
    </form>
</div>
</body>


<script type="text/javascript">

    $(document).ready(function() {
       $("#payAction").submit();
    });
</script>
</html>

