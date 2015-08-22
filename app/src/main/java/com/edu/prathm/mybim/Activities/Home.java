package com.edu.prathm.mybim.Activities;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.edu.prathm.mybim.Fragments.ListOfProjects;
import com.edu.prathm.mybim.Fragments.ListOfStaff;
import com.edu.prathm.mybim.Fragments.OnUpdateProfileListner;
import com.edu.prathm.mybim.Fragments.Profile;
import com.edu.prathm.mybim.Fragments.Setting;
import com.edu.prathm.mybim.Fragments.UserHome;
import com.edu.prathm.mybim.R;
import com.edu.prathm.mybim.popups.ProfileSetting;
import com.edu.prathm.mybim.tab.SlidingTabLayout;

import static com.edu.prathm.mybim.extra.FileOperator.addEntryToSharedPreference;

public class Home extends AppCompatActivity implements OnUpdateProfileListner {

    static final int FRAG_HOME = 0;
    static final int FRAG_LISTOFSTAFF = 1;
    static final int FRAG_LISTOFPROJECT = 2;
    static final int FRAG_PROFILE = 3;
    static final int FRAG_SETTING = 4;
    Toolbar toolbar;
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyTabAdapter(getSupportFragmentManager()));
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);

        //slidingTabLayout.setDistributeEvenly(true);
       // slidingTabLayout.setCustomTabView(R.layout.custom_tab,0);
        //slidingTabLayout.setCustomTabView(R.layout.custom_tab, R.id.tabText);
        //slidingTabLayout.setSelectedIndicatorColors(R.color.primary);

        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.icons));


        slidingTabLayout.setViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProfileSetting() {
        ProfileSetting profileSetting = new ProfileSetting();
        profileSetting.show(getSupportFragmentManager(), "");

    }


    public class MyTabAdapter extends FragmentStatePagerAdapter {

        int[] imageResId = {
                R.drawable.first_tab,
                R.drawable.second_tab,
                R.drawable.third_tab,
                R.drawable.fourth_tab,
                R.drawable.fifth_tab
        };
        public MyTabAdapter(FragmentManager fm) {
            super(fm);
            //tabs = getResources().getStringArray(R.array.tabs);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;


            switch (position) {
                case FRAG_HOME:
                    frag = new UserHome();
                    break;

                case FRAG_LISTOFSTAFF:
                    frag = new ListOfStaff();
                    break;

                case FRAG_LISTOFPROJECT:
                    frag = new ListOfProjects();
                    break;
                case FRAG_PROFILE:
                    frag = new Profile();
                    break;
                case FRAG_SETTING:
                    frag = new Setting();
                    break;

            }

            return frag;
        }

        @Override
        public CharSequence getPageTitle(int position) {

/*
            Drawable image = getResources().getDrawable(imageResId[position]);
            image.setBounds(0, 0, 36, 36);
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
*/
            return null;


        }

        public int getDrawableId(int position) {
            return imageResId[position];
        }


        @Override
        public int getCount() {
            return 5;
        }
    }

}
