package com.edu.prathm.mybim.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.L;
import com.edu.prathm.mybim.network.VollySingleton;
import com.edu.prathm.mybim.pojo.User;
import com.google.android.gms.games.internal.api.RequestsImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.edu.prathm.mybim.URL.LOGIN_URL;
import static com.edu.prathm.mybim.URL.MAIN_URL;
import static com.edu.prathm.mybim.extra.FileOperator.addEntryToSharedPreference;
import static com.edu.prathm.mybim.extra.key.*;

public class Login extends AppCompatActivity {
    private VollySingleton vollySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    EditText staffId;
    Button login;
    private Toolbar toolbar;
    ProgressDialog progressDialog;

    public static String getRequestUrl(String userid) {
        //return MAIN_URL;
        return LOGIN_URL + "?staffID=" + userid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        staffId = (EditText) findViewById(R.id.staffId);
        login = (Button) findViewById(R.id.login);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        vollySingleton = VollySingleton.getInstance();
        requestQueue = vollySingleton.getRequestQueue();
        progressDialog = new ProgressDialog(Login.this, AlertDialog.THEME_HOLO_DARK);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processData();
            }
        });

    }

    protected void processData() {

        String staffIdString = staffId.getText().toString();

        if (!staffIdString.equals("") && staffIdString != null) {
            progressDialog.setMessage("Processing Request");
            progressDialog.show();
            progressDialog.setCancelable(false);

            JsonObjectRequest loginJson = new JsonObjectRequest(Request.Method.GET, getRequestUrl(staffIdString), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();

                    if (parseJson(response)) {
                        startActivity(new Intent(Login.this, Home.class));
                        finish();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            String errorInfo = "";
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                errorInfo = "Connection Time out";

                            } else if (error instanceof AuthFailureError) {
                                errorInfo = "Authentication Fail";
                            } else if (error instanceof ServerError) {
                                errorInfo = "Server Fail";
                            } else if (error instanceof ParseError) {
                                errorInfo = "Parse Error";
                            }
                            L.t(Login.this, errorInfo);
                        }
                    }
            );


            requestQueue.add(loginJson);
        } else {

            L.t(getBaseContext(), "Please enter valid staff ID ");
        }

    }

    private Boolean parseJson(JSONObject response) {
        Boolean isValid = false;
/*
* {
* "user":[{"id":"2354",
*           "firstname":"Rajkumar",
*           "lastname":"Nadar",
*           "role":"Android Developer",
*           "email":"nadar.karthik0@gmail.com",
*           "password":"rajkumar",
*           "gender":"Male",
*           "dob":"1992-09-16",
*           "location":"Ghatkopar",
*           "profileImage":null
*           }
*          ]
* }
* */
/*   {"user":"nil",
        "success":"false"
*  }

         */
        try {
            //String user = response.getString("user");
            String success = "false";
            if (response.has(KEY_SUCCESS)) {
                success = response.getString(KEY_SUCCESS);
            }
            if (success.equals(KEY_FALSE)) {
                L.t(Login.this, "Invalid authentication");

            } else if (success.equals(KEY_TRUE)) {
                L.t(Login.this, "User is authenticated");


                if (response.has(KEY_USER)) {
                    JSONArray userArray = response.getJSONArray(KEY_USER);
                    JSONObject user = userArray.getJSONObject(0);

                    User s = new User();

                    if (user.has(KEY_USER_ID)) {
                        String id = user.getString(KEY_USER_ID);
                        s.setId(id);
                    }
                    if (user.has(KEY_USER_FIRSTNAME)) {
                        String firstname = user.getString(KEY_USER_FIRSTNAME);
                        s.setFirstname(firstname);
                    }
                    if (user.has(KEY_USER_LASTNAME)) {
                        String lastname = user.getString(KEY_USER_LASTNAME);
                        s.setLastname(lastname);
                    }
                    if (user.has(KEY_USER_ROLE)) {
                        String role = user.getString(KEY_USER_ROLE);
                        s.setRole(role);
                    }
                    if (user.has(KEY_USER_EMAIL)) {
                        String email = user.getString(KEY_USER_EMAIL);
                        s.setEmail(email);
                    }
                    if (user.has(KEY_USER_PASSWORD)) {
                        String password = user.getString(KEY_USER_PASSWORD);
                        s.setPassword(password);
                    }
                    if (user.has(KEY_USER_GENDER)) {
                        String gender = user.getString(KEY_USER_GENDER);
                        s.setGender(gender);
                    }
                    if (user.has(KEY_USER_DATE_OF_BIRTH)) {
                        String dob = user.getString(KEY_USER_DATE_OF_BIRTH);

                        Date d =null;
                        if( dob != null && dob.equals("null")) {

                           try {
                               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                               d = dateFormat.parse(dob);
                           }
                           catch(ParseException e)
                           {
                               d=null;
                           }
                        }

                            s.setDob(d);
                    }
                    if (user.has(KEY_USER_LOCATION)) {
                        String location = user.getString(KEY_USER_LOCATION);
                        s.setLocation(location);
                    }
                    if (user.has(KEY_USER_PROFILE_IMAGE)) {
                        String imageUrl = user.getString(KEY_USER_PROFILE_IMAGE);
                        s.setProfileImageUrl(imageUrl);
                    }

                    addEntryUserSharedPreference(s);
                    isValid = true;
                }


            }


        } catch (JSONException e)

        {
            e.printStackTrace();
        }

        return isValid;
    }

    private void addEntryUserSharedPreference(User s) {
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(KEY_USER_IS_LOGIN, KEY_TRUE);


        editor.putString(KEY_USER_ID, s.getId());
        editor.putString(KEY_USER_FIRSTNAME, s.getFirstname());
        editor.putString(KEY_USER_LASTNAME, s.getLastname());
        editor.putString(KEY_USER_EMAIL, s.getEmail());
        editor.putString(KEY_USER_PASSWORD, s.getPassword());
        editor.putString(KEY_USER_GENDER, s.getGender());
        editor.putString(KEY_USER_ROLE, s.getRole());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = s.getDob();

        if (d != null) {
            String dateString = dateFormat.format(d);
            editor.putString(KEY_USER_DATE_OF_BIRTH, dateString);

        }

        editor.putString(KEY_USER_LOCATION, s.getLocation());
        editor.putString(KEY_USER_PROFILE_IMAGE, s.getProfileImageUrl());

        editor.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
