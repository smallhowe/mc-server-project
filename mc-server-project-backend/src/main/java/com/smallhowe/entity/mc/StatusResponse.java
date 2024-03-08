package com.smallhowe.entity.mc;

import lombok.Data;

@Data
public class StatusResponse {
    private ForgeData forgeData;
    private Players players;
    private Version version;
    private Description description;
    private int time;
}
