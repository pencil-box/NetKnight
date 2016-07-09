package com.pencilbox.netknight.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pencilbox.netknight.R;

/**
 * Created by wu on 16/7/9.
 */

public class IpInputDialog extends DialogFragment {
    private EditText ip_start;
    private EditText ip_end;
    private Button ip_sure;

    public interface DataIpInputListnener {
        void onDataIpInputListener(String ipstart, String ipend);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ip_inputdialog, container);
        ip_start = (EditText) view.findViewById(R.id.edit_ipstartin);
        ip_end = (EditText) view.findViewById(R.id.edit_ipstartend);
        ip_sure = (Button) view.findViewById(R.id.ip_sure);
        ip_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataIpInputListnener listener = (DataIpInputListnener) getActivity();
                listener.onDataIpInputListener(ip_start.getText().toString(),
                        ip_end.getText().toString());
                IpInputDialog.this.dismiss();
            }
        });

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
