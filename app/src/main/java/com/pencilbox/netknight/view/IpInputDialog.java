package com.pencilbox.netknight.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.IBlockingIpPresenter;
import com.pencilbox.netknight.utils.IPCheckUtils;

/**
 * Created by wu on 16/7/9.
 * Modified by su on 16/7/12
 */

public class IpInputDialog extends DialogFragment {
    private EditText ip_start;
    private EditText ip_end;

    private IBlockingIpPresenter mIBlockingIpPresenter;

    public void setPresenter(IBlockingIpPresenter presenter) {
        mIBlockingIpPresenter = presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ip_inputdialog, container);
        ip_start = view.findViewById(R.id.edit_ipstartin);
        ip_end = view.findViewById(R.id.edit_ipstartend);
        Button ip_sure = view.findViewById(R.id.ip_sure);

        //这里的数据orz
        ip_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ip_start.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if (ip_end.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if (IPCheckUtils.isIP(ip_start.getText().toString()) && IPCheckUtils.isIP(ip_end.getText().toString())) {
                    mIBlockingIpPresenter.addBlockingIp(ip_start.getText().toString(), ip_end.getText().toString());
                    IpInputDialog.this.dismiss();
                } else {
                    Toast.makeText(getActivity(), "请检查IP地址的有效性", Toast.LENGTH_LONG).show();
                }
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
