package com.smallhowe.entity;

import lombok.Data;

import java.util.List;

@Data
public class Levels {
    private Integer level;
    private Long exp;

    public Levels(Integer level, Long exp) {
        this.level = level;
        this.exp = exp;
    }

    public static Levels getMaxLevel(){
        return initLevels().get(initLevels().size()-1);
    }

    public static List<Levels> initLevels(){
        return List.of(
                new Levels(0, 0L),
                new Levels(1, 100L),
                new Levels(2, 300L),
                new Levels(3, 600L),
                new Levels(4, 1000L),
                new Levels(5, 1500L),
                new Levels(6, 2000L),
                new Levels(7, 2600L),
                new Levels(8, 3300L),
                new Levels(9, 4100L),
                new Levels(10, 5000L)
        );
    }
}
