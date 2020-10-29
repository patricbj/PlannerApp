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

        try {
            viewHolder.setEvent(eventToDisplay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class OverviewEventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView, startDateView;

        public OverviewEventViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.titleView);
            startDateView = itemView.findViewById(R.id.startDateView);
        }

        public void setEvent(Event eventToDisplay) throws ParseException {
            titleView.setText(eventToDisplay.getTitle());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            Calendar cal = eventToDisplay.getStartDate();
            Date startDate = cal.getTime();
            startDateView.setText(dateFormat.format(startDate));
        }
    }
}
