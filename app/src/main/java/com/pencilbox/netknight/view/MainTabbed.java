package com.pencilbox.netknight.view;


import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.service.NetKnightService;
import com.pencilbox.netknight.utils.MyLog;

public class MainTabbed extends AppCompatActivity {
    private static final int REQ_START_VPN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 开启vpn service
     */
    protected void startVpnService() {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, REQ_START_VPN);
        } else {
            onActivityResult(REQ_START_VPN, RESULT_OK, null);
        }
    }

    /**
     * 停止vpnService
     */
    protected void stopVpnService() {
        NetKnightService.isCalledByUser = true;
        NetKnightService.isRunning = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQ_START_VPN:
                MyLog.logd(this, "start Vpn Service");
                Intent intent = new Intent(this, NetKnightService.class);
                startService(intent);
                break;
            default:
                break;
        }
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
            }
            return null;
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
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
            }
            return null;
        }
    }
}
