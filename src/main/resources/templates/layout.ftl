<#macro layout title="YY" content="keyword1,keyword2,keyword3" menuType="">
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <title>${title}</title>
        <link rel="icon" type="image/x-icon" href="/static/icon/video.ico">
        <link rel="stylesheet" href="/static/layui/css/layui.css">

        <script charset="utf-8" type="text/javascript" src="/static/js/jquery-3.2.1.js"></script>
        <script charset="utf-8" type="text/javascript" src="/static/layui/layui.all.js"></script>
    </head>
    <body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-body" style="top:7%;left: 10%">
            <!-- 内容主体区域 -->
            <div style="padding: 1%;">
                <#nested>
            </div>
        </div>


    </div>

    <script>
    </script>

    </body>
    </html>
</#macro>