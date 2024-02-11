package com.smallhowe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.smallhowe.entity.SignIn;
import com.smallhowe.mapper.SignInMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class StudyProjectBackendApplicationTests {
    @Resource
    StringRedisTemplate template;
    @Resource
    SignInMapper signInMapper;


    @Test
    void contextLoads() {
        LocalDateTime ldt = LocalDateTime.now();
        ldt = ldt.withHour(0).withMinute(0).withSecond(0).withNano(0);
        ldt=ldt.plusDays(1);
        System.out.println(ldt);
    }


    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
//    @Test
    void createGenerator() {
        String projectPath = System.getProperty("user.dir");
        //使用mybatis-plus-generator生成模板
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("smallhowe")
                            .outputDir(projectPath+"\\src\\main\\java")
                            .build();
                })
                .packageConfig(builder -> {
                    builder.parent("com.smallhowe")
                            .entity("entity")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .build();
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("db_")
                            .addExclude("persistent_logins")
                            .build();
                    builder.entityBuilder()
                            .idType(IdType.AUTO)
                            .enableLombok()
                            .build();
                    builder.controllerBuilder()
                            .enableRestStyle().build();
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .build();
                    builder.mapperBuilder()
                            .enableMapperAnnotation()
                            .build();

                }).execute();
    }
}
