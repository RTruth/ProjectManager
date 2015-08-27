package com.edu.prathm.mybim.Fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.edu.prathm.mybim.Activities.Login;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.extra.L;
import com.edu.prathm.mybim.extra.key;
import com.edu.prathm.mybim.network.VollySingleton;
import com.edu.prathm.mybim.pojo.Project;
import com.edu.prathm.mybim.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.edu.prathm.mybim.URL.Get_Project_URL;
import static com.edu.prathm.mybim.URL.LOGIN_URL;
import static com.edu.prathm.mybim.extra.FileOperator.getEntryOfSharedPreference;
import static com.edu.prathm.mybim.extra.key.KEY_END_DATE;
import static com.edu.prathm.mybim.extra.key.KEY_FALSE;
import static com.edu.prathm.mybim.extra.key.KEY_PROJECTS;
import static com.edu.prathm.mybim.extra.key.KEY_PROJECT_ID;
import static com.edu.prathm.mybim.extra.key.KEY_PROJECT_NAME;
import static com.edu.prathm.mybim.extra.key.KEY_PROJECT_OWNER;
import static com.edu.prathm.mybim.extra.key.KEY_START_DATE;
import static com.edu.prathm.mybim.extra.key.KEY_SUCCESS;
import static com.edu.prathm.mybim.extra.key.KEY_TEAM_ID;
import static com.edu.prathm.mybim.extra.key.KEY_TRUE;
import static com.edu.prathm.mybim.extra.key.KEY_USER;
import static com.edu.prathm.mybim.extra.key.KEY_USER_DATE_OF_BIRTH;
import static com.edu.prathm.mybim.extra.key.KEY_USER_EMAIL;
import static com.edu.prathm.mybim.extra.key.KEY_USER_FIRSTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_GENDER;
import static com.edu.prathm.mybim.extra.key.KEY_USER_ID;
import static com.edu.prathm.mybim.extra.key.KEY_USER_LASTNAME;
import static com.edu.prathm.mybim.extra.key.KEY_USER_LOCATION;
import static com.edu.prathm.mybim.extra.key.KEY_USER_PASSWORD;
import static com.edu.prathm.mybim.extra.key.KEY_USER_PROFILE_IMAGE;
import static com.edu.prathm.mybim.extra.key.KEY_USER_ROLE;
import static com.edu.prathm.mybim.extra.key.KEY_U_ID;


public class ListOfProjects extends ListFragment {

    private RequestQueue requestQueue;
    MyProjectAdapter myProjectAdapter;
    private Toolbar toolbar;
    ArrayList<Project> projects;

    public ListOfProjects() {
        // Required empty public constructor
    }
    public static String getRequestUrl(String userid) {
        //return MAIN_URL;
        return Get_Project_URL + "?user_id=" + userid;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_projects, container, false);
         requestQueue=VollySingleton.getInstance().getRequestQueue();


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("List Of Project");

        } else {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_list_of_project, menu);

        SearchManager searchManager= (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchToolbarItem = menu.findItem(R.id.action_search);
        SearchView searchView;
        searchView = (SearchView) MenuItemCompat.getActionView(searchToolbarItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
     searchView.setIconifiedByDefault(true);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                //do something
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        projects = new ArrayList<Project>();
     getProjectsObjects();
        myProjectAdapter = new MyProjectAdapter(getActivity(), projects);
        setListAdapter(myProjectAdapter);


    }

    private void getProjectsObjects() {
        /*(String[] projectname = {"java project", "Php project", "html project", "css project"};
        String[] P_by = {"prayhm", "Raj", "mahesh", "hasid"};*/

        JsonObjectRequest projectRequest=new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(getEntryOfSharedPreference(getActivity(), key.KEY_USER_ID)), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
if(parsejason(response))
{
Toast.makeText(getActivity(),"Everything went fine",Toast.LENGTH_LONG).show();
}

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(projectRequest);


    }

    private boolean parsejason(JSONObject response) {
        boolean isValid=false;
        try {
            //String user = response.getString("user");
            String success = "false";
            if (response.has(KEY_SUCCESS)) {
                success = response.getString(KEY_SUCCESS);
            }
            if (success.equals(KEY_FALSE)) {
                L.t(getActivity(), "No Projects");

            } else if (success.equals(KEY_TRUE)) {
                L.t(getActivity(), "projects present");


                if (response.has(KEY_PROJECTS)) {
                    JSONObject project = null;
                    JSONArray ProjArray = response.getJSONArray("project");
                    for (int i = 0; i < ProjArray.length(); i++) {
                        project = ProjArray.getJSONObject(i);

                        Project p = new Project();

                        if (project.has(KEY_PROJECT_ID)) {
                            String id = project.getString(KEY_PROJECT_ID);
                            p.setP_id(id);
                        }
                        if (project.has(KEY_U_ID)) {
                            String u_id = project.getString(KEY_U_ID);
                            p.setU_id(u_id);
                        }
                        if (project.has(KEY_PROJECT_NAME)) {
                            String pname = project.getString(KEY_PROJECT_NAME);
                            p.setProject_name(pname);
                        }
                        if (project.has(KEY_TEAM_ID)) {
                            String team = project.getString(KEY_TEAM_ID);
                            p.setTeam_id(team);
                        }
                        if (project.has(KEY_PROJECT_OWNER)) {
                            String owner = project.getString(KEY_PROJECT_OWNER);
                            p.setProject_owner(owner);
                        }

                        if (project.has(KEY_START_DATE)) {
                            String dob = project.getString(KEY_START_DATE);

                            Date d = null;
                            if (dob != null && dob.equals("null")) {

                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    d = dateFormat.parse(dob);
                                } catch (ParseException e) {
                                    d = null;
                                }
                            }

                            p.setStart_date(d);
                        }
                        if (project.has(KEY_END_DATE)) {
                            String dob = project.getString(KEY_END_DATE);

                            Date d = null;
                            if (dob != null && dob.equals("null")) {

                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    d = dateFormat.parse(dob);
                                } catch (ParseException e) {
                                    d = null;
                                }
                            }

                            p.setStart_date(d);
                        }



                        isValid = true;
                        projects.add(p);

                    }

                }
            }


        } catch (JSONException e)

        {
            e.printStackTrace();
        }

        return isValid;
    }

    }
// TODO: Rename method, update argument and hook method into UI event


    class MyProjectAdapter extends BaseAdapter {
        ArrayList<Project> projects;
        Context context;
        TextView pro_name;
        TextView pro_by;

        public MyProjectAdapter(Context context, ArrayList<Project> projects) {
            this.context = context;
            this.projects = projects;
        }

        @Override
        public int getCount() {
            return projects.size();
        }

        @Override
        public Object getItem(int position) {
            //Toast.makeText(getActivity(),"*********",Toast.LENGTH_LONG).show();
            return projects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.projectlistitem, null);

            }


            pro_name = (TextView) convertView.findViewById(R.id.project_name);
            pro_by = (TextView) convertView.findViewById(R.id.project_by);

            Project currentProject = projects.get(position);
            pro_name.setText(currentProject.getProject_name()); //
            pro_by.setText(currentProject.getProject_owner()); //;

            return convertView;
        }
    }



