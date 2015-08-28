package com.edu.prathm.mybim.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.edu.prathm.mybim.R;

import static com.edu.prathm.mybim.extra.key.KEY_USER_FIRSTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_ID;
import static com.edu.prathm.mybim.extra.key.KEY_USER_LASTNAME;

public class ProfileActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private String firstName;
    private String lastName,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar =(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        firstName = sharedpreferences.getString(KEY_USER_FIRSTNAME, null);
         lastName = sharedpreferences.getString(KEY_USER_LASTNAME, null);
         id = sharedpreferences.getString(KEY_USER_ID,null);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(firstName +" "+ lastName);
        toolbar.setSubtitle(id);

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
}
