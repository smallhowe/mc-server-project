package com.smallhowe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.Levels;
import com.smallhowe.entity.mc.StatusResponse;
import com.smallhowe.mapper.AccountMapper;
import com.smallhowe.mapper.SignInMapper;
import com.smallhowe.utils.ServerListPing;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootTest
class StudyProjectBackendApplicationTests {
    @Resource
    StringRedisTemplate template;
    @Resource
    SignInMapper signInMapper;
    @Resource
    AccountMapper accountMapper;
    @Resource
    BCryptPasswordEncoder passwordEncoder;

    private static final int MC_SERVER_PORT = 25565;
    @Test
    void contextLoads() throws IOException {
        int maxLevel = 100000;
        long exp = 0;
        List<Levels> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i=0;i<=maxLevel;i++){

            list.add(new Levels(i, exp));
        }
//        System.out.println(list);
        System.out.println("耗时："+(System.currentTimeMillis()-start));


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
