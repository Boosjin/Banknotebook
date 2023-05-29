package com.tosan.dao;

import com.tosan.entity.Person;

import java.util.List;

public interface PersonDao {
    void save(List<Person> people);
}
