<#include "../layout.ftl" />
<#include "../video.ftl" />
<#include "uploadToOSS.ftl"/>
<@layout title='视频管理'>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>视频管理</legend>
    </fieldset>

<#--select video_type-->
    <form class="layui-form">
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <select id="videoType" style="width: 20%" lay-filter="videoType">
                    <option value="">选择视频类型</option>
                    <#if videoTypeList?? && videoTypeList?size&gt;0>
                        <#list videoTypeList as videoType>
                            <option value="${videoType.id}">${videoType.paramKey}</option>
                        </#list>
                    </#if>
                </select>
            </div>
            <div>
                <button type="button" class="layui-btn layui-btn-primary" id="addVideo">上传视频</button>
                <button type="button" class="layui-btn layui-btn-primary" onclick="window.open('/')">返回首页</button>
            </div>
        </div>
    </form>

    <table id="videoList" lay-filter="videoListFilter"></table>

    <script type="text/html" id="toolbar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="del">删除数据</button>
        </div>
    </script>


    <script type="text/javascript">
        layui.use(['table', 'layer', 'upload', 'form'], function () {
            var table = layui.table,
                layer = layui.layer,
                upload = layui.upload,
                form = layui.form

            var $ = layui.jquery;
            var openIndex;
            var openLoginIndex;
            var host = window.location.host;

            // 刷新 select
            form.render('select');
            table.render({
                elem: '#videoList'
                , id: 'videoList'
                , height: 500
                , url: '/getVideoItemList' //数据接口
                , page: true //开启分页
                , where: {'videoType': ''}
                , contentType: 'application/json'
                , method: 'post'
                , toolbar: '#toolbar'
                , cols: [[ //表头
                    {type:'radio'}
                    , {field: 'video_id', title: 'ID', width: '10%'}
                    , {field: 'video_title', title: '视频标题', width: '20%', edit: 'text'}
                    , {field: 'video_abstract', title: '视频简介', width: '20%', edit: 'text'}
                    , {field: 'video_url_web', title: '视频链接', width: '40%' , templet: function (data) {
                            var url = 'http://' + host + "/videoInfo/"+ data.video_id;
                            return '<a name="file" href="'+ url +'" target="_blank">'+ url +'</a>'
                        }}
                    , {field: 'video_time', title: '上传时间', width: '10%'}
                ]]
            });
            //监听单元格事件
            // table.on('row(videoListFilter)', function (obj) {
            //     var data = obj.data;
            //     console.log(data)
            // });

            //监听单元格编辑
            table.on('edit(videoListFilter)', function(obj){
                var value = obj.value //得到修改后的值
                    ,data = obj.data //得到所在行所有键值
                    ,field = obj.field; //得到字段
                console.log(obj)

                var updateData = {};
                updateData.video_id = data.video_id;
                updateData.field = field;
                updateData.value = value;

                $.ajax({
                    type: 'POST'
                    , url: '/updateVideoInfo'
                    , data: JSON.stringify(updateData)
                    , dataType: 'json'
                    , contentType: 'application/json'
                    , success: function (data) {
                        if (data.code === '0000') {
                            layer.msg('字段更改为：'+ value)
                        }
                        if (data.code === '9999') {
                            layer.msg(data.msg)
                            return false;
                        }
                    }
                })
            });

            //头工具栏事件
            table.on('toolbar(videoListFilter)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
                switch(obj.event){
                    case 'del':
                        var data = checkStatus.data;  //获取选中行数据
                        if (data.length < 1){
                            layer.msg("未选择")
                            return false;
                        }
                        var videoId = data[0].video_id;

                        $.ajax({
                            type: 'GET'
                            , url: '/admin/del/'+videoId
                            , success: function (data) {
                                if (data.code === '0000') {
                                    layer.msg('删除成功')
                                    table.reload('videoList')
                                }
                                if (data.code === '9999') {
                                    layer.msg(data.msg)
                                    return false;
                                }
                            }
                        })

                        break;
                };
            });


            $('#addVideo').on('click', function () {
                openLoginDiv();
                // openAddDiv()
            })

            openLoginDiv = function () {
                openLoginIndex = layer.open({
                    id: 'openLoginLayer',
                    type: 1,
                    title: "校验码",
                    area: ['700px', '300px'],
                    fixed: true,
                    content: $("#login")
                });
                $('#loginSub').on('click', function () {
                    var code = $('#loginCode').val();
                    console.log(code)
                    if (code === '' || code == null) {
                        layer.msg("校验码不能为空！")
                        return false;
                    }
                    var data = {}
                    data.code = code;

                    $.ajax({
                        type: 'POST'
                        , url: '/checkCode'
                        , data: JSON.stringify(data)
                        , dataType: 'json'
                        , contentType: 'application/json'
                        , success: function (data) {
                            if (data.code === '0000') {
                                openAddDiv()
                                layer.msg("校验成功")
                                closeCanal()
                            }
                            if (data.code === '9999') {
                                layer.msg(data.msg)
                                return;
                            }
                        }
                    })
                });
                $('#loginSubCanal').on('click', function () {
                    closeCanal()
                    return false
                });

                return false;
            }

            openAddDiv = function () {
                openIndex = layer.open({
                    id: 'openDivLayer',
                    type: 1,
                    title: "新增视频",
                    area: ['700px', '300px'],
                    fixed: true,
                    content: $("#openDiv")
                });

                form.render('select');

                $('#canal').on('click', function () {
                    closeAdd()
                });


                $('#addSub').on('click', function () {
                    var editVideoType = $.trim($('#editvideoType').val())

                    if(editVideoType === ''){
                        layer.msg("请选择视频类型")
                        return;
                    }
                    var editVideoName = $('#editVideoName').val();
                    if (editVideoName == null || editVideoName === '') {
                        layer.msg("视频名称不能为空")
                        return;
                    }
                    var editVideoAbstract = $('#editVideoAbstract').val();
                    if (editVideoAbstract == null || editVideoAbstract === '') {
                        editVideoAbstract = '简介暂无'
                    }
                    var videoUrl = $('#videoUrl').attr('value');
                    if (videoUrl == null || videoUrl === '') {
                        layer.msg("视频为空，请上传视频")
                        return;
                    }

                    var data = {};
                    data.videoTitle = editVideoName;
                    data.videoAbstract = editVideoAbstract;
                    data.videoUrl = videoUrl;
                    data.videoType = editVideoType;

                    $.ajax({
                        type: 'POST'
                        , url: '/addVideoItem'
                        , data: JSON.stringify(data)
                        , dataType: 'json'
                        , contentType: 'application/json'
                        , success: function (data) {
                            if (data.code === '0000') {
                                layer.msg("保存成功")
                                closeAdd()
                                table.reload('videoList')
                            }
                            if (data.code === '9999') {
                                layer.msg(data.msg)
                                return;
                            }
                        }
                    })
                });
            }

            closeAdd = function () {
                layer.close(openIndex);
                $('#addSub').off('click')
                $('#canal').off('click')
                $('#fileState').remove()
                $('#editVideoName').val('')
                $('#editVideoAbstract').val('')
            }

            closeCanal = function () {
                layer.close(openLoginIndex);
                $('#loginSub').off('click')
                $('#loginSubCanal').off('click')
                $('#loginCode').val('')
            }

            form.on('select(videoType)', function (data) {
                console.log(data.value); //得到被选中的值

                table.reload(
                    'videoList',
                    {
                        where: {"videoType": data.value}
                    }
                );

            });

        });
    </script>

