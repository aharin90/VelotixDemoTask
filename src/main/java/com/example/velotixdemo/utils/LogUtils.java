package com.example.velotixdemo.utils;

import com.example.velotixdemo.model.LogModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class LogUtils {

    private static final int DATE_TIME = 1;
    private static final int MESSAGE = 2;
    private static final int LEVEL = 0;

    private static final String DATE_TIME_RGX = "(\\d{4}-\\d{2}-\\d{2}\s\\d{2}:\\d{2}:\\d{2}.\\d{3})";

    public String[] parseLogs(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            String[] split = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .reduce((total, line) -> total + line)
                    .map(s -> s.split("(?=(WARN|INFO|ERROR))"))
                    .orElseGet(() -> new String[0]);
            return split;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<LogModel> convertToModels(String[] split) {
        ArrayList<LogModel> logs = new ArrayList<>();
        for (String logRecord : split) {
            try {
                String[] logData = logRecord.split("(?=" + DATE_TIME_RGX + ")|(?<=" + DATE_TIME_RGX + ")");
                LogModel log = new LogModel();
                log.setLevel(logData[LEVEL].replaceAll(":", "").trim());
                log.setDateTime(convertStringToDate(logData[DATE_TIME]));
                log.setMessage(logData[MESSAGE]);
                logs.add(log);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return logs;
    }

    public Timestamp convertStringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return new Timestamp(formatter.parse(date).getTime());
    }
}
