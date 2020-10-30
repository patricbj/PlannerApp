package no.hiof.patricbj.plannerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Calendar;

import pub.devrel.easypermissions.EasyPermissions;

public class CalendarFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
            }
        });

        /*
        RecyclerView calendarEventRecyclerView = view.findViewById(R.id.calendarEventRecyclerView);
        calendarEventRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if (EasyPermissions.hasPermissions(view.getContext(), Manifest.permission.READ_CALENDAR)) {
            Toast.makeText(view.getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
            calendarEventRecyclerView.setAdapter(new CalendarEventRecyclerAdapter(view.getContext(), Event.getEvents(view.getContext(), this.getActivity())));
        } else {
            EasyPermissions.requestPermissions(CalendarFragment.this, Manifest.permission.READ_CALENDAR, READ_CALENDAR_PERMISSION_CODE);
        }
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}