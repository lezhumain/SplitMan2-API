package com.dju.demo.helpers;

import java.util.Arrays;

public class CMDHelperResponse {
    public String response;
    public int exitCode;
    public String filePath;

    @Override
    public String toString() {
        return "CMDHelperResponse{" +
                "response=" + response +
                ", exitCode=" + exitCode +
                '}';
    }

    public CMDHelperResponse(String response, int exitCode, String path) {
        this.exitCode = exitCode;
        this.response = response;
        this.filePath = path;
    }
}
