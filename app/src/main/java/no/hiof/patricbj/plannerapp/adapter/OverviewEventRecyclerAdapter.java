package no.hiof.patricbj.plannerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import no.hiof.patricbj.plannerapp.R;
import no.hiof.patricbj.plannerapp.model.Event;

public class OverviewEventRecyclerAdapter extends RecyclerView.Adapter<OverviewEventRecyclerAdapter.OverviewEventViewHolder> {

    private List<Event> eventList;
    private LayoutInflater inflater;

    public OverviewEventRecyclerAdapter(Context context, List<Event> eventList) {
        inflater = LayoutInflater.from(context);
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public OverviewEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View eventView = inflater.inflate(R.layout.overview_event_list_item, parent, false);

        return new OverviewEventViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewEventViewHolder viewHolder, int position) {
        Event eventToDisplay = eventList.get(position);

        viewHolder.setEvent(eventToDisplay);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class OverviewEventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView, descView, startDateView, endDateView;

        public OverviewEventViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.titleView);
            descView = itemView.findViewById(R.id.descView);
            startDateView = itemView.findViewById(R.id.startDateView);
            endDateView = itemView.findViewById(R.id.endDateView);
        }

        public void setEvent(Event eventToDisplay) {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);

            Calendar start = eventToDisplay.getStartDate();
            Date startDate = start.getTime();

            Calendar end = eventToDisplay.getEndDate();
            Date endDate = end.getTime();

            titleView.setText(eventToDisplay.getTitle());
            descView.setText(eventToDisplay.getNote());
            startDateView.setText(dateTimeFormat.format(startDate));
            endDateView.setText(dateTimeFormat.format(endDate));
        }
    }
}
