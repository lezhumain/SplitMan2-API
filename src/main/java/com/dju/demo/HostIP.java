package com.dju.demo;

public class HostIP {
    public static final String HOST_IP = "http://127.0.0.1:4200"; // TODO env ?

    public static String getIp() {
        return HOST_IP.split("//")[1].split(":")[0];
    }
}
