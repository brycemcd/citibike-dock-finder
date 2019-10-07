package com.github.brycemcd.bikedockfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CitiBikeStationAdapter extends RecyclerView.Adapter<CitiBikeStationAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView docksCount;
        public TextView distanceToDock;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.station_name);
            docksCount = (TextView) itemView.findViewById(R.id.docksCount);
            distanceToDock = (TextView) itemView.findViewById(R.id.distanceToDock);
        }
    }

    private List<CitiBikeStation> citiBikeStations;
    public CitiBikeStationAdapter(List<CitiBikeStation> citiBikeStationList) {
        citiBikeStations = citiBikeStationList;
    }

    @Override
    public CitiBikeStationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_station, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CitiBikeStationAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        CitiBikeStation citiBikeStation = citiBikeStations.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(citiBikeStation.getStationName());

        TextView docksCount = viewHolder.docksCount;
        docksCount.setText(Integer.toString(citiBikeStation.getDocksAvailable()));

        TextView distanceToDock = viewHolder.distanceToDock;
        distanceToDock.setText(Double.toString(Math.round(citiBikeStation.getDistanceAway())));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return citiBikeStations.size();
    }
}
