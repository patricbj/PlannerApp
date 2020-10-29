package no.hiof.patricbj.plannerapp;

import android.Manifest;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import no.hiof.patricbj.plannerapp.adapter.OverviewEventRecyclerAdapter;
import no.hiof.patricbj.plannerapp.model.Event;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class OverviewFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int READ_CALENDAR_PERMISSION_CODE = 101;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Overview.
     */
    // TODO: Rename and change types and number of parameters
    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        /*
        RecyclerView overviewEventRecyclerView = getView().findViewById(R.id.overviewEventRecyclerView);
        overviewEventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_CALENDAR)) {
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
            overviewEventRecyclerView.setAdapter(new OverviewEventRecyclerAdapter(getContext(), Event.getEvents(getContext(), getActivity())));
        } else {
            EasyPermissions.requestPermissions(OverviewFragment.this, Manifest.permission.READ_CALENDAR, READ_CALENDAR_PERMISSION_CODE);
        }
        */

        return view;
    }
}