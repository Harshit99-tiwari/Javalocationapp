package com.example.javalocationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    ArrayList<locationmodel> locationList;

    public LocationAdapter(ArrayList<locationmodel> locationList) {
        this.locationList = locationList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLat, tvLon, tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            tvLat = itemView.findViewById(R.id.tvLat);
            tvLon = itemView.findViewById(R.id.tvLon);
            tvAddress = itemView.findViewById(R.id.tvAddress);
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
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
