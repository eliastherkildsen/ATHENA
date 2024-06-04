package org.apollo.template.persistence.JDBC.DAO;

import java.util.List;

public interface DAO <T>{

    void add(T t);

    void delete(T t);

    void update(T t);

    T read(int id);
    List<T> readAll();
    
}


