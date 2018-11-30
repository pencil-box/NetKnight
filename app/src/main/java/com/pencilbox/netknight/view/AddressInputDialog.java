package com.pencilbox.netknight.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pencilbox.netknight.R;
import com.pencilbox.netknight.presentor.IBlockingDomainPresenter;
import com.pencilbox.netknight.utils.IPCheckUtils;

/**
 * Created by wu on 16/7/8.
 */
public class AddressInputDialog extends DialogFragment {
    private EditText address_start;
    private EditText address_end;
    @Nullable
    private IBlockingDomainPresenter mIBlockingDomainPresenter;

    public void setPresenter(IBlockingDomainPresenter mIBlockingDomainPresenter) {
        this.mIBlockingDomainPresenter = mIBlockingDomainPresenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_input, container);
        address_start = view.findViewById(R.id.edit_adstartin);
        address_end = view.findViewById(R.id.edit_adstartend);
        Button ad_sure = view.findViewById(R.id.ad_sure);

        // mIBlockingDomainPresenter = new IBlockingDomainImpl(this);
        ad_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address_start.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if (address_end.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if (IPCheckUtils.isIP(address_end.getText().toString())) {
                    if (mIBlockingDomainPresenter != null) {
                        mIBlockingDomainPresenter.addBlockingDomain(address_start.getText().toString(),
                                address_end.getText().toString());
                    }
                    dismiss();
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


