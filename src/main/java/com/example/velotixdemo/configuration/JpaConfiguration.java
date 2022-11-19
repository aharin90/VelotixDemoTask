package com.example.velotixdemo.configuration;

import com.example.velotixdemo.VelotixDemoApplication;
import com.example.velotixdemo.model.LogModel;
import com.example.velotixdemo.repository.LogRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackageClasses={VelotixDemoApplication.class})
@EntityScan(basePackageClasses={LogModel.class})
@EnableJpaRepositories(basePackageClasses={LogRepository.class})
@EnableTransactionManagement
public class JpaConfiguration {}
