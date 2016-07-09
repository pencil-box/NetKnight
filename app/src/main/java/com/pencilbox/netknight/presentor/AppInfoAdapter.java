package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/6/27.
 */

public class AppInfoAdapter extends BaseAdapter {
    private List<App> list_appinfo = new ArrayList<>();
    LayoutInflater inflater = null;


    private PackageManager mPackageManager;

    public void addAll(List<App> appLists){
        list_appinfo.addAll(appLists);
    }

    public AppInfoAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


//    public AppInfoAdapter(Context context, List<AppInfo> apps) {

//        list_appinfo = apps;
//    }

    public int getCount() {
        // TODO Auto-generated method stub
//        System.out.println("size" + list_appinfo.size());
        return list_appinfo.size();
    }

    public App getItem(int position) {
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

        App appInfo = getItem(position);
            holder.appIcon.setImageDrawable(appInfo.getIcon());
        holder.tvAppLabel.setText(appInfo.getName());
        holder.tvPkgName.setText(appInfo.getPkgname());

//        AppInfo appInfo = (AppInfo) getItem(position);
//        holder.appIcon.setImageDrawable(appInfo.getAppIcon());
//        holder.tvAppLabel.setText(appInfo.getAppLabel());
//        holder.tvPkgName.setText(appInfo.getPkgName());
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
