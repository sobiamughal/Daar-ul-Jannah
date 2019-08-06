package com.example.roubaisha.counter.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roubaisha.counter.R;

import org.json.JSONArray;
import org.json.JSONException;

public class PendingPrayerAdapter extends RecyclerView.Adapter<PendingPrayerAdapter.PendingPrayerViewHolder> {
    private JSONArray mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PendingPrayerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txtPendingPrayerName;
        private TextView txtPendingPrayerDate;

        public PendingPrayerViewHolder(View v) {
            super(v);
            txtPendingPrayerName = itemView.findViewById(R.id.txtPendingPrayerName);
            txtPendingPrayerDate = itemView.findViewById(R.id.txtPendingPrayerDate);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PendingPrayerAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PendingPrayerAdapter.PendingPrayerViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_prayer_item, parent, false);

        return new PendingPrayerViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PendingPrayerViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            String PrayerName = GetPrayerName(Integer.parseInt(mDataset.getJSONObject(position).getString("PrayerId")));
            holder.txtPendingPrayerName.setText(PrayerName);
            holder.txtPendingPrayerDate.setText(mDataset.getJSONObject(position).getString("CreatedAt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }

    public String GetPrayerName(int PrayerId)
    {
        String PrayerName = "";
        if(PrayerId == 1)
            PrayerName = "Fajr";
        else if(PrayerId == 2)
            PrayerName = "Zuhr";
        else if(PrayerId == 3)
            PrayerName = "Asar";
        else if(PrayerId == 4)
            PrayerName = "Maghrib";
        else if(PrayerId == 5)
            PrayerName = "Isha";
        return PrayerName;
    }
}
