package com.smallhowe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;


import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Random;


@SpringBootTest
class StudyProjectBackendApplicationTests {
//    @Resource
//    StringRedisTemplate template;
//    @Resource
//    SignInMapper signInMapper;
//    @Resource
//    AccountMapper accountMapper;
//    @Resource
//    BCryptPasswordEncoder passwordEncoder;

//    private static final int MC_SERVER_PORT = 25565;
    @Test
    void contextLoads()  {
        long start = System.currentTimeMillis();

        for (int i=0;i<10;i++){
            choujiang();
        }


        System.out.println("运行时长："+(System.currentTimeMillis()-start));
    }

    void choujiang(){
        @Data
        class Jp{
            private String name;
            private int gl;

            public Jp(String name, int gl) {
                this.name = name;
                this.gl = gl;
            }
        }

        List<Jp> list = List.of(
                new Jp("一等奖", 300),
                new Jp("二等奖", 1200),
                new Jp("三等奖", 3500),
                new Jp("没中奖", 10000)
        );


        Random random = new Random();

        int swi = random.nextInt(1,10001);
        for (Jp jp:list){
            if (swi<=jp.getGl()){
                System.out.println(swi+"中了"+jp.getName());
                break;
            }
        }
    }


    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Test
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
