package no.hiof.patricbj.plannerapp.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Event {

    private final String id;
    private String title;
    private String note;
    private Calendar startDate, endDate;

    private Event(String id, String title, String note, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
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

    public void delete(Context context) {
        String[] selArgs =
                new String[]{this.id};
        context.getContentResolver().
                delete(
                        CalendarContract.Events.CONTENT_URI,
                        CalendarContract.Events._ID + " = ? ",
                        selArgs);
    }

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
                CalendarContract.Events._ID,
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
                CalendarContract.Events._ID,
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
                        (selectedDate.getTimeInMillis() + 86400000),
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
                    startDate.setTimeInMillis(cursor.getLong(3));
                    Calendar endDate = Calendar.getInstance();
                    endDate.setTimeInMillis(cursor.getLong(4));

                    Event event = new Event(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
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
