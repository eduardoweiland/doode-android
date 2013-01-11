package net.doode.android;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TabsAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

    private static final class TabInfo {
        public final String title;
        public final Class<?> clss;
        public final Bundle args;

        public TabInfo(String pTitle, Class<?> pCls, Bundle pArgs) {
            title = pTitle;
            clss = pCls;
            args = pArgs;
        }
    }

    public TabsAdapter(SherlockFragmentActivity activity) {
        super(activity.getSupportFragmentManager());
        mContext = activity;
    }

    public void addTab(String title, Class<?> cls, Bundle args) {
        TabInfo info = new TabInfo(title, cls, args);
        mTabs.add(info);
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

    @Override
    public CharSequence getPageTitle(int position) {
        TabInfo info = mTabs.get(position);
        if (info != null) {
            return info.title;
        }
        return null;
    }

}
