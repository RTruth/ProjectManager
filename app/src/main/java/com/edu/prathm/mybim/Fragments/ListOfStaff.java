package com.edu.prathm.mybim.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.network.VollySingleton;
import com.edu.prathm.mybim.pojo.Project;
import com.edu.prathm.mybim.pojo.StaffMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListOfStaff.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ListOfStaff extends ListFragment implements SearchView.OnQueryTextListener {
    MyStaffAdapter myStaffAdapter;
    ImageLoader mImageLoader;
    ArrayList<StaffMember> allstaff;
    private Toolbar toolbar;
     String mCurFilter;


    public ListOfStaff() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_of_project, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchToolbarItem = menu.findItem(R.id.action_search);
        SearchView searchView;
        searchView = (SearchView) MenuItemCompat.getActionView(searchToolbarItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("List Of StaffMember");

        } else {
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : "";
        // getLoaderManager().restartLoader(0, null, this);
        myStaffAdapter.filter(mCurFilter);
        return true;
    }
    // TODO: Rename method, update argument and hook method into UI event


    class MyStaffAdapter extends BaseAdapter {
        List<StaffMember> allStaff;
        ArrayList<StaffMember> allStaffTemp;
        Context context;


        public MyStaffAdapter(Context context, List<StaffMember> allStaff) {

            this.context = context;
            this.allStaff = allStaff;
            allStaffTemp=new ArrayList<>();
            allStaffTemp.addAll(allStaff);
        }
        public class MyviewHolder
        {
            NetworkImageView avatar;
            TextView member;

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
            MyviewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listofstaff, null);
holder=new MyviewHolder();
                holder.avatar = (NetworkImageView) convertView.findViewById(R.id.avatar);
                holder.member=(TextView) convertView.findViewById(R.id.member);
                convertView.setTag(holder);
            }
            else
            {
                holder= (MyviewHolder) convertView.getTag();
            }

            if(allStaff!=null)
            {
                StaffMember currentStaffMember = allStaff.get(position);
               holder.member.setText(currentStaffMember.getStaff_name());
                holder.avatar.setImageUrl(currentStaffMember.getStaff_avatarUrl(), mImageLoader);
            }


            return convertView;
        }
        public  void filter(String charText) {
            if(charText!=null)
                charText = charText.toLowerCase(Locale.getDefault());
            allStaff.clear();
            if (charText.length() == 0) {
                allStaff.addAll(allStaffTemp);
            }
            else
            {
                for (StaffMember staffMember : allStaffTemp)
                {
                    if (staffMember.getStaff_name().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        allStaff.add(staffMember);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
