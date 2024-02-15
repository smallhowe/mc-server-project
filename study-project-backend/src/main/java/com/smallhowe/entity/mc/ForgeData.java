package com.smallhowe.entity.mc;

import lombok.Data;

import java.util.List;

@Data
public class ForgeData {
    private List<String> channels;
    private List<String> mods;
    private boolean truncated;
    private int fmlNetworkVersion;
}
