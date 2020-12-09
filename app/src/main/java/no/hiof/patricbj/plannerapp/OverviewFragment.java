package no.hiof.patricbj.plannerapp;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import no.hiof.patricbj.plannerapp.adapter.OverviewEventRecyclerAdapter;
import no.hiof.patricbj.plannerapp.model.Event;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private static final int READ_CALENDAR_PERMISSION_CODE = 101;

    public OverviewFragment() {
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
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @AfterPermissionGranted(READ_CALENDAR_PERMISSION_CODE)
    private void setEventView(View parentView) {
        RecyclerView overviewEventRecyclerView = parentView.findViewById(R.id.overviewEventRecyclerView);
        overviewEventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getContext() != null ) {
            if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_CALENDAR)) {
                Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
                overviewEventRecyclerView.setAdapter(new OverviewEventRecyclerAdapter(
                        parentView.getContext(),
                        Event.getEventsFromToday(parentView.getContext())));
            } else {
                if (getActivity() == null) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Asking for permission", Toast.LENGTH_SHORT).show();
                    EasyPermissions.requestPermissions(
                            getActivity(),
                            "We need permission",
                            READ_CALENDAR_PERMISSION_CODE,
                            Manifest.permission.READ_CALENDAR);
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView drawerBtn = view.findViewById(R.id.drawerBtn);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        drawerBtn.setOnClickListener(v -> {
            drawerLayout.open();
        });
        setEventView(view);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}