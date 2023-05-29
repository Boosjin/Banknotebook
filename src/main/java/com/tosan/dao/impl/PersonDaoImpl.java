package com.tosan.dao.impl;

import com.tosan.dao.BaseSessionFactory;
import com.tosan.dao.PersonDao;
import com.tosan.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Slf4j
public class PersonDaoImpl implements PersonDao {
    @Override
    public void save(List<Person> people) {
        final SessionFactory sessionFactory = BaseSessionFactory.getInstance();
        final Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            people.forEach(session::merge);
        } catch (Exception e) {
            if (!session.getTransaction().isActive()) {
                log.info("Could Not Start Transaction\n\n\n");
                System.exit(0);
            } else if (!session.isOpen()) {
                log.info("Session Was Closed Unexpectedly\n\n\n");
                System.exit(0);
            } else if (session.getTransaction().isActive()) {
                log.info("Something Went Wrong While Saving People In Database. Rolling Back...\n\n\n");
                session.getTransaction().rollback();
                System.exit(0);
            }
        } finally {
            if (session.isOpen() && session.getTransaction().isActive()) session.getTransaction().commit();
            if (session.isOpen()) session.close();
        }
    }
}
