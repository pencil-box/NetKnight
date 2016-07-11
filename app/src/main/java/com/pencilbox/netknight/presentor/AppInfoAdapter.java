package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    //存放用户选中的item咯,key 为 appId ,value 为 用户选中的position
    private Map<Long,Integer> mWiflSelectedMap;
    private Map<Long,Integer> mMobileSelectedMap;


    private Context mContext;

    public void addAll(List<App> appLists) {

        list_appinfo.addAll(appLists);


        for(int i=0;i<appLists.size();i++){

            mWiflSelectedMap.put(appLists.get(i).getId(),appLists.get(i).getWifiType());
            mMobileSelectedMap.put( appLists.get(i).getId(),appLists.get(i).getMobileDataType());

        }

    }

    public AppInfoAdapter(Context context) {
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mWiflSelectedMap = new HashMap<>();
        mMobileSelectedMap = new HashMap<>();


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

    public View getView(final int position, View convertview, ViewGroup arg2) {
        View view = null;
        ViewHolder holder = null;
        if (convertview == null || convertview.getTag() == null) {
            view = inflater.inflate(R.layout.app_items, null);



            holder = new ViewHolder(view);
//            holder.wifi_spinner = (Spinner) view.findViewById(R.id.wifi_spinner);
//            holder.celluar_spinner = (Spinner) view.findViewById(R.id.celluar_spinner);



            //我咋监听到是哪一个信息呢
            SpinnerAdapter adapter = new SpinnerAdapter(mContext);
            holder.wifi_spinner.setAdapter(adapter);
            holder.celluar_spinner.setAdapter(adapter);

            holder.celluar_spinner.setOnItemSelectedListener(new OnSpinnerItemListener(holder.celluar_spinner,false));
            holder.wifi_spinner.setOnItemSelectedListener(new OnSpinnerItemListener(holder.wifi_spinner,true));



            view.setTag(holder);
        } else {
            view = convertview;
            holder = (ViewHolder) convertview.getTag();
        }

        App appInfo = getItem(position);
        holder.appIcon.setImageDrawable(appInfo.getIcon());
        holder.tvAppLabel.setText(appInfo.getName());
        holder.tvPkgName.setText(appInfo.getPkgname());


        //存储spinner对应的appId的信息
        holder.celluar_spinner.setTag(appInfo);
        holder.wifi_spinner.setTag(appInfo);

//        int selectedPosition = mMobileSelectedMap.get(appInfo.getId());

        holder.celluar_spinner.setSelection(mMobileSelectedMap.get(appInfo.getId()));
        holder.wifi_spinner.setSelection(mWiflSelectedMap.get(appInfo.getId()));


        if(appInfo.isAccessVpn()){
            holder.wifi_spinner.setVisibility(View.VISIBLE);
           holder.celluar_spinner.setVisibility(View.VISIBLE);
            holder.tv_vpn_through.setVisibility(View.GONE);
        }else{
            holder.tv_vpn_through.setVisibility(View.VISIBLE);
            holder.wifi_spinner.setVisibility(View.GONE);
            holder.celluar_spinner.setVisibility(View.GONE);
        }

        return view;
    }


    /**
     * spinner的Item的监听器
     */
    private class OnSpinnerItemListener implements AdapterView.OnItemSelectedListener{

        Spinner mSpinner ;
        boolean isWifi = true;

        public OnSpinnerItemListener(Spinner spinner,boolean isWifi){
            mSpinner = spinner;
            this.isWifi = isWifi;
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



//            Log.d("AppInfoAdapter","appId:"+mSpinner.getTag()+" position"+position);
            //仅仅是为了存储用户选择了position的信息
            App appInfo = (App) mSpinner.getTag();

            if(isWifi){
                mWiflSelectedMap.put(appInfo.getId(),position);

                if(appInfo.getWifiType()!=position){
                    appInfo.setWifiType(position);
                    appInfo.save();
                }

            }else{
                mMobileSelectedMap.put(appInfo.getId(),position);
                if(appInfo.getMobileDataType()!=position){
                    appInfo.setMobileDataType(position);
                    appInfo.save();
                }

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {






        }
    }

    class ViewHolder {
        ImageView appIcon;
        TextView tvAppLabel, tvPkgName,tv_vpn_through;
        Spinner wifi_spinner,celluar_spinner;



        public ViewHolder(View view) {
            this.appIcon = (ImageView) view.findViewById(R.id.app_icon);
            this.tvAppLabel = (TextView) view.findViewById(R.id.tvAppLabel);
            this.tvPkgName = (TextView) view.findViewById(R.id.tvPkgName);
            this.wifi_spinner = (Spinner) view.findViewById(R.id.wifi_spinner);
            this.celluar_spinner= (Spinner) view.findViewById(R.id.celluar_spinner);
            this.tv_vpn_through = (TextView) view.findViewById(R.id.tv_vpn_through);
        }
    }
}
