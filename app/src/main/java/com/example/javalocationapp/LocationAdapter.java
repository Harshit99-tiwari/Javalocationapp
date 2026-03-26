package com.example.javalocationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.widget.Button;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ArrayList<locationmodel> locationList;

    public LocationAdapter(ArrayList<locationmodel> locationList) {
        this.locationList = locationList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLat, tvLon, tvAddress;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvLat = itemView.findViewById(R.id.tvLat);
            tvLon = itemView.findViewById(R.id.tvLon);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        locationmodel location = locationList.get(position);

        holder.tvLat.setText("📍 Lat: " + location.latitude);
        holder.tvLon.setText("🌍 Lon: " + location.longitude);
        holder.tvAddress.setText("🏠 " + location.address);
        holder.btnDelete.setOnClickListener(v -> {
            locationList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
