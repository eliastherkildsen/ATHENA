package org.apollo.template.persistence;

import java.util.List;

public interface DAO <T>{

    void add(T t);
    void addAll(List<T> list);

    void delete(T t);
    void deleteAll(List<T> list);

    void update(T t);
    void updateAll(List<T> t);

    T read(int id);
    List<T> readAll();
    
}
