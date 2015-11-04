package com.project.workgroup.piuradivierte.view.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.workgroup.model.entidades.Event;
import com.project.workgroup.piuradivierte.R;
import com.project.workgroup.piuradivierte.util.RecyclerViewClickListener;

import java.util.List;

/**
 * Created by Junior on 15/10/2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private Context mContext;
    private List<Event> mEventList;
    private RecyclerViewClickListener mRecyclerViewClickListener;


    public EventsAdapter(List<Event> mEventList){
        this.mEventList = mEventList;
    }
    public List<Event> getEventList(){
        return mEventList;
    }
    public void setRecyclerlistListener(RecyclerViewClickListener mRecyclerlistListener){
        this.mRecyclerViewClickListener =  mRecyclerlistListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event , parent, false);
        this.mContext = parent.getContext();
        return new EventViewHolder(view , mRecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {
        Event selectedEvent  = mEventList.get(position);
        holder.titleEvent.setText(selectedEvent.getTitle());




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.imgEvent.setTransitionName("cover" + position);

    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
    public void appendEvent(List<Event> eventList){
        mEventList.addAll(eventList);
        notifyDataSetChanged();
    }
}

class EventViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{

    private final RecyclerViewClickListener onClickListener;
    TextView horaEvent;
    TextView titleEvent;
    TextView lugarEvent;
    ImageView imgEvent;


    public EventViewHolder(View itemView, RecyclerViewClickListener onClickListener) {
        super(itemView);

        horaEvent = (TextView) itemView.findViewById(R.id.fecha_event);
        titleEvent = (TextView) itemView.findViewById(R.id.title_bar);
        lugarEvent = (TextView) itemView.findViewById(R.id.lugar_event);
        imgEvent = (ImageView) itemView.findViewById(R.id.image_event);
        imgEvent.setDrawingCacheEnabled(true);
        imgEvent.setOnTouchListener(this);
        this.onClickListener = onClickListener;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_MOVE) {

            onClickListener.onClick(v, getPosition(), event.getX(), event.getY());
        }
        return true;
    }
}