package com.github.uum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ComponentScan("com.github.uum")
@ImportResource("classpath*:spring/applicationContext-*.xml")
public class UumApplication {
    private static final Logger logger = LoggerFactory.getLogger(UumApplication.class);

    /**
     * 程序启动入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        logger.info("*********************正在启动*********************************");
        SpringApplication application = new SpringApplication(UumApplication.class);
        application.run(args);
        logger.info("*********************启动成功*********************************");
    }
}
