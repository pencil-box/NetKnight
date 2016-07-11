package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 16/6/27.
 */

public class AppInfoAdapter extends BaseAdapter {
    private List<App> list_appinfo = new ArrayList<>();
    LayoutInflater inflater = null;
    private Map<App, Integer> allValues;
    private List<App> checkListName;
    private Context mContext;

    public void addAll(List<App> appLists) {
        list_appinfo.addAll(appLists);
    }

    public AppInfoAdapter(Context context) {
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allValues = new HashMap<>();
        this.checkListName = list_appinfo;
        putAllValues();
    }

    private void putAllValues() {
        for (App str : checkListName) {
            allValues.put(str, 0);
        }
    }


    public int getCount() {
        return list_appinfo.size();
    }

    public App getItem(int position) {
        return list_appinfo.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertview, ViewGroup arg2) {
        View view = null;
        ViewHolder holder = null;
        if (convertview == null || convertview.getTag() == null) {
            view = inflater.inflate(R.layout.app_items, null);
            holder = new ViewHolder(view);
            holder.wifi_spinner = (Spinner) view.findViewById(R.id.wifi_spinner);
            holder.celluar_spinner = (Spinner) view.findViewById(R.id.celluar_spinner);
            SpinnerAdapter adapter = new SpinnerAdapter(mContext);
            holder.wifi_spinner.setAdapter(adapter);
            holder.celluar_spinner.setAdapter(adapter);
            view.setTag(holder);
        } else {
            view = convertview;
            holder = (ViewHolder) convertview.getTag();
        }

        App appInfo = getItem(position);
        holder.appIcon.setImageDrawable(appInfo.getIcon());
        holder.tvAppLabel.setText(appInfo.getName());
        holder.tvPkgName.setText(appInfo.getPkgname());
        return view;
    }

    class ViewHolder {
        ImageView appIcon;
        TextView tvAppLabel, tvPkgName;
        Spinner wifi_spinner,celluar_spinner;

        public ViewHolder(View view) {
            this.appIcon = (ImageView) view.findViewById(R.id.app_icon);
            this.tvAppLabel = (TextView) view.findViewById(R.id.tvAppLabel);
            this.tvPkgName = (TextView) view.findViewById(R.id.tvPkgName);
            this.wifi_spinner = (Spinner) view.findViewById(R.id.wifi_spinner);
            this.celluar_spinner= (Spinner) view.findViewById(R.id.celluar_spinner);

        }
    }
}
