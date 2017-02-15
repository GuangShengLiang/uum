package com.github.uum.persist.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**

 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MapperScanner {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        //配置mapper接口所在的包
        scanner.setBasePackage("com.github.uum.persist.mapper");
        //配置默认的sqlSessionFactory，与MyBatisConfig中配置的别名一致
        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");

        Properties properties = new Properties();
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        scanner.setProperties(properties);
        //配置只扫描某个注解的接口
        //scanner.setAnnotationClass(MyBatisRepository.class);
        return scanner;
    }
}
