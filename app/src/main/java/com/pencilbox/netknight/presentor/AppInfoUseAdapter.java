package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/11.
 */

public class AppInfoUseAdapter extends BaseAdapter {
    private List<App> list_appinfo = new ArrayList<>();
    LayoutInflater inflater = null;

    public void addAll(List<App> appLists) {
        list_appinfo.addAll(appLists);
    }

    public AppInfoUseAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list_appinfo.size();
    }

    @Override
    public Object getItem(int position) {
        return list_appinfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            view = inflater.inflate(R.layout.appnetuse_itmes, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        App appInfo = (App) getItem(position);
        holder.appnetuse_icon.setImageDrawable(appInfo.getIcon());
        holder.netuseAppLabel.setText(appInfo.getName());

        return view;

    }

    class ViewHolder {
        ImageView appnetuse_icon;
        TextView netuseAppLabel;
        SeekBar wifi_seekbar, celluar_seekbar;

        public ViewHolder(View view) {
            this.appnetuse_icon = (ImageView) view.findViewById(R.id.appnetuse_icon);
            this.netuseAppLabel = (TextView) view.findViewById(R.id.netuseAppLabel);
            this.wifi_seekbar = (SeekBar) view.findViewById(R.id.wifi_seekbar);
            this.celluar_seekbar = (SeekBar) view.findViewById(R.id.celluar_seekbar);

        }
    }
}
