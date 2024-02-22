package com.smallhowe.service.impl;

import com.smallhowe.entity.mc.StatusResponse;
import com.smallhowe.service.MinecraftService;
import com.smallhowe.utils.ServerListPing;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MinecraftServiceImpl implements MinecraftService {
    @Override
    public StatusResponse getServerStatus() {
        ServerListPing ping = new ServerListPing();
        ping.setAddress(new InetSocketAddress("59x-11.mcstory.cc", 29492));
        StatusResponse statusResponse;
        try {
            statusResponse = ping.fetchData();
            String version=statusResponse.getVersion().getName();
            Pattern pattern = Pattern.compile("\\d+(?:\\.\\d+)*");
            Matcher matcher = pattern.matcher(version);
            if (matcher.find()) {
                statusResponse.getVersion().setName(matcher.group());
            }

        } catch (IOException e) {
            return null;
        }
        return statusResponse;
    }
}
