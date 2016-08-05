package com.idea.dto;

/**
 * Created by Administrator on 2016/5/21.
 */
public class Exposer {

    private boolean exposered;

    private long seckillId;

    private String md5;

    private long startTime;

    private long endTime;

    private long now;

    public Exposer() {
    }

    public Exposer(boolean exposered, String md5, long seckillId) {
        this.exposered = exposered;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposered, long seckillId) {
        this.exposered = exposered;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposered, long startTime, long endTime, long now){
        this.exposered = exposered;
        this.startTime = startTime;
        this.endTime = endTime;
        this.now = now;
    }

    public boolean isExposered() {
        return exposered;
    }

    public void setExposered(boolean exposered) {
        this.exposered = exposered;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposered=" + exposered +
                ", seckillId=" + seckillId +
                ", md5='" + md5 + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", now=" + now +
                '}';
    }
}
