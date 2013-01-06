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

import java.util.Vector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends SherlockFragmentActivity {

    public static final String TAG = "MainActivity";

    private ActionBar mActionBar;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private SlidingMenu mSidebar;

    public static final int LOGIN_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Doode.isOnline()) {
            Toast.makeText(this, R.string.offline, Toast.LENGTH_LONG).show();
        }

        Vector<String> loginInfo = Doode.doodeDB.loadLoginInfo();
        if (loginInfo == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST);
            return;
        }

        setContentView(R.layout.main);
        prepareActionBar();
        prepareSidebar();

        Log.i(TAG, "LOGIN: user=" + loginInfo.get(0) + ",device=" + Doode.deviceId + ",apikey=" + loginInfo.get(1));
        Doode.client.login(loginInfo.get(0), Doode.deviceId, loginInfo.get(1));

        // Get a reference for the ViewPager from layout
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabsAdapter = new TabsAdapter(this, mViewPager);

        // Activity
        mTabsAdapter.addTab(mActionBar.newTab()
                .setText(R.string.activity),
                ActivityFragment.class, null);

        // Mentions
        mTabsAdapter.addTab(mActionBar.newTab()
                .setText(R.string.mentions),
                MentionsFragment.class, null);

        // Messages
        mTabsAdapter.addTab(mActionBar.newTab()
                .setText(R.string.messages),
                MessagesFragment.class, null);

        // Notifications
        mTabsAdapter.addTab(mActionBar.newTab()
                .setText(R.string.notifications),
                NotificationsFragment.class, null);
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

            case android.R.id.home:
                // Toggle the sidebar
                mSidebar.toggle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST) {
            // TODO
        }
    }

    /**
     * Get the action bar reference and set options.
     */
    private void prepareActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setIcon(R.drawable.doode_logo);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
    }

    /**
     * Create the sidebar on the left.
     */
    private void prepareSidebar() {
        mSidebar = new SlidingMenu(this, SlidingMenu.SLIDING_CONTENT);
        mSidebar.setBehindWidth(280); // temp
        mSidebar.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSidebar.setFadeDegree(0.3f);
        mSidebar.setShadowDrawable(R.drawable.sidebar_shadow);
        mSidebar.setShadowWidth(30); // temp

        View sidebar = LayoutInflater.from(this).inflate(R.layout.sidebar, null);
        final ListView listView = (ListView) sidebar.findViewById(android.R.id.list);
        listView.setFooterDividersEnabled(true);
        listView.setAdapter(new SidebarAdapter());
        mSidebar.setMenu(sidebar);
    }
}
