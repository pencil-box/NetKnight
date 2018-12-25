package com.pencilbox.netknight.net;

import com.pencilbox.netknight.model.BlockIp;
import com.pencilbox.netknight.model.BlockName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by pencil-box on 16/7/10.
 * 管理阻塞ip以及域名信息
 */
public class BlockingPool {

    private static final List<BlockIp> sBlockingIpList = new ArrayList<>();
    private static final List<BlockName> sBlockingNameList = new ArrayList<>();
    //默认不阻断信息
    public static volatile boolean isBlockIp = false;
    public static volatile boolean isBlockName = false;

    public static void initIp() {
        sBlockingIpList.addAll(org.litepal.LitePal.findAll(BlockIp.class));
    }

    public static void initName() {
        sBlockingNameList.addAll(org.litepal.LitePal.findAll(BlockName.class));
    }

    public static void closeIp() {
        sBlockingIpList.clear();
    }

    public static void closeName() {
        sBlockingNameList.clear();
    }

    @NonNull
    static ArrayList<BlockIp> getIpList() {
        if (sBlockingIpList.isEmpty()) {
            initIp();
        }

        return (ArrayList<BlockIp>) sBlockingIpList;
    }

    @NonNull
    static ArrayList<BlockName> getNameList() {
        if (sBlockingNameList.isEmpty()) {
            initName();
        }
        return (ArrayList<BlockName>) sBlockingNameList;
    }
}
