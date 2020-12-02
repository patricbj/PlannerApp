package no.hiof.patricbj.plannerapp.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Event {

    private String title, note;
    private Calendar startDate, endDate;

    private Event(String title, String note, Calendar startDate, Calendar endDate) {
        this.title = title;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    /* TODO: Finne ut hvorfor contentResolver.query returnerer uten data i begge metodene */

    public static ArrayList<Event> getEventsFromToday(Context context) {

        Date date = Calendar.getInstance().getTime();
        Calendar today = Calendar.getInstance();
        today.setTime(date);

        today.set(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0);

        String[] projection = {
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
        };

        String DTSTART = CalendarContract.Events.DTSTART;

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                projection,
                DTSTART + " >= " + today.getTimeInMillis(),
                null,
                DTSTART + " ASC");

        return createEvents(cursor);
    }

    public static ArrayList<Event> getEventsOnDate(Context context, Calendar selectedDate) {

        String[] projection = {
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
        };

        String DTSTART = CalendarContract.Events.DTSTART;

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                projection,
                DTSTART + " >= " +
                        selectedDate.getTimeInMillis() + " AND " +
                        DTSTART + " < " +
                        (selectedDate.getTimeInMillis() - 86400000),
                null,
                 DTSTART + " ASC");

        return createEvents(cursor);
    }

    private static ArrayList<Event> createEvents(Cursor cursor) {
        ArrayList<Event> eventList = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                for (int i = 0; i < cursor.getCount(); i++) {
                    Calendar startDate = Calendar.getInstance();
                    startDate.setTimeInMillis(cursor.getLong(2));
                    Calendar endDate = Calendar.getInstance();
                    endDate.setTimeInMillis(cursor.getLong(3));

                    Event event = new Event(
                            cursor.getString(0),
                            cursor.getString(1),
                            startDate,
                            endDate);

                    eventList.add(event);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return eventList;
    }
}
