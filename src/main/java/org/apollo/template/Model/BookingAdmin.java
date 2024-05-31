package org.apollo.template.Model;

public class BookingAdmin extends Booking{
    private boolean catering;
    private Department department;
    private Team team;

    public boolean isCatering() {
        return catering;
    }

    public BookingAdmin setCatering(boolean catering) {
        this.catering = catering;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public BookingAdmin setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public BookingAdmin setTeam(Team team) {
        this.team = team;
        return this;
    }
}