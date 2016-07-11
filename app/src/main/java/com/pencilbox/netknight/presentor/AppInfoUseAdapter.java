package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.App;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/11.
 * Modified by su on 16/7/12
 */

public class AppInfoUseAdapter extends BaseAdapter {
    private List<App> list_appinfo = new ArrayList<>();
    LayoutInflater inflater = null;

    /**
     * 获取移动数据+WIFI总用量
     */
    long wifiTotal = DataSupport.sum("Traffic","wifiSize",Long.TYPE);
    long mobileTotal = DataSupport.sum("Traffic","mobileSize",Long.TYPE);

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

        /**
         * 设置progressBar显示比例
         */
        holder.celluar_progresskbar.setMax((int)(mobileTotal));
        holder.wifi_progressbar.setMax((int)(wifiTotal));

        /**
         * 获取当前应用移动+WIFI使用量
         */
        long mobileSize = DataSupport.where("appId = ?",String.valueOf(appInfo.getId())).sum("Traffic","mobileSize",Long.TYPE);
        long wifiSize = DataSupport.where("appId = ?",String.valueOf(appInfo.getId())).sum("Traffic","wifiSize",Long.TYPE);

        holder.celluar_progresskbar.setProgress((int)(mobileSize));
        holder.wifi_progressbar.setProgress((int)(wifiSize));

        /**
         * 将结果格式化为百分比
         */
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        //TODO: 当WIFI或mobile用量为空时，进行判断
        String p_mobile = df.format(mobileSize * 100.00 / mobileTotal) + "%";
        String p_wifi = df.format(wifiSize * 100.00 / wifiTotal) + "%";

        /**
         * 显示数据实际用量
         */
        holder.text_celluaruse.setText(mobileSize/1024 + "KB " + p_mobile);
        holder.text_wifiuse.setText(wifiSize/1024 + "KB " + p_wifi);



        return view;

    }

    class ViewHolder {
        ImageView appnetuse_icon;
        TextView netuseAppLabel,text_wifiuse,text_celluaruse;
        ProgressBar wifi_progressbar, celluar_progresskbar;

        public ViewHolder(View view) {
            this.appnetuse_icon = (ImageView) view.findViewById(R.id.appnetuse_icon);
            this.netuseAppLabel = (TextView) view.findViewById(R.id.netuseAppLabel);
            this.text_wifiuse = (TextView) view.findViewById(R.id.text_wifiuse);
            this.text_celluaruse= (TextView) view.findViewById(R.id.text_netuse);
            this.wifi_progressbar = (ProgressBar) view.findViewById(R.id.wifi_progressbar);
            this.celluar_progresskbar = (ProgressBar) view.findViewById(R.id.celluar_progressbar);

        }
    }
}
