package com.example.velotixdemo.repository;


import com.example.velotixdemo.model.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepository extends JpaRepository<LogModel, Long> {}
