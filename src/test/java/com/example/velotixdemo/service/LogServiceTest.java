package com.example.velotixdemo.service;

import com.example.velotixdemo.exception.FileProcessingException;
import com.example.velotixdemo.model.LogModel;
import com.example.velotixdemo.repository.LogRepository;
import com.example.velotixdemo.utils.LogUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
class LogServiceTest {

    public static final String DATE_FROM = "2021-03-30 01:03:40.112";
    public static final String DATE_TO = DATE_FROM;
    public static final String WARN = "WARN";
    public static final String INFO = "INFO";
    public static final String ERROR = "ERROR";

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private LogRepository repository;

    private LogService logService;
    private final LogUtils logUtils = new LogUtils();

    @BeforeEach
    public void init() {
        logService = new LogService(repository, entityManager, logUtils);
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(entityManager).isNotNull();
    }

     @Test
     void saveOneEntity() throws ParseException, FileProcessingException {
         MockMultipartFile file
                 = new MockMultipartFile(
                 "file",
                 "hello.txt",
                 MediaType.TEXT_PLAIN_VALUE,
                 "WARN : 2021-03-30 01:03:40.112 HikariPool-1 housekeeper: HikariPool$HouseKeeper: HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=47m52s667ms)."
                         .getBytes());
         logService.processLogs(file);
         List<LogModel> logModels = logService.retrieveValues(WARN, DATE_FROM, DATE_TO, "HikariPool");
         assertThat(logModels).isNotNull();
         assertThat(logModels.size()).isEqualTo(1);
         assertThat(logModels.get(0).getLevel()).isEqualTo(WARN);

         logModels = logService.retrieveValues(INFO, null, null, null);
         assertThat(logModels).isNotNull();
         assertThat(logModels.size()).isEqualTo(0);

         logModels = logService.retrieveValues(ERROR, null, null, null);
         assertThat(logModels).isNotNull();
         assertThat(logModels.size()).isEqualTo(0);

         logModels = logService.retrieveValues(WARN, null, null, "Non existing string");
         assertThat(logModels).isNotNull();
         assertThat(logModels.size()).isEqualTo(0);
    }
}