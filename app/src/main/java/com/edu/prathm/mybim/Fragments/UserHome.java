package com.edu.prathm.mybim.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.prathm.mybim.Activities.CommentActivity;
import com.edu.prathm.mybim.Activities.SplashActivity;
import com.edu.prathm.mybim.R;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.edu.prathm.mybim.extra.FileOperator.getEntryOfSharedPreference;
import static com.edu.prathm.mybim.extra.key.*;

public class UserHome extends Fragment {
    OnUpdateProfileListner updateProfileListner;
    ImageButton profileButton;
    TextView name2, date_place;
Button commentButton;
    public UserHome() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        updateProfileListner = (OnUpdateProfileListner) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_home, container, false);
        name2 = (TextView) v.findViewById(R.id.name2);
        date_place = (TextView) v.findViewById(R.id.date_place);

commentButton = (Button) v.findViewById(R.id.commentButton);
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String firstName = sharedpreferences.getString(KEY_USER_FIRSTNAME, null);
        String lastName = sharedpreferences.getString(KEY_USER_LASTNAME, null);

        name2.setText(firstName + " " + lastName);
        date_place.setText("10 min - " + sharedpreferences.getString(KEY_USER_LOCATION, null));
        profileButton = (ImageButton) v.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileListner.showProfileSetting();
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CommentActivity.class));
            }
        });
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Home");

        } else {
        }
    }


}
