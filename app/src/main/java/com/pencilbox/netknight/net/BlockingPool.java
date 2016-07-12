package com.pencilbox.netknight.net;

import android.provider.ContactsContract;

import com.pencilbox.netknight.model.BlockIp;
import com.pencilbox.netknight.model.BlockName;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pencil-box on 16/7/10.
 * 管理阻塞ip以及域名信息
 */
public class BlockingPool {

    //默认不阻断信息
    public static volatile boolean isBlockIp = false;
    public static volatile boolean isBlockName = false;


    private static List<BlockIp>  sBlockingIpList;

    private static List<BlockName> sBlockingNameList;

    static{
        sBlockingIpList = new ArrayList<>();
        sBlockingNameList = new ArrayList<>();

    }


    public static void initIp(){
        sBlockingIpList = DataSupport.findAll(BlockIp.class);
    }

    public static void initName(){
        sBlockingNameList = DataSupport.findAll(BlockName.class);
    }


    public static void closeIp(){
        sBlockingIpList = null;
    }
    public static void closeName(){
        sBlockingNameList = null;
    }



    public static ArrayList<BlockIp> getIpList(){

        if(sBlockingIpList==null){
            sBlockingIpList = DataSupport.findAll(BlockIp.class);
        }


        return (ArrayList<BlockIp>) sBlockingIpList;
    }

    public static ArrayList<BlockName> getNameList(){
        if(sBlockingNameList==null){
            sBlockingNameList = DataSupport.findAll(BlockName.class);

        }

        return (ArrayList<BlockName>) sBlockingNameList;
    }

    /**
     * 需要传入含有id号的实体类,所以要存储数据库之后再操作
     * @param blockName
     */
    public static void addName(BlockName blockName){
        sBlockingNameList.add(blockName);
    }
    public static void addIp(BlockIp blockIp){
        sBlockingIpList.add(blockIp);
    }


    public static void removeName(int position){
        sBlockingNameList.remove(position);
    }
    public static void removeIp(int position){
        sBlockingIpList.remove(position);
    }
}
