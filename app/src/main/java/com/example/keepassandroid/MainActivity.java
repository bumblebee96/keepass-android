package com.example.keepassandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.InputStream;

import de.slackspace.openkeepass.KeePassDatabase;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static final int READ_REQ = 41;
    Uri databaseURI = null;
    KeePassDatabase reader = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (resultData != null)
            {
                databaseURI = resultData.getData();

                TextView databasePath = findViewById(R.id.filepath);
                databasePath.setText(databaseURI.getPath());
            }

            if(requestCode == READ_REQ)
            {
                Toast myToast = Toast.makeText(MainActivity.this, "reading file", Toast.LENGTH_SHORT);
                myToast.show();
                readKdbxFile(databaseURI);
            }
        }
    }

    private void readKdbxFile(Uri uri)
    {
        InputStream inputStream = null;

        try
        {
            inputStream = getContentResolver().openInputStream(uri);

            reader = KeePassDatabase.getInstance(inputStream);
            inputStream.close();
        }
        catch (Exception e)
        {
            reader = null;
            e.printStackTrace();
        }
    }
}
