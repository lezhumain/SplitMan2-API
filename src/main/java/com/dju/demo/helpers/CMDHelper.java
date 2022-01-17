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
        final String scriptPath = "Lecteur";

        final String[] cmd = new String[] {
                "python3",
//                scriptPath + "\\LecteurPS.py",
//                path.jo scriptPath + "\\LecteurPS.py",
                Paths.get(scriptPath, "LecteurPS.py").toString(), // TODO remove
                "-n", nom, "-s", prenom, "-b", birth, "-d", d
        };


        CMDHelperResponse resp = new CMDHelper().runCMD(List.of(cmd));

        if(resp.exitCode == 1) {
            return resp;
        }

//        final String imgPath = String.format("%s\\%s_%s.png", scriptPath, nom, prenom);

//        final String imgPath = Paths.get(scriptPath, String.format("%s_%s.png", nom, prenom)).toString();
        final String imgPath = Paths.get(scriptPath, "qrcode_mok_new.jpg").toString();

        if(!Files.exists(Path.of(imgPath))) {
            return null;
        }

        resp.filePath = imgPath;

        return resp;
    }
}
