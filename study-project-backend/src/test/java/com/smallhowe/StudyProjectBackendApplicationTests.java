package com.smallhowe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class StudyProjectBackendApplicationTests {
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {

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
