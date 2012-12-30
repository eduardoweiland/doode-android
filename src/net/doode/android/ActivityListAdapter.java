package net.doode.android;

import java.util.ArrayList;

import net.doode.android.model.BPActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ActivityListAdapter extends ArrayAdapter<BPActivity> {

    private ArrayList<BPActivity> mActivityList;

    public ActivityListAdapter(Context context) {
        super(context, 0);
        mActivityList = new ArrayList<BPActivity>();
    }

    @Override
    public void add(BPActivity activity) {
        mActivityList.add(activity);
        super.add(activity);
    }

    @Override
    public void remove(BPActivity activity) {
        mActivityList.remove(activity);
        super.remove(activity);
    }

    @Override
    public void clear() {
        mActivityList.clear();
        super.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_list_item, parent, false);

            holder = new ViewHolder();
            holder.username = (TextView) view.findViewById(R.id.user_name);
            holder.content  = (TextView) view.findViewById(R.id.content);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        BPActivity activity = getItem(position);
        // TODO: user_avatar
        holder.username.setText(activity.getUser().getUserName());
        holder.content .setText(activity.getContent());

        return view;
    }

    public static class ViewHolder {
        public TextView username;
        public TextView content;
    }

}
