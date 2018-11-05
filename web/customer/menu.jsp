<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String menuIndex = request.getParameter("menuIndex");
%>

<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="test">

            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="javascript:;">工作导航</a>
                <dl class="layui-nav-child">
                    <%--<dd id="dd1"><a href="${ctx}/goods/goodsSourceList.jsp?menuIndex=1">工作导航</a></dd>--%>
                    <dd id="dd2"><a href="${ctx}/customer/customerOnline.jsp?menuIndex=2">在线客服</a></dd>
                   <%-- <dd id="dd3"><a href="">用户中心</a></dd>
                    <dd id="dd4"><a href="">群发消息</a></dd>
                    <dd id="dd5"><a href="">话术管理</a></dd>--%>
                    <%--<dd><a href="${ctx}/goods/goodsCategoryAdd.jsp">添加商品分类</a></dd>--%>
                   <%-- <dd id="dd6"><a href="">留言管理</a></dd>
                    <dd id="dd7"><a href="" >财务管理</a></dd>
                    <dd id="dd8"><a href="">设置</a></dd>--%>
                    <%--<dd id="dd11"><a href="${ctx}/goods/demo1.jsp?menuIndex=11">测试测试</a></dd>--%>

                    <%--<dd><a href="${ctx}/goods/index">自提点列表</a></dd>--%>
                    <%--<dd><a href="${ctx}/goods/index">添加自提点</a></dd>--%>
                    <%--<dd><a href="${ctx}/goods/areaList.jsp">地区管理</a></dd>--%>
                </dl>
            </li>
           <%-- <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="javascript:;">拼多多商品管理</a>
                <dl class="layui-nav-child">
                    <dd id="dd13"><a href="${ctx}/goods/pddGoodsList.jsp">拼多多商品管理</a></dd>
                </dl>
            </li>--%>
        </ul>
    </div>
</div>

<script>
    var menuIndex = <%=menuIndex%>;
    $('#dd' + menuIndex).addClass("layui-this");
</script>

