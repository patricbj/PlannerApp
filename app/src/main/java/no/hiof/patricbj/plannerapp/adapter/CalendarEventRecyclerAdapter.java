package no.hiof.patricbj.plannerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.hiof.patricbj.plannerapp.R;
import no.hiof.patricbj.plannerapp.model.Event;

public class CalendarEventRecyclerAdapter extends RecyclerView.Adapter<CalendarEventRecyclerAdapter.CalendarEventViewHolder> {

    private List<Event> eventList;
    private LayoutInflater inflater;

    public CalendarEventRecyclerAdapter(Context context, List<Event> eventList) {
        inflater = LayoutInflater.from(context);
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public CalendarEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View eventView = inflater.inflate(R.layout.calendar_event_list_item, parent, false);

        return new CalendarEventViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarEventViewHolder viewHolder, int position) {
        Event eventToDisplay = eventList.get(position);

        viewHolder.setEvent(eventToDisplay);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class CalendarEventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, timeTextView;

        public CalendarEventViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }

        public void setEvent(Event eventToDisplay) {
            titleTextView.setText(eventToDisplay.getTitle());
        }
    }
}