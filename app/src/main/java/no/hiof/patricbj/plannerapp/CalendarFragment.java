package no.hiof.patricbj.plannerapp;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

import no.hiof.patricbj.plannerapp.adapter.CalendarEventRecyclerAdapter;
import no.hiof.patricbj.plannerapp.adapter.OverviewEventRecyclerAdapter;
import no.hiof.patricbj.plannerapp.model.Event;
import pub.devrel.easypermissions.EasyPermissions;

public class CalendarFragment extends Fragment {

    private int READ_CALENDAR_PERMISSION_CODE = 101;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();

        RecyclerView calendarEventRecyclerView = view.findViewById(R.id.calendarEventRecyclerView);
        calendarEventRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if (EasyPermissions.hasPermissions(view.getContext(), Manifest.permission.READ_CALENDAR)) {
            Toast.makeText(view.getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
            calendarEventRecyclerView.setAdapter(new CalendarEventRecyclerAdapter(view.getContext(), Event.getEvents(view.getContext(), this.getActivity())));
        } else {
            EasyPermissions.requestPermissions(CalendarFragment.this, Manifest.permission.READ_CALENDAR, READ_CALENDAR_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}