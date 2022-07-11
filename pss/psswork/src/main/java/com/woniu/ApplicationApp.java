package com.woniu;

import com.woniu.util.MinioUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.woniu.dao.mysql")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableRedisRepositories(basePackages = {"com.woniu.dao.redis"})
@RestController
public class ApplicationApp {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationApp.class,args);
    }

    @Bean
    public MinioUtils minioUtils() {
        MinioUtils minioUtils = new MinioUtils("http://192.172.0.205:9000", "minioadmin", "minioadmin");

        // xxx 桶不存在，就创建它。未来，我们要向这个桶里放文件。
        if (!minioUtils.doesBucketExists("business")) {
            minioUtils.createBucket("business");
        }
        return minioUtils;
    }
}
