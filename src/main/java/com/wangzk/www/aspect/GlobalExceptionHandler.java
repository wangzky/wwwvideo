package com.wangzk.www.aspect;

import com.wangzk.www.bean.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: GlobalExceptionHandler
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.aspect
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-16 10:16
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Resp notFound(RuntimeException e) {
        log.error("运行时异常：", e);
        String msg = "运行时异常:"+ e.getMessage();
        return new Resp("9999",msg);
    }
    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Resp badRequest(Exception e) {
        log.error("请求体异常：", e);
        String msg = e.getMessage();
        return new Resp("400",msg);
    }
}
