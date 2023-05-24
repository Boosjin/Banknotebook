package com.tosan.dao;

import com.tosan.entity.Contact;
import com.tosan.entity.Email;
import com.tosan.entity.FileProgress;
import com.tosan.entity.HomeNumber;
import com.tosan.entity.MobileNumber;
import com.tosan.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public abstract class BaseSessionFactory {

    private final static ReentrantLock reentrantLock = new ReentrantLock();
    private static SessionFactory sessionFactory = null;

    private BaseSessionFactory() {
    }

    public static SessionFactory getInstance() {

        if (sessionFactory == null) {
            reentrantLock.lock();
            try {
                if (sessionFactory == null) sessionFactory = new Configuration()
                        .addAnnotatedClass(Contact.class)
                        .addAnnotatedClass(Email.class)
                        .addAnnotatedClass(FileProgress.class)
                        .addAnnotatedClass(HomeNumber.class)
                        .addAnnotatedClass(MobileNumber.class)
                        .addAnnotatedClass(Person.class)
                        .buildSessionFactory();
            } catch (HibernateException e) {
                log.info("Something Went Wrong While Making Session Factory");
                System.exit(0);
            }
            reentrantLock.unlock();
        }
        return sessionFactory;
    }
}