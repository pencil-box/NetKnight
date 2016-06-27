package com.pencilbox.netknight.presentor;

import android.os.AsyncTask;

import com.pencilbox.netknight.model.DemoModel;
import com.pencilbox.netknight.view.IDemoView;

/**
 * Created by pencil-box on 16/6/9.
 */
public class DemoPresenter implements IDemoPresenter {


    private IDemoView mIDemoView;


    public DemoPresenter(IDemoView iDemoView) {

        mIDemoView = iDemoView;


    }

    @Override
    public void doSomeThing() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {


                //执行耗时操作,如网络或者其他请求
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DemoModel demoModel = new DemoModel("#123");
                return demoModel.getDemoId();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mIDemoView.onDemoResult(s);

            }
        }.execute();


    }
}
