package com.wangzk.www.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * PackageName: com.wangzk.www.config
 * ClassName: MyErrorPageConfig
 * Description:
 * date: 2020/1/10 15:46
 *
 * @author bufou-wangzk
 */
@Configuration
public class MyErrorPageConfig {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error-400");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-404");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-500");
                factory.addErrorPages(errorPage400, errorPage404, errorPage500);
            }
        };
    }
}
