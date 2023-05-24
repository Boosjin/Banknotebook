package com.tosan.dao.impl;

import com.tosan.dao.BaseSessionFactory;
import com.tosan.dao.FileProgressDao;
import com.tosan.entity.FileProgress;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class FileProgressDaoImpl implements FileProgressDao {

    @Override
    public void saveProgress(FileProgress fileProgress) {

        final SessionFactory sessionFactory = BaseSessionFactory.getInstance();
        final Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.merge(fileProgress);
        } catch (Exception e) {
            if (!session.getTransaction().isActive()) {
                log.info("Could Not Start Transaction\n\n\n");
                System.exit(0);
            } else if (!session.isOpen()) {
                log.info("Session Was Closed Unexpectedly\n\n\n");
                System.exit(0);
            } else if (session.getTransaction().isActive()) {
                log.info("Something Went Wrong While Saving File Progress In Database. Rolling Back...\n\n\n");
                session.getTransaction().rollback();
                System.exit(0);
            }
        } finally {
            if (session.isOpen() && session.getTransaction().isActive()) session.getTransaction().commit();
            if (session.isOpen()) session.close();
        }
    }

    @Override
    public FileProgress getFileProgress(String fileUrl) {

        final SessionFactory sessionFactory = BaseSessionFactory.getInstance();
        final Session session = sessionFactory.getCurrentSession();
        FileProgress fileProgress = null;

        try {
            session.beginTransaction();
            fileProgress = session.get(FileProgress.class, fileUrl);
        } catch (Exception e) {
            if (!session.getTransaction().isActive()) {
                log.info("Could Not Start Transaction\n\n\n");
                System.exit(0);
            } else if (!session.isOpen()) {
                log.info("Session Was Closed Unexpectedly\n\n\n");
                System.exit(0);
            } else if (session.getTransaction().isActive()) {
                log.info("Something Went Wrong While Getting File Progress From Database. Rolling Back...\n\n\n");
                session.getTransaction().rollback();
                System.exit(0);
            }
        } finally {
            if (session.isOpen() && session.getTransaction().isActive()) session.getTransaction().commit();
            if (session.isOpen()) session.close();
        }
        return fileProgress;
    }
}
