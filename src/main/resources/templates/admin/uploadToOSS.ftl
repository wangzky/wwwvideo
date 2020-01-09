<#macro uploadToOSS >
    <a class="layui-btn layui-btn-sm" style="margin-top: 5px" id="uploadOne">
        上传视频文件
    </a>

    <input id="videoUrl" style="display: none" value=""/>
<#--    <a class="layui-btn layui-btn-sm" id="uploadMore">-->
<#--        上传多个文件-->
<#--    </a>-->

    <script>
        layui.config({
            base: '/static/'
        }).extend({
            aliossUploader: 'js/aliossUploader'
        }).use(['aliossUploader'], function() {
            var aliossUploader = layui.aliossUploader;
            aliossUploader.render({
                elm:'#uploadOne',
                region:'oss-cn-beijing',
                bucket: 'wangzk-ali-bj',
                layerTitle:'上传数据文件',
                policyData:{},
                policyHeader:{
                    Authorization:layui.data('wangzk-ali-bj').Authorization
                },
                policyUrl:'/getPolicy',
                prefixPath:'video/',
                // fileType:'images',
                multiple:false,
                httpStr:'http',
                allUploaded:function(res){
                    console.log(res)
                    layer.msg(JSON.stringify(res[0].name) + '上传成功');
                    var url = res[0].ossUrl;
                    $('#videoUrl').attr('value',url)
                    var html = '<p id="fileState"><em>'+ res[0].name +'</em></p>'
                    $('#uploadOne').after(html)
                },
                policyFailed:function(res){
                    console.error(res)
                    // layer.msg(JSON.stringify(res));
                    var html = '<p  id="fileState"><em>上传失败</em></p>'
                    $('#uploadOne').after(html)
                }
            });
            // aliossUploader.render({
            //     elm:'#uploadMore',
            //     region:'oss-cn-shanghai',
            //     bucket: 'XXXXXX',
            //     layerTitle:'上传文件到阿里云OSS',
            //     accessidFiled : 'accessid',
            //     policyFiled : 'policy',
            //     signatureFiled : 'signature',
            //     codeFiled:'code',
            //     codeStatus:0,
            //     policyMethod:'GET',
            //     policyData:{},
            //     policyHeader:{
            //         Authorization:layui.data('XXXXXX').Authorization
            //     },
            //     policyUrl:'https://www.XXXXXX.cn/pop/api/alioss/policy',
            //     httpStr:'https',
            //     layerArea:['800px','500px'],
            //     prefixPath:'coordtrans/',
            //     fileType:'images',
            //     multiple:true,
            //     allUploaded:function(res){
            //         layer.msg(JSON.stringify(res));
            //     },
            //     policyFailed:function(res){
            //         layer.msg(JSON.stringify(res));
            //     },
            //     uploadRenderData:{
            //         size:100
            //     }
            // });
        });
    </script>
</#macro>