package com.pencilbox.netknight.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.IBlockingDomainImpl;
import com.pencilbox.netknight.presentor.IBlockingDomainPresenter;
import com.pencilbox.netknight.presentor.IBlockingIpPresenter;
import com.pencilbox.netknight.presentor.ListAdapter;
import com.pencilbox.netknight.utils.IPCheckUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/8.
 */

public class AddressInputDialog extends DialogFragment {
    private EditText address_start;
    private EditText address_end;
    private Button ad_sure;
    private IPCheckUtils ipCheckUtils = new IPCheckUtils();

    private IBlockingDomainPresenter mIBlockingDomainPresenter;


    public void setPresenter(IBlockingDomainPresenter mIBlockingDomainPresenter){
        this.mIBlockingDomainPresenter = mIBlockingDomainPresenter;
    }

    /*
    public interface DataInputListener {
        void onDataInputListener(String start, String end);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_input, container);
        address_start = (EditText) view.findViewById(R.id.edit_adstartin);
        address_end = (EditText) view.findViewById(R.id.edit_adstartend);
        ad_sure = (Button) view.findViewById(R.id.ad_sure);

        // mIBlockingDomainPresenter = new IBlockingDomainImpl(this);
        ad_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (address_start.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if (address_end.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if (ipCheckUtils.isIP(address_end.getText().toString())) {
                    mIBlockingDomainPresenter.addBlockingDomain(address_start.getText().toString(),
                            address_end.getText().toString());
                   // DataInputListener listener = (DataInputListener) getActivity();
                   // listener.onDataInputListener(address_start.getText().toString(),
                     //       address_end.getText().toString());
                    AddressInputDialog.this.dismiss();
                } else {
                    Toast.makeText(getActivity(),"请检查IP地址的有效性",Toast.LENGTH_LONG).show();
                }
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


