<#include "layout.ftl" />
<#include "video.ftl" />
<@layout title='${videoInfo.videoTitle}'>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend><a name="file" download="${videoInfo.videoTitle}" href="${videoInfo.videoUrl}" target="_blank">${videoInfo.videoTitle}</a></legend>
    </fieldset>
    <p><em>${videoInfo.videoAbstract}</em></p>
    <section class="layui-card">
        <div class="layui-card-body" style="width: 80%">
            <@video url='${videoInfo.videoUrl}'></@video>
        </div>
    </section>

</@layout>