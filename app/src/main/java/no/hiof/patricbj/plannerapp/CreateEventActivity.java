package no.hiof.patricbj.plannerapp;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.text.SimpleDateFormat;

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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class CreateEventActivity extends AppCompatActivity {

    private static final String TAG = "CreateEventActivity";

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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.setTitle("Set start time");
                timePickerDialog.updateTime(fromHour, fromMinute);
                timePickerDialog.show();
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.setTitle("Set end time");
                timePickerDialog.updateTime(fromHour, fromMinute);
                timePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
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
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long calID = 1;
                long startMillis = 0;
                long endMillis = 0;

                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2020, 10, 19, 20, 0);
                startMillis = beginTime.getTimeInMillis();

                Calendar endTime = Calendar.getInstance();
                endTime.set(2020, 10, 19, 22, 0);
                endMillis = endTime.getTimeInMillis();

                ContentResolver cr = getContentResolver();
                ContentValues cv = new ContentValues();
                cv.put(CalendarContract.Events.TITLE, String.valueOf(fieldTitle.getText()));
                cv.put(CalendarContract.Events.DESCRIPTION, String.valueOf(fieldNote.getText()));
                cv.put(CalendarContract.Events.CALENDAR_ID, calID);
                cv.put(CalendarContract.Events.DTSTART, startMillis);
                cv.put(CalendarContract.Events.DTEND, endMillis);
                cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
                // Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, cv);

                Toast.makeText(view.getContext(), cv.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
