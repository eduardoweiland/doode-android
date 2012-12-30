/**************************************************************************
 *  This file is part of the Doode Android project                        *
 *  Copyright (C) 2012 Eduardo Weiland                                    *
 *  duduweiland@users.sourceforge.net                                     *
 *                                                                        *
 *  This program is free software: you can redistribute it and/or modify  *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  This program is distributed in the hope that it will be useful,       *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>  *
 **************************************************************************/

package net.doode.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {

    private ActionBar mActionBar;
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Doode.isOnline()) {
            Toast.makeText(this, R.string.offline, Toast.LENGTH_LONG).show();
        }

        // Vector<String> loginInfo = Doode.doodeDB.loadLoginInfo();
        // if ( loginInfo == null ) {
        // startActivity( new Intent().setClass( getApplicationContext(),
        // LoginActivity.class ) );
        // return;
        // }

        // Doode.client.login(loginInfo.get(0), Doode.deviceId,
        // loginInfo.get(1));

        setContentView(R.layout.main);

        // Get a reference for the action bar
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);

        // Get a reference for the ViewPager from layout
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        // Page selected listener
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        });

        // Tab listener
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                mPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

            public void onTabReselected(Tab tab, FragmentTransaction ft) {}
        };

        // Activity
        mActionBar.addTab(mActionBar.newTab()
                .setText(R.string.activity_title)
                .setTabListener(tabListener));

        // Mentions
        mActionBar.addTab(mActionBar.newTab()
                .setText(R.string.mentions_title)
                .setTabListener(tabListener));

        // Messages
        mActionBar.addTab(mActionBar.newTab()
                .setText(R.string.messages_title)
                .setTabListener(tabListener));

        // Notifications
        mActionBar.addTab(mActionBar.newTab()
                .setText(R.string.notifications_title)
                .setTabListener(tabListener));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main_menu, menu);

        boolean hasNotifications = false;   // TODO: get notifications
        menu.findItem(R.id.menu_notifications).setVisible(hasNotifications);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update_status:
                startActivity(new Intent(getApplicationContext(), UpdateStatusActivity.class));
                return true;

            case R.id.menu_search:
                return true;

            case R.id.menu_preferences:
                return true;

            case R.id.menu_about:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        final int PAGE_COUNT = 4;

        /** This method will be invoked when a page is requested to create */
        @Override
        public Fragment getItem(int index) {
            Bundle data = new Bundle();
            data.putInt("current_page", index + 1);

            switch (index) {
                case 0:
                    ActivityFragment activityFrag = new ActivityFragment();
                    activityFrag.setArguments(data);
                    return activityFrag;
                case 1:
                    MentionsFragment mentionsFrag = new MentionsFragment();
                    mentionsFrag.setArguments(data);
                    return mentionsFrag;
                case 2:
                    MessagesFragment messagesFrag = new MessagesFragment();
                    messagesFrag.setArguments(data);
                    return messagesFrag;
                case 3:
                    MentionsFragment another = new MentionsFragment();
                    another.setArguments(data);
                    return another;
            }

            return null;
        }

        /** Returns the number of pages */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

}
