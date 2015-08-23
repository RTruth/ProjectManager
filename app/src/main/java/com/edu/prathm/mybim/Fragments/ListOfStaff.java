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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.network.VollySingleton;
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
    ImageLoader mImageLoader;
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
mImageLoader= VollySingleton.getInstance().getImageLoader();
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
        m.setStaff_avatarUrl("http://a5.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTE5NDg0MDU0OTM2NTg1NzQz.jpg");
        m.setStaff_name("tom cruise");
        StaffMember m2 = new StaffMember();
        m2.setStaff_avatarUrl("https://40.media.tumblr.com/539758b0d9ae7247468f7fd2edab904a/tumblr_n2yksxcApl1t0eow1o1_500.jpg");
        m2.setStaff_name("robert downy jr");

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
        NetworkImageView avatar;
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
            avatar = (NetworkImageView) convertView.findViewById(R.id.avatar);
            member = (TextView) convertView.findViewById(R.id.member);
            StaffMember currentStaffMember = allStaff.get(position);
            member.setText(currentStaffMember.getStaff_name());
            avatar.setImageUrl(currentStaffMember.getStaff_avatarUrl(), mImageLoader);
            return convertView;
        }
    }
}
