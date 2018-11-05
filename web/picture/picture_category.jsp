<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/common.jsp" %>
<%@ include file="/picture/picture_menu.jsp" %>

<script>
    var locationPage;
    //JavaScript代码区域
    layui.use(['element', 'laydate', 'table'], function () {
        var element = layui.element;
        var laydate = layui.laydate;
        var table = layui.table;

        table.render({
            elem: '#pictureTable'
            , url: '${pageContext.request.contextPath}/picture?method=getPictureCategoryInfo'
            //,width: 1900
            , height: 580
            , cols: [[
                {field: 'id', width: 150, title: '编号', align: 'center', fixed: 'left', sort: true}
                , {field: 'category_name', width: 150, title: '分类名称', align: 'center'}
                , {field: 'uploader', width: 250, title: '上传人', align: 'center'}
                , {field: 'update_time', width: 300, title: '编辑时间', align: 'center', templet: "#editTimeTmpl"}
                , {field: 'create_time', width: 300, title: '创建时间', align: 'center', templet: "#createTimeTmpl"}
                , {fixed: 'right', title: '操作', width: 550, align: 'center', toolbar: "#barDemo"}
            ]]
            , id: 'listTable'
            , response: {
                statusName: 'success'
                , statusCode: 1
                , msgName: 'errorMessage'
                , countName: 'total'
                , dataName: 'rs'
            }
        });

        $("#insert").on('click', function () {

            layer.open({
                type: 1
                , title: '新增图片分类'
                , id: 'insertPicture'
                , area: ['600px', '600px']
                , content: $('#PictureAdd')
                , btn: ['新增', '取消']
                , btnAlign: 'c' //按钮居中
                , shade: 0 //不显示遮罩
                , yes: function (data) {
                    var categoryName = $("#category_name").val();
                    insertPicture(categoryName);

                }
                , btn2: function () {
                    layer.closeAll();
                }
            });

        });

        function insertPicture(categoryName) {

            if (categoryName == "") {
                layer.msg('请输入广告链接名称');
                return false;
            }

            $.ajax({
                type: "get",
                async: false, // 同步请求
                cache: true,// 不使用ajax缓存
                contentType: "application/json",
                url: "${ctx}/advertising?method=addAdvertUrl",
                data: {
                    "advertlink_name": advertlink_name,
                    "url_link": url_link,
                    "remarks": remarks,
                    "category": category
                },

                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        advert_url_id = data.result.ids;
                        layer.msg('编辑成功', {time: 1000}, function () {
                            window.location.reload();
                        });


                    } else {
                        layer.msg("异常");
                    }
                },
                error: function () {
                    layer.alert("错误");
                }
            });

            return false;
        }

        //批量启用
        $('#status_open').on('click', function () {
            var checkStatus = table.checkStatus('listTable');
            var data = checkStatus.data;
            var selectCount = data.length;
            if (selectCount == 0) {
                layer.msg("请选择一条数据！");
                return false;
            }
            ;
            if (selectCount >= 1) {
                layer.confirm('确定开启广告位吗？', function (index) {
                    layer.close(index);
                    var ids = new Array(selectCount);
                    for (var i = 0; i < selectCount; i++) {
                        ids[i] = checkStatus.data[i].id;
                    }

                    $.ajax({
                        type: "get",
                        async: false, // 同步请求
                        cache: true,// 不使用ajax缓存
                        contentType: "application/json",
                        url: "${ctx}/advertising",
                        data: "method=updatePositionStatus&status=1&ids=" + ids,
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                layer.msg("操作成功");
                                table.reload("listTable");
                            } else {
                                layer.msg("异常");
                            }
                        },
                        error: function () {
                            layer.alert("错误");
                        }
                    });
                });
            }
            return false;
        });

        //批量停用
        $('#status_shut').on('click', function () {
            var checkStatus = table.checkStatus('listTable');
            var data = checkStatus.data;
            var selectCount = data.length;
            if (selectCount == 0) {
                layer.msg("请选择一条数据！");
                return false;
            }
            ;
            if (selectCount >= 1) {
                layer.confirm('确定停用广告位吗？', function (index) {
                    layer.close(index);
                    var ids = new Array(selectCount);
                    for (var i = 0; i < selectCount; i++) {
                        ids[i] = checkStatus.data[i].id;
                    }

                    $.ajax({
                        type: "get",
                        async: false, // 同步请求
                        cache: true,// 不使用ajax缓存
                        contentType: "application/json",
                        url: "${ctx}/advertising",
                        data: "method=updatePositionStatus&status=2&ids=" + ids,
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                layer.msg("操作成功");
                                table.reload("listTable");
                            } else {
                                layer.msg("异常");
                            }
                        },
                        error: function () {
                            layer.alert("错误");
                        }
                    })
                });
            }
            return false;
        });

        table.on('tool(pictureList)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                layer.open({
                    type: 1
                    , title: '编辑'
                    , id: 'layerDemo'
                    , area: ['600px', '600px']
                    , content: $('#PictureEdit')
                    , btn: ['保存', '取消']
                    , btnAlign: 'c' //按钮居中
                    , shade: 0 //不显示遮罩
                    , yes: function (data) {
                        var return_id = $("#return_id").val();
                        var return_position_name = $("#return_position_name").val();
                        var return_page_location = $("#return_page_location").val();
                        if ($("#return_page_location").val() == "首页") {
                            locationPage = 1;
                        } else if ($("#return_page_location").val() == "分类") {
                            locationPage = 2;
                        }
                        else if ($("#return_page_location").val() == "会员") {
                            locationPage = 3;
                        }
                        else if ($("#return_page_location").val() == "我的") {
                            locationPage = 4;
                        }
                        var return_position = $("#return_position").val();
                        var return_market_time = $("#return_market_time").val();
                        var return_marketend_time = $("#return_marketend_time").val();
                        var return_playback_length = $("#return_playback_length").val();
                        $.ajax({
                            type: "get",
                            async: false, // 同步请求
                            cache: true,// 不使用ajax缓存
                            contentType: "application/json",
                            url: "${ctx}/advertising?method=updatePositionEdit",
                            data: {
                                "id": return_id,
                                "position_name": return_position_name,
                                "page_location": locationPage,
                                "position": return_position,
                                "market_time": return_market_time,
                                "marketend_time": return_marketend_time,
                                "playback_length": return_playback_length
                            },

                            dataType: "json",
                            success: function (data) {
                                if (data.success) {
                                    layer.msg('编辑成功', {time: 1000}, function () {
                                        //do something
                                        window.location.reload();
                                    });

                                } else {
                                    layer.msg("异常");
                                }
                            },
                            error: function () {
                                layer.alert("错误");
                            }
                        });
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                    , success: function () {

                        $("#return_id").val(data.id);
                        $("#return_position_name").val(data.position_name);
                        $("#return_page_location").val(data.page_location);
                        if (data.page_location == "1") {
                            $("#return_page_location").val("首页");
                        }
                        if (data.page_location == "2") {
                            $("#return_page_location").val("分类");
                        }
                        if (data.page_location == "3") {
                            $("#return_page_location").val("会员");
                        }
                        if (data.page_location == "4") {
                            $("#return_page_location").val("我的");
                        }
                        $("#return_position").val(data.position);
                        $("#return_playback_length").val(data.playback_length);

                        var return_market_time = $("#return_market_time").val();
                        var return_marketend_time = $("#return_marketend_time").val();

                        if (typeof data.market_time != "undefined") {
                            var st = data.market_time;
                            return_market_time = "20" + st.substr(0, 2) + "-" + st.substr(2, 2) + "-" + st.substr(4, 2) + " " + st.substr(6, 2) + ":" + st.substr(8, 2) + ":" + st.substr(10, 2);
                            $("#return_market_time").val(return_market_time);
                        }
                        if (typeof data.marketend_time != "undefined") {
                            var et = data.marketend_time;
                            return_marketend_time = "20" + et.substr(0, 2) + "-" + et.substr(2, 2) + "-" + et.substr(4, 2) + " " + et.substr(6, 2) + ":" + et.substr(8, 2) + ":" + et.substr(10, 2);
                            $("#return_marketend_time").val(return_marketend_time);
                        }


                    }
                });
            }
            else if (obj.event === 'delete') {
                var adverts_id = $("#adverts_id").val();
                <%--window.location.href = "${ctx}/advertising/advertList.jsp?id=" + data.id + "&adverts_id=" + data.adverts_id + "&page_location=" + data.page_location + "&position=" + data.position;--%>
                window.location.href = "${ctx}/advertising/advertList.jsp?id=" + data.id;
            }
        });


    });


