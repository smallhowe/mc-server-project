package com.smallhowe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class StudyProjectBackendApplicationTests {
    @Resource
    StringRedisTemplate template;

    @Test
    void contextLoads() {
        System.out.println(template.getExpire("email:66F426023E6E66F95D941A57BA474006:4008611252@qq.com"));
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
                    builder.addTablePrefix("db_").build();
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
