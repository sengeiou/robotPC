<%--
  Created by IntelliJ IDEA.
  User: sdmin
  Date: 2018/10/15
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/customer/header.jsp" %>
<%@ include file="/customer/menu.jsp" %>
<html>
<head>
    <title>Title</title>
    <%--<script  type="text/javascript" src="../layui/layui.js?t=1515376178739"></script>--%>
    <script  type="text/javascript" src="../common/third-part/jquery-1.11.3.min.js"></script>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <style>
        .width-1180 {
            width: 1180px;
            margin: 0 auto;
            position: relative;
        }
        .login_box {
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -190px;
            margin-top: 15px;
            border-radius: 4px;
            -moz-border-radius: 4px;
            -webkit-border-radius: 4px;
            background-color: #fff;
            width: 431px;
            height: 540px;
            box-shadow: 0 2px 10px #999;
            -moz-box-shadow: #999 0 2px 10px;
            -webkit-box-shadow: #999 0 2px 10px;
        }
        .login_box .qrcode {
            position: relative;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="width-1180">
    <div class="login_box">
        <div class="qrcode">
            <img id="loginId" />
            <div>
                <p>请用微信扫码登录</p>
            </div>
        </div>
    </div>

</div>
</body>
<script type="text/javascript">
    window.onload =function(){
        $.ajax({
            //几个参数需要注意一下
            type: "get",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "${ctx}/wxindex?method=wxLogin" ,//url
            async : false,
            //  data: {loginName:loginName,pwd:pwd},
            success:function(data){
                if(data.code==0){
                    $("#loginId").attr("src","https://login.weixin.qq.com/qrcode/"+data.result);
                }
            },
            error:function(){
                alert("系统繁忙，请重试");
            },
        });
    }

        setTimeout(function() {
            $.ajax({
                //几个参数需要注意一下
                type: "get",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: "${ctx}/wxindex?method=checkLonginStatus" ,//url
                async : false,
                success:function(data){
                    if(data.code==0){
                        window.location.href="${pageContext.request.contextPath}/customer/customerIndex.jsp?wxuin="+data.result
                    }
                },
                error:function(){
                    alert("系统繁忙，请重试");
                },
            });
        },5000)
</script>

<%@ include file="/customer/footer.jsp" %>
</html>
