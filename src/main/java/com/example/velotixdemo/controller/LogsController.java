package com.example.velotixdemo.controller;

import com.example.velotixdemo.model.LogModel;
import com.example.velotixdemo.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping(path = "logs")
public class LogsController {

    private final LogService logService;

    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public void fetchLogFile(@RequestParam("file") MultipartFile file) {
        logService.processLogs(file);
    }

    @GetMapping
    public List<LogModel> retrieveModels(
            @PathParam("level") String level,
            @PathParam("dateFrom") String dateFrom,
            @PathParam("dateTo") String dateTo,
            @PathParam("text") String text) throws ParseException {

        return logService.retrieveValues(level, dateFrom, dateTo, text);
    }

    @ExceptionHandler
    public ResponseEntity<String> dateParseExceptionHandler(ParseException e) {
        return ResponseEntity.badRequest()
                .body("Wrong Date format specified. Please enter dateTo/dateFrom type of <b>yyyy-MM-dd HH:mm:ss.SSS<b>");
    }

    @ExceptionHandler
    public ResponseEntity<String> handler(Exception e) {
        e.printStackTrace();
        return  ResponseEntity.badRequest().body("Something bad happened");
    }
}
