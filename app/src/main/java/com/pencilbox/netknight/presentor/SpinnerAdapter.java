package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pencilbox.netknight.R;

/**
 * Created by wu on 16/7/10.
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private String[] values;
    LayoutInflater inflater = null;

    public SpinnerAdapter(Context mContext) {
        this.mContext = mContext;
        Resources res = mContext.getResources();
        this.values = res.getStringArray(R.array.checklist_values);

    }

    @Override

    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        TextView spinner_value;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.spinner_value, null);
            holder = new ViewHolder();
            holder.spinner_value = (TextView) convertView
                    .findViewById(R.id.spinner_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String value = values[position];
        holder.spinner_value.setText(value);
        if ("允许联网".equals(value.trim())) {
            Log.d("CheckList", "alsdjflaskdjf");
            //holder.spinner_value.setTextColor(Color.RED);
        } else if ("禁止联网".equals(value)) {
           // holder.spinner_value.setTextColor(Color.GREEN);
        } else if ("联网时提示".equals(value)) {
           // holder.spinner_value.setTextColor(Color.BLACK);
        } else {
            //holder.spinner_value.setTextColor(Color.BLUE);
        }
        return convertView;
    }
}

