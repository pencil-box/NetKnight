package com.pencilbox.netknight.view;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainTabbed extends AppCompatActivity implements AddressInputDialog.DataInputListener,
        IpInputDialog.DataIpInputListnener {

    PopupWindow popupWindow;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ListView listViewaddress, listViewip;
    private List<String> listaddress, listip;
    private ListAdapter address_adapter, ip_adapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        listaddress = new ArrayList<String>();
        listip = new ArrayList<String>();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tabbed, menu);
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

    @Override
    public void onDataInputListener(String start, String end) {

        listViewaddress = (ListView) this.findViewById(R.id.list_address);
        listaddress.add(start);
        listaddress.add(end);


        address_adapter = new ListAdapter(this, listaddress);
        listViewaddress.setAdapter(address_adapter);
    }

    @Override
    public void onDataIpInputListener(String ipstart, String ipend) {
        listViewip = (ListView) this.findViewById(R.id.list_ip);
        listip.add(ipstart);
        listip.add(ipend);
        ip_adapter = new ListAdapter(this, listip);
        listViewip.setAdapter(ip_adapter);


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return new MainApp();
                case 1:
                    return new MainIp();
                case 2:
                    return new MainAddress();
                case 3:
                    return new MainPrefer();
            }
            return null;
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "应用";
                case 1:
                    return "IP";
                case 2:
                    return "域名";
                case 3:
                    return "偏好";
            }
            return null;
        }
    }

}
