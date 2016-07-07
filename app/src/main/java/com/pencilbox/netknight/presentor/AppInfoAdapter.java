package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.AppInfo;

import java.util.List;

/**
 * Created by wu on 16/6/27.
 */

public class AppInfoAdapter extends BaseAdapter {
    private List<AppInfo> list_appinfo = null;
    LayoutInflater inflater = null;

    public AppInfoAdapter(Context context, List<AppInfo> apps) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list_appinfo = apps;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        System.out.println("size" + list_appinfo.size());
        return list_appinfo.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list_appinfo.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertview, ViewGroup arg2) {
        System.out.println("getView at " + position);
        View view = null;
        ViewHolder holder = null;
        if (convertview == null || convertview.getTag() == null) {
            view = inflater.inflate(R.layout.app_items, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertview;
            holder = (ViewHolder) convertview.getTag();
        }
        AppInfo appInfo = (AppInfo) getItem(position);
        holder.appIcon.setImageDrawable(appInfo.getAppIcon());
        holder.tvAppLabel.setText(appInfo.getAppLabel());
        holder.tvPkgName.setText(appInfo.getPkgName());
        return view;
    }

    class ViewHolder {
        ImageView appIcon;
        TextView tvAppLabel, tvPkgName;

        public ViewHolder(View view){
            this.appIcon= (ImageView) view.findViewById(R.id.app_icon);
            this.tvAppLabel= (TextView) view.findViewById(R.id.tvAppLabel);
            this.tvPkgName = (TextView) view.findViewById(R.id.tvPkgName);

        }
    }
}
