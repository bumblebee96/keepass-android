package com.example.keepassandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;

public class SecondFragment extends Fragment {
    private DataEntriesAdapter entriesAdapter;
    private KeePassFile database;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //TODO: find better way to pass data
        MainActivity temp = (MainActivity) getActivity();
        String password = SecondFragmentArgs.fromBundle(getArguments()).getPassword();
        database = temp.reader.openDatabase(password);
        List<Entry> entries = database.getEntries();

        displayDatabase(view, entries);

        view.findViewById(R.id.lock_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lock();
            }
        });

        view.findViewById(R.id.auto_type_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                autoType(view);
            }
        });
    }

    private void lock()
    {
        MainActivity temp = (MainActivity) getActivity();
        temp.reader = null;

        NavDirections action = SecondFragmentDirections.lockDatabase();
        NavHostFragment.findNavController(SecondFragment.this).navigate(action);
    }

    private void autoType(View view)
    {
        //TODO: make this output as usb keyboard
        Entry selectedEntry = entriesAdapter.getSelectedEntry();

        if(selectedEntry == null)
        {
            Snackbar.make(view, "select an entry to auto type", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else
        {
            String selection = selectedEntry.getTitle() + " : " + selectedEntry.getPassword();
            Snackbar.make(view, selection, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void displayDatabase(View view, List<Entry> entries)
    {
        RecyclerView recyclerView = view.findViewById(R.id.data_entries);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        entriesAdapter = new DataEntriesAdapter(entries);
        recyclerView.setAdapter(entriesAdapter);

    }
}
