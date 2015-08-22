package com.edu.prathm.mybim.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.pojo.StaffMember;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListOfStaff.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ListOfStaff extends ListFragment {
    MyStaffAdapter myStaffAdapter;
    ArrayList<StaffMember> allstaff;
    private Toolbar toolbar;

    public ListOfStaff() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_of_staff, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
allstaff = new ArrayList<StaffMember>();
        getStaffObjects();
        myStaffAdapter = new MyStaffAdapter(getActivity(), allstaff);
        setListAdapter(myStaffAdapter);

    }

    public void getStaffObjects() {
        StaffMember m = new StaffMember();
        m.setStaff_avatarUrl("ggggggggggggg");
        m.setStaff_name("hiiiiiiiiiiiii");
        StaffMember m2 = new StaffMember();
        m2.setStaff_avatarUrl("ggggggggggggg");
        m2.setStaff_name("hiiiiiiiiiiiii");

        allstaff.add(m);
        allstaff.add(m2);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("List Of StaffMember");

        } else {
        }
    }
    // TODO: Rename method, update argument and hook method into UI event


    class MyStaffAdapter extends BaseAdapter {
        ArrayList<StaffMember> allStaff;
        Context context;
        ImageView avatar;
        TextView member;

        public MyStaffAdapter(Context context, ArrayList<StaffMember> allStaff) {

            this.context = context;
            this.allStaff = allStaff;
        }

        @Override
        public int getCount() {
            return allStaff.size();
        }

        @Override
        public Object getItem(int position) {
            return allStaff.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listofstaff, null);

            }
            avatar = (ImageView) convertView.findViewById(R.id.avatar);
            member = (TextView) convertView.findViewById(R.id.member);
            StaffMember currentStaffMember = allStaff.get(position);
            member.setText(currentStaffMember.getStaff_name());
            return convertView;
        }
    }
}
