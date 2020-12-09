package no.hiof.patricbj.plannerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import no.hiof.patricbj.plannerapp.R;
import no.hiof.patricbj.plannerapp.model.Event;

public class OverviewEventRecyclerAdapter extends RecyclerView.Adapter<OverviewEventRecyclerAdapter.OverviewEventViewHolder> {

    private final List<Event> eventList;
    private final LayoutInflater inflater;

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

    private void removeEvent(int position, Context context) {
        Event eventToDelete = eventList.get(position);
        eventToDelete.delete(context);
        eventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eventList.size());
    }

    private void editEvent(Event event) {

    }

    public class OverviewEventViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView, descView, startDateView, endDateView;
        private int position;
        private Event event;

        public OverviewEventViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.titleView);
            descView = itemView.findViewById(R.id.descView);
            startDateView = itemView.findViewById(R.id.startDateView);
            endDateView = itemView.findViewById(R.id.endDateView);
            ImageView deleteEventBtn = itemView.findViewById(R.id.deleteEventBtn);
            //editEventBtn = itemView.findViewById(R.id.editEventBtn);

            deleteEventBtn.setOnClickListener(v -> removeEvent(position, itemView.getContext()));
        }

        public void setEvent(Event eventToDisplay) {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);

            Calendar start = eventToDisplay.getStartDate();
            Date startDate = start.getTime();

            Calendar end = eventToDisplay.getEndDate();
            Date endDate = end.getTime();

            titleView.setText(eventToDisplay.getTitle());
            if (eventToDisplay.getNote() == null || eventToDisplay.getNote().equals("")) {
                descView.setVisibility(View.GONE);
            } else {
                descView.setText(eventToDisplay.getNote());
            }
            startDateView.setText(dateTimeFormat.format(startDate));
            endDateView.setText(dateTimeFormat.format(endDate));

            this.event = eventToDisplay;
        }
    }
}
