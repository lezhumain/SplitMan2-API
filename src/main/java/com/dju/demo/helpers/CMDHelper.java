package com.dju.demo.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class CMDHelper {
    public CMDHelperResponse runCMD(final List<String> cmds) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(cmds);

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        String results = readProcessInput(process.getInputStream());
        String results1 = readProcessInput(process.getErrorStream());

//        System.out.println(results);

        int exitCode = process.waitFor();

        return new CMDHelperResponse(results, exitCode, null);
    }

    private String readProcessInput(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    public CMDHelperResponse genImg(final String nom, final String prenom, final String birth, final String d) throws IOException, InterruptedException {
        final String scriptPath = "";

        final String[] cmd = new String[] {
                "python",
                scriptPath + "\\LecteurPS.py",
                "-n", nom, "-s", prenom, "-b", birth, "-d", d
        };


        CMDHelperResponse resp = new CMDHelper().runCMD(List.of(cmd));

        final String imgPath = String.format("%s\\%s_%s.png", scriptPath, nom, prenom);

        if(!Files.exists(Path.of(imgPath))) {
            return null;
        }

        resp.filePath = imgPath;

        return resp;
    }
}
