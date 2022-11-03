package com.dju.demo;

public class HostIP {
    public static final String HOST_IP = "PROD_IP"; // TODO env ?

    public static String getIp() {
        return HOST_IP.contains("//")
                ? HOST_IP.split("//")[1].split(":")[0]
                : null;
    }
}
