package com.wangzk.www.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.wangzk.www.bean.LayResp;
import com.wangzk.www.bean.Resp;
import com.wangzk.www.bean.VideoItem;
import com.wangzk.www.bean.VideoListQry;
import com.wangzk.www.service.SysParamService;
import com.wangzk.www.service.VideoService;
import com.wangzk.www.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;
import java.util.*;

/**
 * PackageName: com.wangzk.www.controller
 * ClassName: IndexController
 * Description:
 * date: 2020/1/7 10:06
 *
 * @author bufou-wangzk
 */
@Controller
@Slf4j
public class IndexController {

    static String endpoint = "https://wangzk-ali-bj.oss-accelerate.aliyuncs.com";
    static String accessKeyId = "TFRBSTRGeGNHc05pejRtTlpLTEd0YTdM";
    static String accessKeySecret = "VXRRakZ1VkRRSGdpUkVhZUtsQW11eW9tRmZlTllz";

    @Autowired
    VideoService videoService;

    @Autowired
    SysParamService sysParamService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("videoList" , videoService.getVideoItemList());
        return modelAndView;
    }

    @RequestMapping("/videoInfo/{videoId}")
    public ModelAndView videoPage(@PathVariable(value = "videoId") String videoId){
        ModelAndView modelAndView = new ModelAndView("videoInfo");
        modelAndView.addObject("videoInfo",videoService.getVideoItemById(videoId));
        return modelAndView;
    }

    @RequestMapping("/videoListAdmin")
    public ModelAndView videoListAdmin(){
        ModelAndView modelAndView = new ModelAndView("admin/videoListAdmin");
        modelAndView.addObject("videoTypeList" , sysParamService.getParamListByCode("video_type").getData());
        return modelAndView;
    }

    @PostMapping("/addVideoItem")
    @ResponseBody
    public Resp addVideoItem(@RequestBody VideoItem videoItem){
        return videoService.addVideoItem(videoItem);
    }

    @PostMapping("/updateVideoInfo")
    @ResponseBody
    public Resp updateVideoInfo(@RequestBody Map input){
        return videoService.updateVideoInfo(input);
    }



    @PostMapping("/getVideoItemList")
    @ResponseBody
    public LayResp getVideoItemList(@RequestBody VideoListQry input){
        log.info("视频列表请求参数==>"+input.toString());
        return videoService.getVideoItemListForLay(input);
    }

    @GetMapping("/admin/del/{videoId}")
    @ResponseBody
    public Resp delVideoItem(@PathVariable(value = "videoId") String videoId){
        log.info("视频删除==>"+videoId);
        return videoService.delVideo(videoId);
    }

    @GetMapping("/getPolicy")
    @ResponseBody
    public Map getPolicy(){

        Map returnMap = new HashMap();
        returnMap.put("code",0);
        returnMap.put("success",true);
        returnMap.put("msg","签名成功");
        returnMap.put("data", getOSSSignature());
        return returnMap;
    }

    @PostMapping("/checkCode")
    @ResponseBody
    public Resp checkCode(@RequestBody Map input){
        String code = (String)input.get("code");
        if ("9900".equals(code)){
            return new Resp();
        }else {
            return new Resp("9999" , "校验失败");
        }
    }

    public Map getOSSSignature(){
        OSS client = new OSSClientBuilder().build(endpoint, Base64Util.decoderBase64(accessKeyId), Base64Util.decoderBase64(accessKeySecret));

        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "video");

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            log.info(encodedPolicy);
            log.info("===================");
            log.info(postSignature);
            Map myData = new HashMap();
            myData.put("accessid", Base64Util.decoderBase64(accessKeyId));
            myData.put("host",endpoint);
            myData.put("policy",encodedPolicy);
            myData.put("signature",postSignature);
            myData.put("expire",1554851252);
            return myData;
        } catch (Exception e) {
            log.error("获取签名失败" + e.getMessage());
            return null;
        }
    }


    public static void main(String[] args) {


    }


}
