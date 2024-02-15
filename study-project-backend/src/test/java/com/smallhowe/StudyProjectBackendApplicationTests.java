package com.smallhowe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import com.smallhowe.entity.mc.StatusResponse;
import com.smallhowe.mapper.SignInMapper;
import com.smallhowe.utils.ServerListPing;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;


@SpringBootTest
class StudyProjectBackendApplicationTests {
    @Resource
    StringRedisTemplate template;
    @Resource
    SignInMapper signInMapper;

    private static final int MC_SERVER_PORT = 25565;
    @Test
    void contextLoads() throws IOException {
        ServerListPing serverListPing = new ServerListPing();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", MC_SERVER_PORT);
        serverListPing.setAddress(address);
        StatusResponse statusResponse = serverListPing.fetchData();
        System.out.println(statusResponse);

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
