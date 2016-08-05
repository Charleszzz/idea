package com.idea.enums;

/**
 * Created by Administrator on 2016/5/21.
 */
public enum  SeckillState {
    SUCCESS(1,"秒杀成功"),
    CLOSE(0,"秒杀关闭"),
    REPEAT(-1,"重复秒杀"),
    DATA_REWRITE(-2,"数据篡改"),
    INNER_ERROR(-3,"内部错误");

    private int state;

    private String stateInfo;

    public static SeckillState stateOf(int state){
        for (SeckillState seckillState :
                SeckillState.values()) {
            if (seckillState.state == state){
                return seckillState;
            }
        }
        return null;
    }

    SeckillState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
