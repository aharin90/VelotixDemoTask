package com.example.velotixdemo.service;

import com.example.velotixdemo.exception.FileProcessingException;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
public class LogService {

    private final LogRepository logRepo;
    private final EntityManager entityManager;
    private final LogUtils logUtils = new LogUtils();

    public LogService(LogRepository logRepo, EntityManager entityManager) {
        this.logRepo = logRepo;
        this.entityManager = entityManager;
    }
    
    public void processLogs(MultipartFile file) throws FileProcessingException {
        String[] logsArray = logUtils.parseLogs(file);
        ArrayList<LogModel> logModels = logUtils.convertToModels(logsArray);
        persistLogs(logModels);
    }

    public void persistLogs(List<LogModel> a) {
        logRepo.saveAll(a);
    }

    public List<LogModel> retrieveValues(String level, String dateFrom, String dateTo, String text) throws ParseException {

        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<LogModel> cr = cb.createQuery(LogModel.class);
        Root<LogModel> root = cr.from(LogModel.class);
        List<Predicate> pr = new ArrayList<>();

        if (dateFrom != null) {
            pr.add(cb.greaterThanOrEqualTo(root.get("dateTime"), logUtils.convertStringToDate(dateFrom)));
        }
        if (dateTo != null) {
            pr.add(cb.lessThanOrEqualTo(root.get("dateTime"), logUtils.convertStringToDate(dateTo)));
        }
        if (level != null) {
            pr.add(cb.equal(root.get("level"), level));
        }
        if (text != null) {
            pr.add(cb.like(root.get("message"), "%" + text + "%"));
        }
        Predicate[] predicates = pr.toArray(new Predicate[0]);
        Query<LogModel> query = session.createQuery(cr.select(root).where(predicates));

        session.close();
        return query.getResultList();
    }
}
