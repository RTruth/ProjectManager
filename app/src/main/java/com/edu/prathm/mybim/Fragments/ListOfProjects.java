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

import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.pojo.Project;

import java.util.ArrayList;


public class ListOfProjects extends ListFragment {
    MyProjectAdapter myProjectAdapter;
    private Toolbar toolbar;
    ArrayList<Project> projects;

    public ListOfProjects() {
        // Required empty public constructor
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
        String[] projectname = {"java project", "Php project", "html project", "css project"};
        String[] P_by = {"prayhm", "Raj", "mahesh", "hasid"};

        for(int i = 0 ; i<projectname.length;i++ )
        {
            Project p = new Project();
            p.setProject_name(projectname[i]);
            p.setProject_owner(P_by[i]);

            projects.add(p);

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


}
