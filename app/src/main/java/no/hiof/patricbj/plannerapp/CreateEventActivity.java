package no.hiof.patricbj.plannerapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.text.SimpleDateFormat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class CreateEventActivity extends AppCompatActivity {

    private static final String TAG = "CreateEventActivity";
    private static final int WRITE_CALENDAR_PERMISSION_CODE = 100;

    private int fromHour, fromMinute, toHour, toMinute;

    private Button btnStartDate;
    private Button btnStartTime;
    private Button btnEndDate;
    private Button btnEndTime;
    private Button btnCreateEvent;
    private EditText fieldTitle;
    private EditText fieldNote;
    private EditText fieldStartDate;
    private EditText fieldStartTime;
    private EditText fieldEndDate;
    private EditText fieldEndTime;
    private DatePickerDialog.OnDateSetListener startDatePickerSetListener;
    private DatePickerDialog.OnDateSetListener endDatePickerSetListener;
    private TimePickerDialog.OnTimeSetListener startTimePickerSetListener;
    private TimePickerDialog.OnTimeSetListener endTimePickerSetListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Event");

        btnStartDate = (Button) findViewById(R.id.btnFromDate);
        btnStartTime = (Button) findViewById(R.id.btnFromTime);
        btnEndDate = (Button) findViewById(R.id.btnToDate);
        btnEndTime = (Button) findViewById(R.id.btnToTime);
        btnCreateEvent = (Button) findViewById(R.id.btnCreateEvent);
        fieldTitle = (EditText) findViewById(R.id.fieldTitle);
        fieldNote = (EditText) findViewById(R.id.fieldNote);
        fieldStartDate = (EditText) findViewById(R.id.fieldFromDate);
        fieldStartTime = (EditText) findViewById(R.id.fieldFromTime);
        fieldEndDate = (EditText) findViewById(R.id.fieldEndDate);
        fieldEndTime = (EditText) findViewById(R.id.fieldEndTime);



        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        startDatePickerSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                month += 1;
                                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);

                                String date = dayOfMonth + "/" + month + "/" + year;
                                fieldStartDate.setText(date);
                            }
                        },
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.setTitle("Set start date");
                dialog.show();
            }
        });

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                fromHour = hourOfDay;
                                fromMinute = minute;

                                String time = fromHour + ":" + fromMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm", Locale.ENGLISH
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    assert date != null;
                                    fieldStartTime.setText(f24Hours.format(date));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 24, 0, true
                );
                try {
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                } catch (NullPointerException ignored) {}
                timePickerDialog.setTitle("Set start time");
                timePickerDialog.updateTime(fromHour, fromMinute);
                timePickerDialog.show();
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                toHour = hourOfDay;
                                toMinute = minute;

                                String time = toHour + ":" + toMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm", Locale.ENGLISH
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    assert date != null;
                                    fieldEndTime.setText(f24Hours.format(date));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 24, 0, true
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.setTitle("Set end time");
                timePickerDialog.updateTime(fromHour, fromMinute);
                timePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        endDatePickerSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                month += 1;
                                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);

                                String date = dayOfMonth + "/" + month + "/" + year;
                                fieldEndDate.setText(date);
                            }
                        },
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EasyPermissions.hasPermissions(view.getContext(), Manifest.permission.WRITE_CALENDAR)) {
                    Toast.makeText(CreateEventActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
                } else {
                    EasyPermissions.requestPermissions(CreateEventActivity.this, Manifest.permission.WRITE_CALENDAR, WRITE_CALENDAR_PERMISSION_CODE, Manifest.permission.WRITE_CALENDAR);
                }

                long calID = 1;

                DateFormat dateFormatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();

                if ((fieldStartDate.getText() != null) && (fieldStartTime.getText() != null)) {
                    try {
                        beginTime.setTime(dateFormatDateTime.parse(String.valueOf(fieldStartDate.getText() + " " + fieldStartTime.getText())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(view.getContext(), "Start date or time is empty", Toast.LENGTH_SHORT).show();
                }
//
                try {
                    endTime.setTime(dateFormatDateTime.parse(String.valueOf(fieldStartDate.getText() + " " + fieldStartTime.getText())));
                } catch (ParseException ignored) {

                }

                // Adding events using intent
//                Calendar endTime = Calendar.getInstance();
//                endTime.set(2020, 10, 19, 22, 0);
//                endMillis = endTime.getTimeInMillis();
//
//
//                Calendar cal = Calendar.getInstance();
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setType("vnd.android.cursor.item/event");
//                intent.putExtra("beginTime", startMillis);
//                intent.putExtra("allDay", false);
//                intent.putExtra("rrule", "FREQ=YEARLY");
//                intent.putExtra("endTime", endMillis);
//                intent.putExtra("title", "A test event from PlannerApp");
//                startActivity(intent);

                ContentResolver cr = getContentResolver();
                ContentValues cv = new ContentValues();
                cv.put(CalendarContract.Events.TITLE, String.valueOf(fieldTitle.getText()));
                cv.put(CalendarContract.Events.DESCRIPTION, String.valueOf(fieldNote.getText()));
                cv.put(CalendarContract.Events.CALENDAR_ID, calID);
                cv.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                cv.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);
                if (uri != null) {
                    if (uri.getLastPathSegment() != null) {
                        long eventID = Long.parseLong(uri.getLastPathSegment());
                        Toast.makeText(view.getContext(), "Created event with ID " + eventID, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Something wrong happened :(", Toast.LENGTH_LONG).show();
                    }
                }

                finish();
                overridePendingTransition(R.anim.hold, R.anim.exit_bottom);
            }
        });
    }

    // From EasyPermisson Github guide
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
