package com.example.velotixdemo.service;


import com.example.velotixdemo.model.LogModel;
import com.example.velotixdemo.repository.LogRepository;
import com.example.velotixdemo.utils.LogUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;


@Service
public class LogService {

    private final LogRepository logRepo;
    private final EntityManager entityManager;
    private final LogUtils logUtils;

    public LogService(LogRepository logRepo, EntityManager entityManager, LogUtils logUtils) {
        this.logRepo = logRepo;
        this.entityManager = entityManager;
        this.logUtils = logUtils;
    }


    public void processLogs(MultipartFile file) {
        List<LogModel> logs = logUtils.parseLogs(file);
        persistLogs(logs);
    }

    public void persistLogs(List<LogModel> a) {
        logRepo.saveAll(a);
    }

    public List<LogModel> retrieveValues(String level, String dateFrom, String dateTo, String text) {

        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<LogModel> cr = cb.createQuery(LogModel.class);
            Root<LogModel> root = cr.from(LogModel.class);
            List<Predicate> pr = new ArrayList<>();

            if (dateFrom != null) {
                pr.add(cb.equal(root.get("dateTime"), dateFrom));
            }
            if (level != null) {
                pr.add(cb.equal(root.get("level"), level));
            }
            if (text != null) {
                pr.add(cb.like(root.get("message"), "%" + text + "%"));
            }

            Predicate[] predicates = pr.toArray(new Predicate[0]);
            Query<LogModel> query = session.createQuery(cr.select(root).where(predicates));

            return query.getResultList();
        } catch (Exception ignore) {

        }
        return null;
    }
}
