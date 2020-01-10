<#include "layout.ftl" />
<#include "video.ftl" />
<@layout title='首页'>
    <h1>Hello</h1><br>
    <p>当前时间：${.now?string("yyyy-MM-dd HH:mm")}</p>


    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 3%;">
        <legend>My Video</legend>
    </fieldset>
    <ul class="layui-timeline">
        <#if videoList??&& videoList?size&gt;0>
            <#list videoList as item>
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis"></i>
                    <div class="layui-timeline-content layui-text" style="cursor:pointer" onclick='window.location.href="/videoInfo/${item.videoId?string}"'>
                        <h3 class="layui-timeline-title">${item.videoTitle}</h3>
                        <p><em>${item.videoTime} - ${item.videoAbstract}</em></p>
                    </div>
                </li>
            </#list>
        </#if>
    </ul>


</@layout>
