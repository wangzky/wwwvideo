<#include "../layout.ftl" />
<#include "../video.ftl" />
<#include "uploadToOSS.ftl"/>
<@layout title='视频管理'>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>视频管理</legend>
    </fieldset>

    <button type="button" class="layui-btn layui-btn-primary" id="addVideo">上传视频</button>
    <button type="button" class="layui-btn layui-btn-primary" onclick="window.open('/')">返回首页</button>

    <table id="videoList" lay-filter="videoListFilter"></table>


    <script type="text/javascript">
        layui.use(['table', 'layer', 'upload'], function () {
            var table = layui.table,
                layer = layui.layer,
                upload = layui.upload

            var $ = layui.jquery;
            var openIndex;
            var openLoginIndex;
            table.render({
                elem: '#videoList'
                , id: 'videoList'
                , height: 500
                , url: '/getVideoItemList' //数据接口
                // ,page: true //开启分页
                , method: 'post'
                , cols: [[ //表头
                    {field: 'videoId', title: 'ID', width: '10%', fixed: 'left'}
                    , {field: 'videoTitle', title: '视频标题', width: '20%'}
                    , {field: 'videoAbstract', title: '视频简介', width: '20%'}
                    , {field: 'videoUrl', title: '视频链接', width: '40%'}
                    , {field: 'videoTime', title: '上传时间', width: '10%'}
                ]]
            });
            //监听单元格事件
            table.on('row(videoListFilter)', function (obj) {
                var data = obj.data;
                console.log(data)
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
                    if (code === '' || code == null){
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

                $('#canal').on('click', function () {
                    closeAdd()
                });


                $('#addSub').on('click', function () {
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

        });
    </script>

</@layout>

<#--弹出框-->
<div id="openDiv" style="display: none;margin: 20px 20px;">
    <form class="layui-form">
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

