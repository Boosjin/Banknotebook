package com.tosan.dao.impl;

import com.tosan.dao.BaseSessionFactory;
import com.tosan.dao.ProcessedRecordNumberDao;
import com.tosan.entity.ProcessedRecordNumber;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class ProcessedRecordNumberDaoImpl implements ProcessedRecordNumberDao {
    @Override
    public void saveProcessedRecordNumber(ProcessedRecordNumber processedRecordNumber) {
        final SessionFactory sessionFactory = BaseSessionFactory.getInstance();
        final Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(processedRecordNumber);
        } catch (Exception e) {
            if (!session.getTransaction().isActive()) {
                log.info("Could Not Start Transaction\n\n\n");
                System.exit(0);
            } else if (!session.isOpen()) {
                log.info("Session Was Closed Unexpectedly\n\n\n");
                System.exit(0);
            } else if (session.getTransaction().isActive()) {
                log.info("Something Went Wrong While Saving Processed Record Number In Database. Rolling Back...\n\n\n");
                session.getTransaction().rollback();
                System.exit(0);
            }
        } finally {
            if (session.isOpen() && session.getTransaction().isActive()) session.getTransaction().commit();
            if (session.isOpen()) session.close();
        }
    }
}
