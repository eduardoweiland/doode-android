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

import net.doode.android.model.BPActivity;
import net.doode.android.model.BPUser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;

/**
 * Activity class for viewing and replying to public messages.
 * 
 * @author Eduardo Weiland
 */
public class ActivityFragment extends SherlockListFragment {

    private ActivityListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ActivityListAdapter(getActivity());
        setListAdapter(mAdapter);

        BPUser user = new BPUser("Administrator", "admin");
        mAdapter.add(new BPActivity("test 1", user));
        mAdapter.add(new BPActivity("test 2", user));
        mAdapter.add(new BPActivity("test 3", user));
        mAdapter.add(new BPActivity("test 4", user));
        mAdapter.add(new BPActivity("test 5", user));
        mAdapter.add(new BPActivity("test 6", user));
        mAdapter.add(new BPActivity("test 7", user));
        mAdapter.add(new BPActivity("test 8", user));
        mAdapter.add(new BPActivity("test 9", user));
        mAdapter.add(new BPActivity("test 10", user));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle saved) {
        return inflater.inflate(R.layout.activity, group, false);
    }

    @Override
    public void onDestroy() {
        mAdapter.clear();
        super.onDestroy();
    }

}
