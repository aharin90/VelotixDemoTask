package com.example.velotixdemo.controller;

import com.example.velotixdemo.model.LogModel;
import com.example.velotixdemo.repository.LogRepository;
import com.example.velotixdemo.utils.LogUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllersTest  {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogRepository repository;
    private final LogUtils logUtils = new LogUtils();

    @BeforeAll
    public void fillDb() throws ParseException {
        LogModel model = new LogModel(1L,"INFO", logUtils.convertStringToDate("2018-01-01 00:00:00.111"),"INFO MESSAGE");
        repository.save(model);
        LogModel model2 = new LogModel(2L,"WARN", logUtils.convertStringToDate("2020-01-01 00:00:00.111"),"WARN MESSAGE");
        repository.save(model2);
        LogModel model3 = new LogModel(3L,"ERROR", logUtils.convertStringToDate("2022-01-01 00:00:00.111"),"ERROR MESSAGE");
        repository.save(model3);
    }

    @AfterAll
    public void cleanDb() {
        repository.deleteAll();
    }

    @Test
    void shouldReceiveException() throws Exception {
        String fileName = "sampleFile.txt";
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                "Bad file content".getBytes()
        );
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/logs");
        mockMvc.perform(multipartRequest.file(sampleFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Something bad happened")));
    }

    @Test
    void shouldSendFileAndReceive200OK() throws Exception {
        String fileName = "sampleFile.txt";
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                fileName,
                "text/plain",
                "WARN : 2021-01-29 23:00:36.120 HikariPool-1 housekeeper: HikariPool$HouseKeeper: HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=47m52s667ms).".getBytes()
        );
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/logs");
        mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().isOk());
    }

    @Test
    void shouldReceiveParseError() throws Exception {
        mockMvc.perform(get("/logs").queryParam("dateFrom", "bad date"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Wrong Date format specified.")));
    }

    @Test
    void shouldGetMessageByLevel() throws Exception {
        mockMvc.perform(get("/logs").queryParam("level", "INFO"))
                .andExpect(content().string(containsString("INFO")))
                .andExpect(content().string(not(containsString("WARN"))))
                .andExpect(content().string(not(containsString("ERROR"))))
        ;
    }

    @Test
    void shouldGetMessageByDateRange() throws Exception {
        mockMvc.perform(get("/logs").queryParam("dateFrom", "2020-01-01 00:00:00.111"))
                .andExpect(content().string(not(containsString("INFO"))))
                .andExpect(content().string(containsString("WARN")))
                .andExpect(content().string(containsString("ERROR")));
    }

    @Test
    void shouldGetMessageByMessagePart() throws Exception {
        mockMvc.perform(get("/logs").queryParam("text", "MESSAGE"))
                .andExpect(content().string(containsString("INFO")))
                .andExpect(content().string(containsString("WARN")))
                .andExpect(content().string(containsString("ERROR")));
    }

    @Test
    void shouldGetOneMessageByCombinedCriteria() throws Exception {
        mockMvc.perform(get("/logs")
                        .queryParam("text", "MESSAGE")
                        .queryParam("dateFrom", "2021-01-01 00:00:00.111"))
                .andExpect(content().string(not(containsString("INFO"))))
                .andExpect(content().string(not(containsString("WARN"))))
                .andExpect(content().string(containsString("ERROR")));
    }
}