</script>
<!-- 操作 -->
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs layui-btn-normal layui-btn-radius" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal layui-btn-radius" lay-event="delete">删除</a>
</script>
<!-- 编辑时间 -->
<script type="text/html" id="editTimeTmpl">
    {{# if(d.update_time ==''){}}
    <span style="color: rgba(10,10,10,0.46);text-align: center;width: 100%;height: 100%;display: inline-block;"> ----</span>
    {{# }else { }}
    20{{ d.update_time.substr(0,2) }}-{{ d.update_time.substr(2,2) }}-{{ d.update_time.substr(4,2) }} {{ d.update_time.substr(6,2) }}:{{ d.update_time.substr(8,2) }}:{{ d.update_time.substr(10,2) }}
    {{# } }}
</script>
<!-- 创建时间 -->
<script type="text/html" id="createTimeTmpl">
    {{# if(d.create_time ==''){}}
    <span style="color: rgba(10,10,10,0.46);text-align: center;width: 100%;height: 100%;display: inline-block;"> ----</span>
    {{# }else { }}
    20{{ d.create_time.substr(0,2) }}-{{ d.create_time.substr(2,2) }}-{{ d.create_time.substr(4,2) }} {{ d.create_time.substr(6,2) }}:{{ d.create_time.substr(8,2) }}:{{ d.create_time.substr(10,2) }}
    {{# } }}
</script>
<!-- 内容主体区域 -->
<div class="layui-body">
    <!-- 上部分查询表单-->
    <div class="main-top" style="padding:5px 5px 0px 5px">
        <div class="layui-elem-quote">
            图片库分类
        </div>
        <form class="layui-form layui-form-pane">
            <div style="background-color: #f2f2f2;padding:5px 0">
                <div class="layui-form-item" style="margin-bottom:5px">

                </div>

                <div class="layui-form-item" style="margin-bottom: 0">

                </div>
            </div>
        </form>
        <div style="margin-top: 5px">
            <button id="insert" class="layui-btn layui-btn-sm" style="margin-top: 5px">新增图片</button>
        </div>
        <!-- 表格显示-->
        <table class="layui-hide" id="pictureTable" lay-filter="pictureList"></table>
    </div>

    <!-- 编辑页面 -->


    <!-- 新增页面 -->
    <div id="PictureAdd" style="display: none; padding: 15px;">
        <form class="layui-form layui-form-pane">
            <div class="layui-form-item">

                <label class="layui-form-label" style="width: 150px">图片分类名称</label>
                <div class="layui-input-block">
                    <input style="width: 350px" class="layui-input" type="text" name="category_name" id="category_name"
                           lay-verify="title" autocomplete="off">
                </div>

            </div>


        </form>
    </div>

</div>
<%@ include file="/common/footer.jsp" %>