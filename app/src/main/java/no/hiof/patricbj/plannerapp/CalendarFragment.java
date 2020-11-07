package no.hiof.patricbj.plannerapp;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import no.hiof.patricbj.plannerapp.model.Event;
import pub.devrel.easypermissions.EasyPermissions;

public class CalendarFragment extends Fragment {

    private static final int READ_CALENDAR_PERMISSION_CODE = 101;

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

        final RecyclerView calendarEventRecyclerView = view.findViewById(R.id.calendarEventRecyclerView);
        calendarEventRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth, 0, 0, 0);
                if (EasyPermissions.hasPermissions(view.getContext(), Manifest.permission.READ_CALENDAR)) {
                calendarEventRecyclerView.setAdapter(new CalendarEventRecyclerAdapter(
                        view.getContext(),
                        Event.getEventsOnDate(view.getContext(), selectedDate)));
                } else {
                    if (getActivity() == null) {
                        Toast.makeText(view.getContext(), "Calendar: Activity is null", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Asking for permission", Toast.LENGTH_SHORT).show();
                        EasyPermissions.requestPermissions(
                                getActivity(),
                                Manifest.permission.READ_CALENDAR,
                                READ_CALENDAR_PERMISSION_CODE,
                                Manifest.permission.READ_CALENDAR);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}