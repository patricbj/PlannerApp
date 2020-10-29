package no.hiof.patricbj.plannerapp.model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import no.hiof.patricbj.plannerapp.CalendarFragment;
import no.hiof.patricbj.plannerapp.MainActivity;
import pub.devrel.easypermissions.EasyPermissions;

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

    public static ArrayList<Event> getEvents(Context context, Activity activity ) {
        // TODO: Need to find a way to get Events from a Calendar when a date is selected in CalendarView

        ArrayList<Event> eventList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(CalendarContract.Instances.CONTENT_URI, new String[]{"title", "description", "dtstart", "dtend"}, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int[] CalIDs = new int[cursor.getCount()];

        for (int i = 0; i < CalIDs.length; i++) {
            CalIDs[i] = cursor.getInt(0);
            Calendar startDate = Calendar.getInstance();
            startDate.setTimeInMillis(cursor.getLong(3));
            Calendar endDate = Calendar.getInstance();
            endDate.setTimeInMillis(cursor.getLong(4));
            Event event = new Event(
                    cursor.getString(1),
                    cursor.getString(2),
                    startDate,
                    endDate);
            eventList.add(event);
            Toast.makeText(context, "Event: " + CalIDs[i] + " | Title: " + event.getTitle(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        cursor.close();

        return eventList;
        /*
        Calendar startDate = Calendar.getInstance();
        startDate.set(2020, 10 - 1, 28, 21, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 10 - 1, 28, 22, 0);

        Event event1 = new Event("Lage Lasagne","spise etterpå", startDate, endDate);

        eventList.add(event1);
        */
    }

    public static ArrayList<Event> getEventsOnDate() {

        ArrayList<Event> eventList = new ArrayList<>();

        return eventList;
    }
}
