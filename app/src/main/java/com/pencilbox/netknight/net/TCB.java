/*
** Copyright 2015, Mohamed Naufal
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

package com.pencilbox.netknight.net;

import com.pencilbox.netknight.receiver.NetChangeReceiver;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Transmission Control Block
 * 传输控制块,用来监视每条连接的,每条channel对应一条与应用的连接
 */
public class TCB
{
    public String ipAndPort;

    //记录发送的数据咯
    private int sendMobileBytes =0;
    private int sendWifiBytes =0;

    //最后再存储相应的信息咯
    public int getMobileBytes(){
        return sendMobileBytes;
    }
    public int getWifiBytes(){
        return sendWifiBytes;
    }

    public void caculateTransBytes(int payloadSize){

        if(NetChangeReceiver.sNetState == NetChangeReceiver.NET_STATE_WIFI){
            sendWifiBytes+=payloadSize;
            return ;
        }
        if(NetChangeReceiver.sNetState == NetChangeReceiver.NET_STATE_MOBILE){
            sendMobileBytes += payloadSize;
            return;
        }
    }


    //客户端的顺序码,每次发送多少数据就加多少,普通的无负载的数据包算做是1byte
    public long mySequenceNum, theirSequenceNum;
    //客户端的ack码,为对方发来的seq码加上其发送的数据大小
    public long myAcknowledgementNum, theirAcknowledgementNum;
//    public TCBStatus status;

    //Tcp的状态信息
    public static final int TCB_STATUS_SYN_SENT = 1;
    public static final int TCB_STATUS_SYN_RECEIVED =2;
    public static final int TCB_STATUS_ESTABLISHED = 3;
    public static final int TCB_STATUS_CLOSE_WAIT = 4;
    public static final int TCB_STATUS_LAST_ACK = 5;

    public int tcbStatus = -1;



    // TCP has more states, but we need only these
//    public enum TCBStatus
//    {
//        SYN_SENT,
//        SYN_RECEIVED,
//        ESTABLISHED,
//        CLOSE_WAIT,
//        LAST_ACK,
//    }

    //用来封装生成数据包
    public Packet referencePacket;

//    public SocketChannel channel;
//    public boolean waitingForNetworkData;
    public SelectionKey selectionKey;

//    private static final int MAX_CACHE_SIZE = 50; // XXX: Is this ideal?
//    private static LRUCache<String, TCB> tcbCache =
//            new LRUCache<>(MAX_CACHE_SIZE, new LRUCache.CleanupCallback<String, TCB>()
//            {
//                @Override
//                public void cleanup(Map.Entry<String, TCB> eldest)
//                {
//                    eldest.getValue().closeChannel();
//                }
//            });



//    public static TCB getTCB(String ipAndPort)
//    {
//        synchronized (tcbCache)
//        {
//            return tcbCache.get(ipAndPort);
//        }
//    }
//
//    public static void putTCB(String ipAndPort, TCB tcb)
//    {
//        synchronized (tcbCache)
//        {
//            tcbCache.put(ipAndPort, tcb);
//        }
//    }

    public TCB(String ipAndPort, long mySequenceNum, long theirSequenceNum, long myAcknowledgementNum, long theirAcknowledgementNum,
              Packet referencePacket)
    {
        this.ipAndPort = ipAndPort;

        this.mySequenceNum = mySequenceNum;
        this.theirSequenceNum = theirSequenceNum;
        this.myAcknowledgementNum = myAcknowledgementNum;
        this.theirAcknowledgementNum = theirAcknowledgementNum;

//        this.channel = channel;
        this.referencePacket = referencePacket;
    }





//    public static void closeTCB(TCB tcb)
//    {
//        tcb.closeChannel();
//        synchronized (tcbCache)
//        {
//            tcbCache.remove(tcb.ipAndPort);
//        }
//    }

//    public static void closeAll()
//    {
//        synchronized (tcbCache)
//        {
//            Iterator<Map.Entry<String, TCB>> it = tcbCache.entrySet().iterator();
//            while (it.hasNext())
//            {
//                it.next().getValue().closeChannel();
//                it.remove();
//            }
//        }
//    }

//    private void closeChannel()
//    {
//        try
//        {
//            channel.close();
//        }
//        catch (IOException e)
//        {
//            // Ignore
//        }
//    }
}
