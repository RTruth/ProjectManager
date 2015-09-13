package com.edu.prathm.mybim.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.prathm.mybim.Activities.GPSTracker;
import com.edu.prathm.mybim.Activities.ProfileActivity;
import com.edu.prathm.mybim.Activities.ProjectDetails;
import com.edu.prathm.mybim.ImageViewFragment;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.FileOperator;
import com.edu.prathm.mybim.extra.L;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.edu.prathm.mybim.extra.key.*;

public class Profile extends Fragment implements View.OnClickListener {
    OnUpdateProfileListner updateProfileListner;
    ImageView profile, avatar;
    private ImageButton profileButton;
    private TextView name2, date_place, name, profile_id, profile_dept, profile_desn, profile_date,place;
GPSTracker gps;
    String cityName,stateName,countryName;
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

        gps = new GPSTracker(activity);
        Geocoder geocoder = new Geocoder(activity, Locale.ENGLISH);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();


            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addresses!=null) {
                Log.d(""+Profile.class.getName(),"got Some address"+addresses);
               cityName = addresses.get(0).getSubAdminArea();
                Log.d("Profile",cityName);
                //stateName = addresses.get(0).getAddressLine(1);
                //countryName = addresses.get(0).getAddressLine(2);
            } // \n is for new line

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

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
        place = (TextView) v.findViewById(R.id.place);
        profile = (ImageView) v.findViewById(R.id.profile_img);
        avatar = (ImageView) v.findViewById(R.id.pro_img);
place.setText(cityName);
        profile.setOnClickListener(this);
        avatar.setOnClickListener(this);
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String firstName = sharedpreferences.getString(KEY_USER_FIRSTNAME, null);
        String lastName = sharedpreferences.getString(KEY_USER_LASTNAME, null);
        String id = sharedpreferences.getString(KEY_USER_ID, null);
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
      //  date_place.setText("10 min - " + sharedpreferences.getString(KEY_USER_LOCATION, null));


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (FileOperator.getEntryOfSharedPreference(getActivity(), KEY_LOCAL_PIC_PATH) != null) {


            Bitmap bmImg = BitmapFactory.decodeFile(FileOperator.getEntryOfSharedPreference(getActivity(), KEY_LOCAL_PIC_PATH));
            if (bmImg != null) {
                profile.setImageBitmap(bmImg);
                avatar.setImageBitmap(bmImg);
            }
        }
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

    @Override
    public void onClick(View view) {
        Log.e("image","imageClicked");
       ImageViewFragment imageViewFragment=new ImageViewFragment();
        imageViewFragment.show(getChildFragmentManager(),"ImageDialog");


    }
}
