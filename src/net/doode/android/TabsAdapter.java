package net.doode.android;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TabsAdapter extends FragmentPagerAdapter
        implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private final Context mContext;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

    private static final class TabInfo {
        public final Class<?> clss;
        public final Bundle args;

        public TabInfo(Class<?> pCls, Bundle pArgs) {
            clss = pCls;
            args = pArgs;
        }
    }

    public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
        super(activity.getSupportFragmentManager());
        mContext   = activity;
        mActionBar = activity.getSupportActionBar();
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    public void addTab(ActionBar.Tab tab, Class<?> cls, Bundle args) {
        TabInfo info = new TabInfo(cls, args);
        tab.setTag(info);
        tab.setTabListener(this);
        mTabs.add(info);
        mActionBar.addTab(tab);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabs.get(position);
        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public void onPageSelected(int position) {
        mActionBar.setSelectedNavigationItem(position);
    }

    public void onPageScrollStateChanged(int state) {}

    public void onPageScrolled(int position, float offset, int pixels) {}

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
        Object tag = tab.getTag();
        for (int i = 0; i < mTabs.size(); ++i) {
            if (tag == mTabs.get(i)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}

}
