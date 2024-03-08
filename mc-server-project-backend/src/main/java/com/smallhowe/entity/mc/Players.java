package com.smallhowe.entity.mc;

import lombok.Data;

import java.util.List;

@Data
public class Players {
    private int max;
    private int online;
    private List<Player> sample;
}
