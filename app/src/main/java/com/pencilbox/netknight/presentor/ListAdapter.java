package com.pencilbox.netknight.presentor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.model.BlockIp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/5.
 */

public class ListAdapter extends BaseAdapter {
//    private List<String> datas = new ArrayList<String>();
    private List<BlockIp> datas = new ArrayList<BlockIp>();
    private Context context;

    public ListAdapter(Context context, List<BlockIp> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public BlockIp getItem(int position) {
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


        hodler.textView1.setText(datas.get(position).getOriginIp());
        hodler.textView2.setText(datas.get(position).getEndIp());

//        String item1 = "";
//        String item2 = "";
//
//        if (position * 2 + 1 < datas.size()) {
//            item1 = datas.get(position * 2);
//            item2 = datas.get(position * 2 + 1);
//        } else if (position * 2 + 1 == datas.size()) {
//            item1 = datas.get(position * 2);
//            item2 = "";
//        }
//
//        if (item1 != null) {
//            hodler.textView1.setText(item1);
////            hodler.layout1.setTag(item1);
//        }
//
//        if (item2 != null) {
//            hodler.textView2.setText(item2);
////            hodler.layout2.setTag(item2);
//        }


        return convertView;
    }

    private class ViewHodler {
        TextView textView1;
        TextView textView2;
    }
}
