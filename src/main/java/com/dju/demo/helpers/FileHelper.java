package com.dju.demo.helpers;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class FileHelper {
    private static FileHelper _instance;

    static private BehaviorSubject<Boolean> closed = BehaviorSubject.create();
    static private Observable<Boolean> _dbClosed;

    public static FileHelper get_instance() {
        if(_instance == null) {
            _instance = new FileHelper();
        }
        return _instance;
    }

    protected FileHelper() {
        closed.onNext(true);

        _dbClosed = closed
                .filter(aBoolean -> aBoolean)
                .delay(500, TimeUnit.MILLISECONDS)
                .map(aBoolean -> {
                    return true;
                });

        _dbClosed.subscribe((p) -> {
//            System.out.println("handled close.");
        });
    }

    private void createFileIfNeeded(final String url) throws IOException {
        Path path = Paths.get(url);
        if(!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public String readFile(String filePath, Charset charset) {
        //Create the observable
        Single<String> testSingle = FileHelper._dbClosed
                .firstOrError()
                .map(aBool -> {
                    return doReadFile(filePath, charset);
                });

        return testSingle.blockingGet();
    }

    public String readFile(String filePath) throws IOException {
        return readFile(filePath, StandardCharsets.UTF_8);
    }

    private String doReadFile(String filePath, Charset charset) throws IOException {
        this.createFileIfNeeded(filePath);

        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis, charset);
             BufferedReader reader = getBufferReader(isr)
        ) {
            StringBuilder finalStr = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                finalStr.append(str);
                finalStr.append(System.getProperty("line.separator"));
            }

            return finalStr.toString();

        } catch (IOException e) {
            e.printStackTrace();
            closed.onNext(true);
            throw e;
        } finally {
            closed.onNext(true);
        }
    }

    private BufferedReader getBufferReader(InputStreamReader isr) {
        closed.onNext(false);
        return new BufferedReader(isr);
    }

    public void writeFile(String filePath, final String data) throws IOException {
        //Create the observable
        Single<Boolean> testSingle = FileHelper._dbClosed
                .firstOrError()
                .map(aBool -> {
                    return doWriteFile(filePath, data);
                });

        testSingle.blockingGet();
    }

    private boolean doWriteFile(String filePath, String data) throws IOException {
        String[] lines = data.split("\n");
        try (FileWriter fw = new FileWriter(new File(filePath), StandardCharsets.UTF_8, true);
             BufferedWriter writer = getBufferWriter(fw)) {

            for (String line : lines) {
                writer.append(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            closed.onNext(true);
            throw e;
        }
        finally {
            closed.onNext(true);
        }

        return true;
    }

    private BufferedWriter getBufferWriter(FileWriter fw) {
        closed.onNext(false);
        return new BufferedWriter(fw);
    }

    private String getLastLine(final String fileContent) {
        String[] lines = Arrays.stream(fileContent.split(System.getProperty("line.separator")))
                .filter(s -> s != null && !s.equals(""))
                .toArray(String[]::new);
        String res = lines.length > 0 ? lines[lines.length - 1] : "";

//            org.json.simple.parser.JSONParser jp = new JSONParser();
//            org.json.simple.JSONArray o = (org.json.simple.JSONArray)jp.parse(res);

        if(res == null || res.equals("")) {
            res = "[]";
        }

        return res;
    }

    public String readLastLine(String sessionFile) throws IOException {
        return getLastLine(readFile(sessionFile));
    }
}
