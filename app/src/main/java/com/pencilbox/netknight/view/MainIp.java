package com.pencilbox.netknight.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.net.BlockingPool;
import com.pencilbox.netknight.presentor.BlockingIpImpl;
import com.pencilbox.netknight.presentor.IBlockingIpPresenter;

public class MainIp extends Fragment implements IBlockingIpView {
    private ListView listView;
//    private List<String> listIp;
//    private ListAdapter listAdapter;
    private IBlockingIpPresenter mBlockingIpPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ip_main, container, false);

        /*
         * 将数据库中的已有记录加载进listview
         */
        listView = view.findViewById(R.id.list_ip);
//        listIp = new ArrayList<String>();
//        for (int i=1;i<= org.litepal.LitePal.count(BlockIp.class);i++){
//            listIp.add(org.litepal.LitePal.find(BlockIp.class,i).getOriginIp());
//            listIp.add(org.litepal.LitePal.find(BlockIp.class,i).getEndIp());
//        }
//        listAdapter = new ListAdapter(this.getContext(),listIp);
//        listView.setAdapter(listAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.d("MainIp", "点击了" + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除么").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("MainIp", "删除咯");

                        mBlockingIpPresenter.deleteBlockingIp(position);

                    }
                }).create().show();

                return false;
            }
        });


        //设置默认的开关信息
        Switch mBlockingSwitch = view.findViewById(R.id.btn_ipswitch);
        mBlockingSwitch.setChecked(BlockingPool.isBlockIp);

        mBlockingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    BlockingPool.initIp();
                    BlockingPool.isBlockIp = true;
                } else {
                    BlockingPool.closeIp();
                    BlockingPool.isBlockIp = false;
                }
            }
        });


        mBlockingIpPresenter = new BlockingIpImpl(this, getActivity());
        mBlockingIpPresenter.loadBlockingIpList();


        view.findViewById(R.id.btn_ipadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpInputDialog dialog = new IpInputDialog();
                dialog.setPresenter(mBlockingIpPresenter);
                dialog.show(getFragmentManager(), "Dialog");

            }
        });

        return view;
    }

    @Override
    public void onLoadBlockingList(BaseAdapter adapter) {

        listView.setAdapter(adapter);
    }

    @Override
    public void onListRefresh() {

    }

    @Override
    public void onOptionFailed(int typeId, String msg) {

    }


}
