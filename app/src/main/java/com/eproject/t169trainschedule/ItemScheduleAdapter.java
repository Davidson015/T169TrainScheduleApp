package com.eproject.t169trainschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eproject.t169trainschedule.model.Schedule;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemScheduleAdapter extends RecyclerView.Adapter<ItemScheduleAdapter.ScheduleViewHolder> {

    List<Schedule> schedules;

    public ItemScheduleAdapter(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        // Instantiating the views
        TextView stationName;
        ImageView stationImage;
        TextView arrTime;
        TextView depTime;
        TextView runningTime;
        TextView timeFromLast;
        TextView totalDist;
        TextView distFromLast;
        TextView hardSeat;
        TextView hardSleeper;
        TextView softSleeper;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            // Initializing the views
            stationImage = itemView.findViewById(R.id.card_img);
            stationName = itemView.findViewById(R.id.station_name);
            arrTime = itemView.findViewById(R.id.arr_time);
            depTime = itemView.findViewById(R.id.dep_time);
            runningTime = itemView.findViewById(R.id.running_time);
            timeFromLast = itemView.findViewById(R.id.time_from_last);
            totalDist = itemView.findViewById(R.id.total_dist);
            distFromLast = itemView.findViewById(R.id.dist_from_last);
            hardSeat = itemView.findViewById(R.id.hard_seat_price);
            hardSleeper = itemView.findViewById(R.id.hard_sleeper_price);
            softSleeper = itemView.findViewById(R.id.soft_sleeper_price);
        }
    }

    @NonNull
    @Override
    public ItemScheduleAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        ScheduleViewHolder svh = new ScheduleViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemScheduleAdapter.ScheduleViewHolder holder, int position) {
//        holder.stationImage.setImageResource(schedules.get(position).getStationImage());
        Picasso.get().load(schedules.get(position).getStationImage()).into(holder.stationImage);
        holder.stationName.setText(schedules.get(position).getStationName());
        holder.arrTime.setText(schedules.get(position).getArrTime());
        holder.depTime.setText(schedules.get(position).getDepTime());
        holder.runningTime.setText(schedules.get(position).getRunningTime());
        holder.timeFromLast.setText(schedules.get(position).getTimeFromLast());
        holder.totalDist.setText(schedules.get(position).getTotalDist());
        holder.distFromLast.setText(schedules.get(position).getDistFromLast());
        holder.hardSeat.setText(schedules.get(position).getHardSeat());
        holder.hardSleeper.setText(schedules.get(position).getHardSleeper());
        holder.softSleeper.setText(schedules.get(position).getSoftSleeper());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }
}
