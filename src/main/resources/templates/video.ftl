<#macro video url=''>

    <div class="video" id="video" data-url="${url}" style="width: 100%;"></div>

    <script type="text/javascript">

        layui.config({
            base: '/static/'
        }).extend({
            ckplayer: 'ckplayer/ckplayer'
        }).use(['jquery', 'ckplayer'], function() {
            var $ = layui.$,
                ckplayer = layui.ckplayer;
            var vUrl = $('#video').data('url')
            var videoObject = {
                    container: '#video',
                    loop: false,
                    autoplay: false,
                    video: [
                        [vUrl, 'video/mp4']
                    ]
                };
            if (vUrl === ''){
                console.log("视频链接为空")
            }
            console.log(vUrl)
            new ckplayer(videoObject);
        });
    </script>
</#macro>