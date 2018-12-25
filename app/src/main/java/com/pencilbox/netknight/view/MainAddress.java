package com.pencilbox.netknight.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.pencilbox.netknight.presentor.IBlockingDomainImpl;
import com.pencilbox.netknight.presentor.IBlockingDomainPresenter;

import java.util.Objects;


public class MainAddress extends Fragment implements IBlockingAddressView {
    private ListView listView;
    private IBlockingDomainPresenter iBlockingDomainPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.address_main, container, false);
        listView = view.findViewById(R.id.list_address);

        view.findViewById(R.id.btn_addressadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressInputDialog dialog = new AddressInputDialog();
                dialog.setPresenter(iBlockingDomainPresenter);
                dialog.show(Objects.requireNonNull(getFragmentManager()), "Dialog");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.d("MainAddrress", "点击了" + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除么").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("Mainaddress", "删除咯");

                        iBlockingDomainPresenter.deleteBlockingDomain(position);

                    }
                }).create().show();

                return false;
            }
        });

        //设置默认的开关信息
        Switch mBlockingSwitch = view.findViewById(R.id.btn_adswitch);
        mBlockingSwitch.setChecked(BlockingPool.isBlockName);
        mBlockingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    BlockingPool.initName();
                    BlockingPool.isBlockName = true;
                } else {
                    BlockingPool.closeName();
                    BlockingPool.isBlockName = false;
                }
            }
        });
        iBlockingDomainPresenter = new IBlockingDomainImpl(this, getActivity());
        iBlockingDomainPresenter.loadBlockingDomainList();

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadBlockingAddressList(BaseAdapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public void onListRefresh() {
    }
}
