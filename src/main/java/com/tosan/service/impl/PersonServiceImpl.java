package com.tosan.service.impl;

import com.tosan.dao.PersonDao;
import com.tosan.entity.Person;
import com.tosan.service.PersonService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public void save(List<Person> people) {
        if (people == null) {
            log.info("You Can Not Provide A Null List Of People To Save!\n\n\n");
            System.exit(0);
        }
        personDao.save(people);
    }
}
