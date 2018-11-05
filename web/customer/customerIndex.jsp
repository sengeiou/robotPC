<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/customer/header.jsp" %>
<%@ include file="/customer/menu.jsp" %>
<html>
<%
    String WXuin = request.getParameter("wxuin");
%>
<head>
    <meta charset="utf-8">
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="msapplication-tap-highlight" content="no">
    <title></title>
    <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/font-awesome/4.6.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/customerService/service.css" />
    <link rel="stylesheet" href="../css/customerService/index.css" />
    <link rel="stylesheet" href="../css/assets/layui/css/layui.css" />
    <script type="text/javascript" src="../js/commonality/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="../js/commonality/layui/layui.js"></script>
    <style>
        #test1 #headId2{
            width: 16px;
            height: 16px;
            display: inline-block;
            overflow: hidden;
            font-size: 68px;
            position: absolute;
            right: 0;
            top: 0;
            opacity: 0;
            filter: alpha(opacity=0);
            cursor: pointer;
        }
        .product #headId3{
            width: 116px;
            height: 116px;
            display: inline-block;
            overflow: hidden;
            font-size: 68px;
            position: absolute;
            margin-left: 10px;
            opacity: 0;
            filter: alpha(opacity=0);
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div class="main">
            <!-- 微信内容区域 -->
            <div class="main_inner">
                <div class="panel give_me">
                    <!-- 搜索框 -->
                    <div class="search_bar" id="search_bar">
                        <img id="search_img" src="../image/dd.jpg"/>
                        <input  class="frm_search ng-isolate-scope ng-pristine ng-valid" id='txt' type="text"  placeholder="搜索好友昵称">
                        <ul class="drop"></ul>
                    </div>
                    <!-- 好友列表 -->
                    <div class="s-side">
                        <ul>
                            <!--这部分是导航栏信息。-->
                            <li class="first">
                                <div class="d-firstNav s-firstNav clearfix" >
                                    <span>全部用户</span>
                                    <span id="s-fir"></span>
                                    <i class="fa fa-caret-right fr iconRotate"></i>
                                </div>
                                <ul class="d-firstDrop s-firstDrop" style="background: #ebecf0;">
                                    <%--好友的--%>
                                    <div class="retsList"></div>
                                    <div class="uop">

                                    </div> <%--群列表--%>
                                </ul>
                            </li>
                        </ul>
                    </div>

                </div>

                <!-- 素材 -->
                <div class="matter-div">
                    <div class="matter-case"> 素材</div>
                    <div class="allGoods_list">
                        <ul>

                        </ul>
                    </div>
                </div>
                <!-- 聊天区 -->
                <div style="height:100%;" class="ng-scope">
                    <div id="chatArea"  class="box chat ng-scope chatRoom">
                        <!-- 头部提醒和谁聊天 -->
                        <div class="box_hd">
                            <div id="chatRoomMembersWrap"></div>
                            <div class="title_wrap">
                                <div class="title poi">
                                    <a class="title_name ng-binding" id="headNameId" >文件传输助手</a>
                                </div>
                            </div>
                        </div>

                        <div class="scroll-wrapper box_bd chat_bd scrollbar-dynamic" style="position: absolute;">
                            <div class="box_bd chat_bd scrollbar-dynamic scroll-content scroll-scrolly_visible" id="cont" style="margin-bottom: 0px; margin-right: 0px; height: 600px;">
                                <div class="ng-scope">
                                    <div  class="top-placeholder ng-scope" style="height: 100%;"></div>
                                    <div  class="ng-scope">
                                        <div class="clearfix">
                                            <div class="onself" style="overflow: hidden;">
                                                <!-- 系统消息 -->

                                                <!-- 别人的信息 -->
                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>
                                    <!-- 发送消息的地方 -->
                                    <div class="box_ft ng-scope" >
                                        <div class="toolbar" id="tool_bar">
                                            <a class="web_wechat_face"  href="javascript:;" title="暂未开放">
                                                <img src="../image/face.png" />
                                            </a>
                                            <a class="web_wechat_screencut"  href="javascript:;" title="暂未开放">
                                                <img src="../image/screenshot.png" />
                                            </a>
                                            <a  class="web_wechat_pic js_fileupload ng-isolate-scope webuploader-container" id="test1" href="javascript:;" title="图片和文件">
                                                <img src="../image/file.png" />
                                                <input id="headId2" name="headPhone" type="file"  onchange="headImg()"/>

                                            </a>
                                        </div>
                                        <div class="content ng-isolate-scope" id="ng-scope"  track-opt="{target:'发送框',keys:['enter','backspace','blankspace']}">
                                            <pre id="editArea"  class="flex edit_area ng-isolate-scope" contenteditable="true"  contenteditable-directive=""></pre>
                                        </div>
                                        <div class="action">
                                            <a  class="desc ng-scope" href="javascript:;" title="暂未开放">结束会话</a>
                                            <a class="btn btn_send" onclick="sendMessage()" href="javascript:;" >发送</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
