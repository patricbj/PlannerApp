package no.hiof.patricbj.plannerapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import no.hiof.patricbj.plannerapp.adapter.OverviewEventRecyclerAdapter;
import no.hiof.patricbj.plannerapp.adapter.PageAdapter;
import no.hiof.patricbj.plannerapp.model.Event;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CALENDAR_PERMISSION_CODE = 101;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabCalendar, tabOverview;
    private CalendarView calendarView;

    public PageAdapter pageradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabCalendar = (TabItem) findViewById(R.id.tabCalendar);
        tabOverview = (TabItem) findViewById(R.id.tabOverview);
        viewPager = findViewById(R.id.viewpager);
        FloatingActionButton addEventButton = findViewById(R.id.addEventButton);

        pageradapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageradapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageradapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pageradapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createEventIntent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(createEventIntent);
                overridePendingTransition(R.anim.enter_bottom, R.anim.hold);
            }
        });
    }
}