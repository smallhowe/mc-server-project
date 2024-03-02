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
                new Levels(1, 100L), // +200
                new Levels(2, 300L), // +300
                new Levels(3, 600L), // +400
                new Levels(4, 1000L), // +500
                new Levels(5, 1500L), // +600
                new Levels(6, 2100L), // +700
                new Levels(7, 2800L), // +800
                new Levels(8, 4600L), // +900
                new Levels(9, 5500L), // +1000
                new Levels(10, 6500L)
        );
    }
}
