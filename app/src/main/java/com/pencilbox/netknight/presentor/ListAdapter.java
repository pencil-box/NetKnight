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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/5.
 */

public class ListAdapter extends BaseAdapter implements View.OnClickListener {
    private List<String> datas = new ArrayList<String>();
    private Context context;

    public ListAdapter(Context context, List<String> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size() % 2 == 0 ? datas.size() / 2 : datas.size() / 2 + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
            hodler.layout1 = (LinearLayout) convertView.findViewById(R.id.item_layout1);
            hodler.layout2 = (LinearLayout) convertView.findViewById(R.id.item_layout2);

            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        String item1 = "";
        String item2 = "";

        if (position * 2 + 1 < datas.size()) {
            item1 = datas.get(position * 2);
            item2 = datas.get(position * 2 + 1);
        } else if (position * 2 + 1 == datas.size()) {
            item1 = datas.get(position * 2);
            item2 = "";
        }

        if (item1 != null) {
            hodler.textView1.setText(item1);
            hodler.layout1.setOnClickListener(this);
            hodler.layout1.setTag(item1);
        }

        if (item2 != null) {
            hodler.textView2.setText(item2);
            hodler.layout2.setOnClickListener(this);
            hodler.layout2.setTag(item2);
        }


        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    private class ViewHodler {
        TextView textView1;
        TextView textView2;
        LinearLayout layout1;
        LinearLayout layout2;
    }
}