</body>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
    layui.use('jquery', function(){
        var $ = layui.jquery;
        var submit = function(){
            return false;
        };
        $('#test').on('submit', function(){
            return false
        });
        $('#test').on('submit', function(){
            return true
        });
    });

    // 左侧下拉框
   var img='';
   var res='';
    var nickName='';//用户名称
    var yiName ='';
    var WXuin = <%=WXuin%>;
    window.onload =function(){
        $.ajax({
            //几个参数需要注意一下
            type: "get",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "${ctx}/wxindex?method=getWXContact&WXuin="+WXuin ,//url
            async : false,
            //  data: {loginName:loginName,pwd:pwd},
            success:function(data){
                var arr3 = new Array();
                    res = data.result.memberListJson.MemberList;
                    var getList="";//好友
                    var getListt="";//群
                    for(var i=0; i<res.length; i++){
                        var userName = res[i].UserName;//用户id
                        var RemarkNm = res[i].RemarkName;//用户备注
                        img = res[i].HeadImgUrl;//用户头像
                        arr3.push(res[i].NickName);     // 用户名称
                        var VerifyFlag = res[i].VerifyFlag; //公众号
                        //群聊列表
                        if("@@" == userName.substr(0,2) && VerifyFlag == 0 ){
                                getListt += '<li class="s-thirdItem"style="background-color: #FFFFFF;">';
                                getListt += '<div class="layui-form-item" style="margin-bottom: 0px;" >';
                                getListt += '<div class="layui-input-block" style="margin-left: 15px;color:#333333;height: 46px;line-height: 46px;">';
                                getListt += '<input  type="radio" class="radioId" id="radioId'+i+'" value= "'+res[i].UserName+'" name="radioName" lay-skin="primary" title="" onclick="getUserName()">';
                                getListt += '<span id="nickNameId">'+res[i].NickName+'(群)</span>';
                                getListt += '<i id="nic"></i>';
                                getListt += '</div>';
                                getListt += '</div>';
                                getListt += '</li>';
                        }
                        //好友列表
                        if("@@" != userName.substr(0,2) && VerifyFlag != 8 &&  VerifyFlag != 56  &&  VerifyFlag != 24){
                                getList += '<li class="s-thirdItem"style="background-color: #FFFFFF;">';
                                getList += '<div class="layui-form-item" style="margin-bottom: 0px;" >';
                                getList += '<div class="layui-input-block" style="margin-left: 15px;color:#333333;height: 46px;line-height: 46px;">';
                                getList += '<input  type="radio" class="radioId" id="radioId'+i+'" value= "'+res[i].UserName+'" name="radioName" lay-skin="primary" title="" onclick="getUserName()">';
                                if(RemarkNm !='' && RemarkNm != undefined ){
                                    getList += '<span id="nickNameId">'+RemarkNm+'</span>';
                                }else {
                                    getList += '<span id="nickNameId">'+res[i].NickName+'</span>';
                                }
                                getList += '<i id="nic"></i>';
                                getList += '</div>';
                                getList += '</div>';
                                getList += '</li>';

                        }
                    }
                    $('.s-firstDrop .retsList').html(getList);
                    $('.s-firstDrop .uop').html(getListt);
                    setInterval(alertFunc, 2000);

                    // 搜索框搜关键字
                     var input = document.getElementById('search_bar').getElementsByTagName("input")[0];
                     var ul = document.getElementById('search_bar').getElementsByTagName('ul')[0];
                     ul.style.display = "none";
                     input.onkeyup = function() {
                         remove();
                         var getList1="";//好友
                         var getListt1="";//群
                        for (var index = 0; index < res.length; index++) {
                            if (res[index].NickName.indexOf(this.value) > -1) {
                                var userName = res[index].UserName;
                                if("@@" == userName.substr(0,2)){
                                    //群聊列表
                                    getListt1 += '<li class="s-thirdItem"style="background-color: #FFFFFF;">';
                                    getListt1 += '<div class="layui-form-item" style="margin-bottom: 0px;" >';
                                    getListt1 += '<div class="layui-input-block" style="margin-left: 15px;color:#333333;height: 46px;line-height: 46px;">';
                                    getListt1 += '<input  type="radio" class="radioId" id="radioId" value= "'+res[index].UserName+'" name="radioName" lay-skin="primary" title="" onclick="getUserName()">';
                                    getListt1 += '<span id="nickNameId">'+res[index].NickName+'(群)</span>';
                                    getListt1 += '</div>';
                                    getListt1 += '</div>';
                                    getListt1 += '</li>';
                                }
                                if("@@" != userName.substr(0,2)) {
                                    //好友列表
                                    getList1 += '<li class="s-thirdItem"style="background-color: #FFFFFF;">';
                                    getList1 += '<div class="layui-form-item" style="margin-bottom: 0px;" >';
                                    getList1 += '<div class="layui-input-block" style="margin-left: 15px;color:#333333;height: 46px;line-height: 46px;">';
                                    getList1 += '<input  type="radio" class="radioId" id="radioId" value= "'+res[index].UserName+'" name="radioName" lay-skin="primary" title="" onclick="getUserName()">';
                                    getList1 += '<span id="nickNameId">'+res[index].NickName+'</span>';
                                    getList1 += '</div>';
                                    getList1 += '</div>';
                                    getList1 += '</li>';
                                }
                            }
                        }
                         remove();
                         $('.s-firstDrop .retsList').html(getList1);
                         $('.s-firstDrop .uop').html(getListt1);

                    }

                    function create(arr_1) {
                        ul.style.display = "block";
                        for (var index = 0; index < arr_1.length; index++) {
                            var li = document.createElement("li");
                            li.innerHTML = "<a href='javascript:;'>" + arr_1[index] + "</a>";
                            ul.appendChild(li);
                        }
                    }

                    function remove() {
                        ul.style.display = "none";
                        for (var index = ul.childNodes.length - 1; index>= 0; index--) {
                            ul.removeChild(ul.childNodes[index]);
                        }
                    }

            },
            error:function(){
                alert("系统繁忙，请重试");
            },
        });
        fodder();
    }

    // 获取好友列表----点击其中一个好友发生的事件
    var toUserName='';
    function getUserName() {
        toUserName = $("input[name='radioName']:checked").val();//点击列表中其中一个好友的toUserName
        var zz = $(":radio:checked + span").text();
        $("#headNameId").text(zz);
        var getReceive ="";
        getReceive+='<div ng-switch-default="" class="message ng-scope you" >';
        getReceive+='</div>';
        $('.onself').html(getReceive);

        //点击好友时消息红点消失
        var getRet = res;
        for(var i=0;i<getRet.length;i++){
            if(getRet[i].UserName == partyId){
                var sf = getRet[i].UserName;
                if(sf == partyId){
                    $('#radioId'+i).parent(".layui-input-block").find("#red_mark").remove();//好友列表
                    $('#s-fir').remove();//全部用户
                }

            }


        }


    }

    // 聊天窗口----给别人发送消息
    function sendMessage() {
        var cont = document.getElementById('cont');
        var message = document.getElementById('editArea').innerHTML;
        var ToUserName = toUserName;
        var meImag = img;
        var getHtml ="";
        getHtml+='<div  class="message ng-scope me first">';
        getHtml+='<div  class="message_system ng-scope">';
        getHtml+='<div  class="content ng-binding ng-scope">18:02</div>';
        getHtml+='</div>';
        getHtml+='<img class="avatar" src='+meImag+' >';
        getHtml+='<div class="content"> ';
        getHtml+='<div class="bubble js_message_bubble ng-scope bubble_primary right">';
        getHtml+='<div class="bubble_cont ng-scope">';
        getHtml+='<div class="plain">';
        getHtml+='<pre class="js_message_plain ng-binding">'+message+'</pre>';
        getHtml+='</div>';
        getHtml+='</div>';
        getHtml+='</div>';
        getHtml+='</div>';
        getHtml+='</div>';
        $('.onself').append(getHtml)

        $.ajax({
            url: "${ctx}/wxindex?method=sendMessage&WXuin="+WXuin,
            type: "get",
            dataType: "json",
            data: {
                "message":message,
                "ToUserName":ToUserName
            },
            success: function (data) {
                 var message = $("#editArea").remove().innerHTML;
                 var getphtml="";
                 getphtml+='<pre id="editArea"  class="flex edit_area ng-isolate-scope" contenteditable="true"  contenteditable-directive=""></pre>';
                 $('#ng-scope').html(getphtml);
            },
            error: function (data) {
                alert(data.result);
            }
        });
        cont.scrollTop = cont.scrollHeight;
    }


    // 查询别人发的消息
    var receiveInformation='';//别人发的消息
    var partyId = '';
    var partyName = '';
    var receiveImage = "";
    var msgType = "";
    var url = "";
    var msgId = "";
    var skey="";
    function alertFunc() {
        var receiveRes =res;
        for (var i=0;i<receiveRes.length;i++){
            // 别人的头像
            receiveImage = receiveRes[i].HeadImgUrl;
        }
        $.ajax({
            url: "${ctx}/wxindex?method=getWXMessage&WXuin="+WXuin,
            type: "get",
            async : false,
            dataType: "json",
            data: {
            },
            // processData: false,
            success: function (data) {
                // 收到别人的信息
                if(data.result !='' && data.result !=null){
                    partyId = data.result.FromUserName;//从谁那里发的
                    receiveInformation = data.result.message;
                    msgType = data.result.msgType;
                    url = data.result.url;
                    skey = data.result.skey;
                    console.log("skey:::::::",skey)
                    msgId = data.result.msgId;
                }
                console.log(receiveInformation,'消息')
                //选择其中一个好友加载一个聊天框
                var rtList = res;
                var getReceive ='';
                for(var i=0; i<rtList.length; i++){
                    if(receiveInformation != undefined && receiveInformation != ''){
                        receiveImage = url+receiveImage;
                        if(toUserName == partyId ){
                            if(msgType == 3){
                                getReceive += '<div ng-switch-default="" class="message ng-scope you" >';
                                getReceive += '<img class="avatar" src=' + receiveImage + ' >';
                                getReceive += '<div class="content">';
                                getReceive += '<div class="bubble js_message_bubble ng-scope bubble_default left">';
                                getReceive += '<div class="bubble_cont ng-scope">';
                                getReceive += '<div class="plain">';
                                getReceive += '<img src=${ctx}/wxindex?method=getImgMessage&WXuin='+WXuin+'&msgId='+msgId+'&skey='+skey+' />';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                $('.onself').append(getReceive);
                                receiveInformation = '';
                            }else {
                                getReceive += '<div ng-switch-default="" class="message ng-scope you" >';
                                getReceive += '<img class="avatar" src=' + receiveImage + ' >';
                                getReceive += '<div class="content">';
                                getReceive += '<div class="bubble js_message_bubble ng-scope bubble_default left">';
                                getReceive += '<div class="bubble_cont ng-scope">';
                                getReceive += '<div class="plain">';
                                getReceive += '<pre class="js_message_plain ng-binding">' + receiveInformation + '</pre>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                getReceive += '</div>';
                                $('.onself').append(getReceive);
                                receiveInformation = '';
                            }
                        }else {
                            if(rtList[i].UserName == partyId){
                            for(var i=0;i<rtList.length;i++){
                                        var sf = rtList[i].UserName;
                                        var useList = '';
                                        if(sf == partyId){
                                            useList +='<div id="red_mark"></div>';
                                            $('#radioId'+i).parent(".layui-input-block").find("#nic").html(useList);//好友列表
                                            $('#s-fir').html(useList);//全部用户
                                        }

                                }


                            }
                        }

                    }


                }
            },
            error: function (data) {
            }
        });
    }

    //物理键enter
    document.onkeydown = function (e) {
        if (!e) e = window.event;
        if ((e.keyCode || e.which) == 13) {
            sendMessage();
        }
    }
    // 素材
    function fodder(){
        $.ajax({
            url: "http://10.1.0.18:8056/poseidon/poster",
            type: "GET",
            dataType: "jsonp", //指定服务器返回的数据类型
            data: {
                method:'selectPosterCategory',
                categoryId:'1',
                page:'1',
                limit:'3',
                url_type:'poster'
            },
            success: function(data) {
                var resList = data.result.rs;
                resListHtml="";
                for(var i=0;i<resList.length;i++){
                    var ecplain = resList[i].picture_name;
                    var resImg = resList[i].image;
                    resListHtml+='<li class="product">';
                    resListHtml+='<a href="javascript:;" title='+ecplain+'>';
                    resListHtml+='<input id="headId3"  class="rdo_test"  name="headPhone" type="radio"/>';
                    resListHtml+='<img id="imgId" src='+resImg+'>';
                    resListHtml+='</a>';
                    resListHtml+='</li>';
                }
                $('.allGoods_list ul').html(resListHtml);

                $(".rdo_test").on("click",function(){
                    var resImage =toUserName;
                    var formData = new FormData();
                    var s=$(this).next().attr("src");
                    var fileObject = s.files[0];
                    formData.append("headPhone", fileObject);
                    formData.append("ToUserName",resImage);
                    formData.append("WXuin",WXuin);
                    $.ajax({
                        url: "${ctx}/wxindex?method=sendImageMessage",
                        type: "POST",
                        dataType: "json",
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            console.log("sendImg",data.result)
                        },
                        error: function (data) {
                            console.log("sendImg",data.result)
                        }
                    });
                });
            }
        })

    }




    //发送图片
    function headImg() {
        var v =  $('#headId2').val();
        var resImage =toUserName;
        var formData = new FormData();
        var img_file = document.getElementById("headId");
        if (undefined == img_file) {
            img_file = document.getElementById("headId2");
        }
        var fileObject = img_file.files[0];
        formData.append("headPhone", fileObject);
        formData.append("ToUserName",resImage);
        formData.append("WXuin",WXuin);
        $.ajax({
            url: "${ctx}/wxindex?method=sendImageMessage",
            type: "POST",
            dataType: "json",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                console.log("sendImg",data.result)
            },
            error: function (data) {
                console.log("sendImg",data.result)
            }
        });
    }

</script>
<script type="text/javascript" src="../js/customerService/index.js"></script>
<script type="text/javascript" src="../js/customerService/service.js"></script>
</html>