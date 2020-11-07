package no.hiof.patricbj.plannerapp.model;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Event {

    private static final int READ_CALENDAR_PERMISSION_CODE = 101;

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

    public static ArrayList<Event> getEventsFromToday(Context context) {
        // TODO: Need to find a way to get Events from a Calendar when a date is selected in CalendarView

        ArrayList<Event> eventList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        Calendar today = Calendar.getInstance();

        String[] projection = {
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
        };

        String DTSTART = CalendarContract.Events.DTSTART;

        Cursor cursor = contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                projection,
                DTSTART + " > " + today.getTimeInMillis(),
                null,
                DTSTART + " ASC");

        if (cursor != null) {
            cursor.moveToFirst();

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
        return eventList;
    }

    public static ArrayList<Event> getEventsOnDate(Context context, Calendar selectedDate) {

        ArrayList<Event> eventList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = {
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND
        };

        String DTSTART = CalendarContract.Events.DTSTART;

        Cursor cursor = contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                projection,
                DTSTART + " > " + selectedDate.getTimeInMillis() + " AND " +
                        DTSTART + " <= " + (selectedDate.getTimeInMillis() + 86399999 /* 23 hours 59 min 59 sec 999 ms */),
                null,
                CalendarContract.Events.DTSTART + " ASC");

        if (cursor != null) {

            cursor.moveToFirst();

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
        return eventList;
    }
}
