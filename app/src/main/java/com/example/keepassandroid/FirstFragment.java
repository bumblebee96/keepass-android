package com.example.keepassandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import de.slackspace.openkeepass.domain.KeePassFile;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        final EditText passField = view.findViewById(R.id.passwordEditText);

        view.findViewById(R.id.unlock_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                unlock(passField.getText().toString());
            }
        });

        view.findViewById(R.id.browse_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openFilePicker();
            }
        });
    }

    private void unlock(String passwordAttempt)
    {
        //TODO: find better way to pass data
        MainActivity temp = (MainActivity) getActivity();

        if(temp.reader == null)
        {
            Toast myToast = Toast.makeText(getActivity(), "invalid database", Toast.LENGTH_SHORT);
            myToast.show();
        }
        else
        {
            try
            {
                KeePassFile database = temp.reader.openDatabase(passwordAttempt);

                FirstFragmentDirections.UnlockDatabase action = FirstFragmentDirections.unlockDatabase(passwordAttempt);
                NavHostFragment.findNavController(FirstFragment.this).navigate(action);
            }
            catch(Exception e)
            {
                Toast myToast = Toast.makeText(getActivity(), "wrong password", Toast.LENGTH_SHORT);
                myToast.show();
            }
        }

    }

    private void openFilePicker()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        getActivity().startActivityForResult(intent, MainActivity.READ_REQ);
    }
}
