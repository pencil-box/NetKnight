package com.pencilbox.netknight.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pencil-box on 16/6/21.
 */
public class AppUtils {

    /**
     * 获取应用包名
     */
    public static void getAppPackegeName(Context context){
        List<PackageInfo> packageList = context.getPackageManager().getInstalledPackages(0);


        String packageName;
        System.out.println("----------appName-----------");
        for(int i =0;i<packageList.size();i++){

            packageName = packageList.get(i).packageName;
            if(!isSystem(packageName,context)){

                System.out.println(packageList.get(i).packageName+"&&Uid is"+packageList.get(i).applicationInfo.uid);

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
