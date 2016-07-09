package com.pencilbox.netknight.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.pencilbox.netknight.Constants;
import com.pencilbox.netknight.model.App;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by pencil-box on 16/6/21.
 */
public class AppUtils {


    /**
     * 从appInfo中读取信息
     * @param context
     * @return
     */
    public static List<App> queryAppInfo(Context context){

        List<App> appList = DataSupport.findAll(App.class);


        if(appList == null||appList.size()==0){
            Log.d("AppUtils","执行初始化数据咯");
            initAppInfo(context);
            appList = DataSupport.findAll(App.class);

        }

        //获取icon的drawble信息

        for(int i=0;i<appList.size();i++){
            App appInfo = appList.get(i);
            try {
                appInfo.setIcon(context.getPackageManager().getApplicationIcon(appInfo.getPkgname()));
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("AppUtils","setIcon failed");
                e.printStackTrace();
            }

        }


        return  appList;
    }


    /**
     * 初始化appInfo数据,并将查询到的数据存入数据库中
     * @param context
     */
    private static void initAppInfo(Context context){

        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageList = pm.getInstalledPackages(0);


        String packageName;
        System.out.println("----------appName-----------");
        for(int i =0;i<packageList.size();i++){

            packageName = packageList.get(i).packageName;
            if(!isSystem(packageName,context)){

//                installedApp.add(packageList.get(i));
                System.out.println(packageList.get(i).applicationInfo.loadLabel(pm)+" "+packageList.get(i).packageName+"&&Uid is"+packageList.get(i).applicationInfo.uid);


                App app = new App();
                app.setUid(packageList.get(i).applicationInfo.uid+"");


                app.setName((String) packageList.get(i).applicationInfo.loadLabel(pm));
                app.setPkgname(packageName);

                app.setMobileDataType(Constants.ACCESS_TYPE_REMIND);
                app.setWifiType(Constants.ACCESS_TYPE_REMIND);

                if(app.save()){
                    Log.d("AppUtils","save success");
                }else{
                    Log.d("AppUtils","save failed");
                }
            }


        }


    }


    //判断是不是系统的包
    public static boolean isSystem(String packageName, Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            return ((info.applicationInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0);
            /*
            PackageInfo pkg = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            PackageInfo sys = pm.getPackageInfo("android", PackageManager.GET_SIGNATURES);
            return (pkg != null && pkg.signatures != null && pkg.signatures.length > 0 &&
                    sys.signatures.length > 0 && sys.signatures[0].equals(pkg.signatures[0]));
            */
        } catch (PackageManager.NameNotFoundException ignore) {
            return false;
        }
    }


    public static String getPackageNameByUid(Context context,int uid){


        PackageManager pm = context.getPackageManager();
        String[] packageName = pm.getPackagesForUid(uid);
        if(packageName!=null&&packageName.length==1){
            return packageName[0];
        }

        return "Null";
    }

}
