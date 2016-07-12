package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.BlockName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/12.
 */

public class AddressListAdapter extends BaseAdapter {
    private List<BlockName> datas = new ArrayList<BlockName>();
    private Context context;

    public AddressListAdapter(Context context, List<BlockName> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public BlockName getItem(int position) {

        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return datas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            hodler = new ViewHodler();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, null);
            hodler.textView1 = (TextView) convertView.findViewById(R.id.left_item);
            hodler.textView2 = (TextView) convertView.findViewById(R.id.rigth_item);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        hodler.textView1.setText(datas.get(position).getcName());
        hodler.textView2.setText(datas.get(position).getIp());
        return convertView;
    }

    private class ViewHodler {
        TextView textView1;
        TextView textView2;
    }
}
