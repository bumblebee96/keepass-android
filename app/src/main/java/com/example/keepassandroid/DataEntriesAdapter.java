package com.example.keepassandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.slackspace.openkeepass.domain.Entry;

public class DataEntriesAdapter extends RecyclerView.Adapter<DataEntriesAdapter.MyViewHolder>
{
    private List<Entry> mEntries;
    private int selectedEntry;

    public DataEntriesAdapter(List<Entry> myDataset)
    {
        selectedEntry = -1;
        mEntries = myDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public View rowItem;
        public TextView title;
        public TextView user;
        public TextView pass;

        public MyViewHolder(View v)
        {
            super(v);

            rowItem = v;
            title = v.findViewById(R.id.entry_title);
            user = v.findViewById(R.id.entry_user);
            pass = v.findViewById(R.id.entry_pass);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        holder.rowItem.setActivated(position == selectedEntry);
        holder.rowItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (position == selectedEntry)
                {
                    selectedEntry = -1;
                    notifyItemChanged(position);
                }
                else {
                    int prevSelected = selectedEntry;
                    selectedEntry = position;
                    notifyItemChanged(selectedEntry);
                    notifyItemChanged(prevSelected);
                }
            }
        });

        holder.title.setText(mEntries.get(position).getTitle());
        holder.user.setText(mEntries.get(position).getUsername());
        holder.pass.setText(mEntries.get(position).getPassword());
    }

    @Override
    public int getItemCount()
    {
        return mEntries.size();
    }

    public Entry getSelectedEntry()
    {
        if(selectedEntry == -1)
            return null;

        return mEntries.get(selectedEntry);
    }
}