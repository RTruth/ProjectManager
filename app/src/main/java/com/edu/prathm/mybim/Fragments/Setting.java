package com.edu.prathm.mybim.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.prathm.mybim.Activities.Login;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.L;
import com.edu.prathm.mybim.pojo.User;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.edu.prathm.mybim.extra.key.CONSTANT_NA;
import static com.edu.prathm.mybim.extra.key.KEY_FALSE;
import static com.edu.prathm.mybim.extra.key.KEY_TRUE;
import static com.edu.prathm.mybim.extra.key.KEY_USER_DATE_OF_BIRTH;
import static com.edu.prathm.mybim.extra.key.KEY_USER_EMAIL;
import static com.edu.prathm.mybim.extra.key.KEY_USER_FIRSTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_GENDER;
import static com.edu.prathm.mybim.extra.key.KEY_USER_ID;
import static com.edu.prathm.mybim.extra.key.KEY_USER_IS_LOGIN;
import static com.edu.prathm.mybim.extra.key.KEY_USER_LASTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_LOCATION;
import static com.edu.prathm.mybim.extra.key.KEY_USER_PASSWORD;
import static com.edu.prathm.mybim.extra.key.KEY_USER_PROFILE_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Setting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Setting extends Fragment implements View.OnClickListener {
TextView logOut,about;

    public Setting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_setting, container, false);
logOut= (TextView) v.findViewById(R.id.logOut);
        about= (TextView) v.findViewById(R.id.about);
logOut.setOnClickListener(this);
   about.setOnClickListener(this);
        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Setting");

        }
        else {  }
    }

    @Override
    public void onClick(View v) {

        if(v == logOut)
        {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            // Setting Dialog Title
            alertDialog.setTitle("Confirm Logout...");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to log out?");

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.`);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {

                    addDummyEntryIntoSharedPreference();
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    private void addDummyEntryIntoSharedPreference() {
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(KEY_USER_IS_LOGIN, KEY_FALSE);


        editor.putInt(KEY_USER_ID, -1);
        editor.putString(KEY_USER_FIRSTNAME, CONSTANT_NA);
        editor.putString(KEY_USER_LASTNAME, CONSTANT_NA);
        editor.putString(KEY_USER_EMAIL, CONSTANT_NA);
        editor.putString(KEY_USER_PASSWORD, CONSTANT_NA);
        editor.putString(KEY_USER_GENDER, CONSTANT_NA);

            editor.putString(KEY_USER_DATE_OF_BIRTH,CONSTANT_NA);
           // L.t(Login.this, dateString);

        editor.putString(KEY_USER_DATE_OF_BIRTH, CONSTANT_NA);
        editor.putString(KEY_USER_LOCATION, CONSTANT_NA);
        editor.putString(KEY_USER_PROFILE_IMAGE, CONSTANT_NA);

        editor.commit();

    }
}
