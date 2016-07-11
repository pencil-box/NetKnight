package com.pencilbox.netknight.model;

import java.nio.ByteBuffer;

/**
 * Created by pencil-box on 16/7/10.
 */
public class PcapFile {

    private byte[] data;
    private long recordingTime;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getRecordingTime() {
        return recordingTime;
    }

    public void setRecordingTime(long recordingTime) {
        this.recordingTime = recordingTime;
    }
}
