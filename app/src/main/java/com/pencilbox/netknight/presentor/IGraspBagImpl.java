package com.pencilbox.netknight.presentor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.pencilbox.netknight.pcap.PCapFilter;
import com.pencilbox.netknight.view.IGraspBagView;
/**
 * Created by tang on 2016/6/28.
 */
public class IGraspBagImpl implements IGraspBagPresenter {

    private IGraspBagView mGraspBag;

    public IGraspBagImpl(IGraspBagView graspBag){


        mGraspBag = graspBag;
    }




    @Override
    public void startGraspingBag(int position) {

        if(position == -1){
            PCapFilter.startCapPacket(0);
        }


        //TODO 这里应有应用列表信息,并通过position 查找到相应的appId,然后进行传参抓取

    }

    private final int MSG_STOP_CAPTRUE = 1;

    private Handler mHandler  = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);

            switch (msg.what){
                case MSG_STOP_CAPTRUE:

                    mGraspBag.onGraspFininished((String) msg.obj);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void stopGraspingBag() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String fileName = PCapFilter.stopCapPacket();
                Message msg= Message.obtain();
                msg.what= MSG_STOP_CAPTRUE;
                msg.obj = fileName;
                mHandler.sendMessage(msg);

            }
        }).start();




    }

    @Override
    public void loadGraspGagData() {

        //执行相关操作
        //持续更新
    }
}
