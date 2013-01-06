package net.doode.android;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class SidebarAdapter extends BaseAdapter {

    public static final String TAG = "SidebarAdapter";

    static class SidebarEntry {
        String name;
        int drawableID;

        public SidebarEntry(String _name, int _drawableID) {
            name = _name;
            drawableID = _drawableID;
        }

        public SidebarEntry(int _name, int _drawableID) {
            name = Doode.getAppContext().getString(_name);
            drawableID = _drawableID;
        }
    }

    private final ArrayList<SidebarEntry> mItens;

    public SidebarAdapter() {
        mItens = new ArrayList<SidebarEntry>();
        mItens.add(new SidebarEntry(R.string.activity, R.drawable.avatar_default));
        mItens.add(new SidebarEntry(R.string.mentions, R.drawable.avatar_default));
    }

    public int getCount() {
        return mItens.size();
    }

    public Object getItem(int index) {
        return mItens.get(index);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        SidebarEntry entry = mItens.get(position);

        if (view == null) {
            view = LayoutInflater.from(Doode.getAppContext())
                    .inflate(R.layout.sidebar_item, parent, false);
        }
        TextView textView = (TextView) view;
        textView.setText(entry.name);
        Drawable img = Doode.getAppContext().getResources().getDrawable(entry.drawableID);
        textView.setCompoundDrawables(img, null, null, null);
        return view;
    }

}
