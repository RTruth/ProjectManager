package com.edu.prathm.mybim.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edu.prathm.mybim.Activities.ProfileActivity;
import com.edu.prathm.mybim.Activities.ProjectDetails;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.L;

import static com.edu.prathm.mybim.extra.key.*;

public class Profile extends Fragment {
    OnUpdateProfileListner updateProfileListner;
    private ImageButton profileButton;
    private TextView name2, date_place, name, profile_id, profile_dept, profile_desn, profile_date;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        updateProfileListner = (OnUpdateProfileListner) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        profileButton = (ImageButton) v.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileListner.showProfileSetting();
            }
        });

        name2 = (TextView) v.findViewById(R.id.name2);
        date_place = (TextView) v.findViewById(R.id.date_place);
        name = (TextView) v.findViewById(R.id.name);
        profile_id = (TextView) v.findViewById(R.id.profile_id);
        profile_dept = (TextView) v.findViewById(R.id.profile_dept);
        profile_desn = (TextView) v.findViewById(R.id.profile_desn);
        profile_date = (TextView) v.findViewById(R.id.profile_date);


        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String firstName = sharedpreferences.getString(KEY_USER_FIRSTNAME, null);
        String lastName = sharedpreferences.getString(KEY_USER_LASTNAME, null);
        String id = ""+sharedpreferences.getInt(KEY_USER_ID,-1);
        String role = sharedpreferences.getString(KEY_USER_ROLE, null);
        //String department = sharedpreferences.getString(KEY_USER_, null);
        String dob = sharedpreferences.getString(KEY_USER_DATE_OF_BIRTH, null);
        String location = sharedpreferences.getString(KEY_USER_LOCATION, null);
        String profileImg = sharedpreferences.getString(KEY_USER_PROFILE_IMAGE, null);

        name.setText(firstName + " " + lastName);
        name2.setText(firstName + " " + lastName);
        profile_id.setText(id);
        profile_dept.setText("IT Department");

        profile_desn.setText(role);
        profile_date.setText(dob);
        date_place.setText("10 min - " + sharedpreferences.getString(KEY_USER_LOCATION, null));


        return v;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Profile");

        } else {
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_frag, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                //do something
                return true;
            case R.id.action_add_project:
                //do something
                startActivity(new Intent(getActivity(), ProjectDetails.class));
                return true;
            case R.id.action_update_proile:
                //do something
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

}