</@layout>

<#--弹出框-->
<div id="openDiv" style="display: none;margin: 20px 20px;">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">视频类型</label>
            <div class="layui-input-block" style="width: 40%;">
                <select id="editvideoType">
                    <#if videoTypeList?? && videoTypeList?size&gt;0>
                        <#list videoTypeList as videoType>
                            <option value="${videoType.id}">${videoType.paramKey}</option>
                        </#list>
                    </#if>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">视频名称</label>
            <div class="layui-input-block">
                <input type="text" id="editVideoName" placeholder="请输入视频名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">视频简介</label>
            <div class="layui-input-block">
                <input type="text" id="editVideoAbstract" placeholder="请输入视频简介" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">视频内容</label>
            <div class="layui-input-block">
                <@uploadToOSS></@uploadToOSS>
            </div>
        </div>
        <div class="layui-form-item">
            <div align="center">
                <button type="button" class="layui-btn" id="addSub">确定</button>
                <button type="button" class="layui-btn layui-btn-primary" id="canal">取消</button>
            </div>
        </div>
    </form>
</div>

<div id="login" style="display: none;margin: 20px 20px;">
    <label class="layui-form-label">输入校验码</label>
    <div class="layui-input-block">
        <input type="text" id="loginCode" placeholder="请输入校验码" autocomplete="off" class="layui-input">
    </div>
    <div align="center" style="margin-top: 50px">
        <button type="button" class="layui-btn" id="loginSub">确定</button>
        <button type="button" class="layui-btn layui-btn-primary" id="loginSubCanal">取消</button>
    </div>
</div>

