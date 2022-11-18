package com.example.velotixdemo.utils;

import com.example.velotixdemo.model.LogModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class LogUtils {

    private static final int DATE_TIME = 1;
    private static final int MESSAGE = 2;
    private static final int LEVEL = 0;

    private static final String DATE_TIME_RGX = "(\\d{4}-\\d{2}-\\d{2}\s\\d{2}:\\d{2}:\\d{2}.\\d{3})";
    private String all = "";

    public ArrayList<LogModel> parseLogs(MultipartFile file) {
        try {
            InputStream inputStream  = file.getInputStream();

        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .forEach(this::handleLine);

        String[] split = all.split("(?=(WARN|INFO|ERROR))");

        ArrayList<LogModel> logs = new ArrayList<>();

        for (String logRecord : split) {
            String[] logData = logRecord.split("(?=" + DATE_TIME_RGX + ")|(?<=" + DATE_TIME_RGX + ")");
            LogModel log = new LogModel();
            log.setLevel(logData[LEVEL].replaceAll(":", "").trim());
            log.setDateTime(logData[DATE_TIME]);
            log.setMessage(logData[MESSAGE]);
            logs.add(log);
        }
        return logs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLine(String s) {
        all = all.concat(s);
    }
}
