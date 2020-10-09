package no.hiof.patricbj.plannerapp;

import android.text.format.Time;

import java.util.Date;

public class Event {

    private String title;
    private String note;
    private Date fromDate;
    private Time fromTime;
    private Date toDate;
    private Time toTime;

    private Event(String title, String note, Date fromDate, Time fromTime, Date toDate, Time toTime) {
        this.title = title;
        this.note = note;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }
}
