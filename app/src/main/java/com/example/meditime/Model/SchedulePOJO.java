package com.example.meditime.Model;

public class SchedulePOJO {
    private String id, schedule_name, schedule_time;

    public SchedulePOJO(String id, String schedule_name, String schedule_time) {
        this.id = id;
        this.schedule_name = schedule_name;
        this.schedule_time = schedule_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }
}
