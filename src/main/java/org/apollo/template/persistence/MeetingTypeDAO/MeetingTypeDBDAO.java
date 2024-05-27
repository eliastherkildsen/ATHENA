package org.apollo.template.persistence.MeetingTypeDAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.model.MeetingType;
import org.apollo.template.persistence.DAO;

import java.sql.Connection;
import java.util.List;

public class MeetingTypeDBDAO implements DAO<MeetingType> {

    // fetching connection from JDBC
    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(MeetingType meetingType) {

    }

    @Override
    public void addAll(List<MeetingType> list) {

    }

    @Override
    public void delete(MeetingType meetingType) {

    }

    @Override
    public void deleteAll(List<MeetingType> list) {

    }

    @Override
    public void update(MeetingType meetingType) {

    }

    @Override
    public void updateAll(List<MeetingType> t) {

    }

    @Override
    public MeetingType read(int id) {
        return null;
    }

    @Override
    public List<MeetingType> readAll() {
        return List.of();
    }
}
