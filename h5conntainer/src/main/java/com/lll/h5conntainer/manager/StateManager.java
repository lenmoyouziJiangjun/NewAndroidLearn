package com.lll.h5conntainer.manager;
/**
 * Version 1.0
 * Created by lll on 17/5/4.
 * Description 用来判断是否采用Hy还是调用原生的
 * copyright generalray4239@gmail.com
 */
public class StateManager {
    private boolean isUsedHybrid = true;
    private String hybridCode = "1";
    private boolean isMatchMode = false;
    private static StateManager instance = null;

    private StateManager() {
    }

    public static StateManager getInstance() {
        if(instance == null) {
            Class var0 = StateManager.class;
            synchronized(StateManager.class) {
                StateManager temp = instance;
                if(temp == null) {
                    temp = new StateManager();
                    instance = temp;
                }
            }
        }

        return instance;
    }

    public void setUsedHybrid(String hybridCode) {
        this.hybridCode = hybridCode;
    }

    public boolean isUsedHybrid() {
        return "1".equals(this.hybridCode);
    }

    public boolean isCloseAllHybrid() {
        return "-1".equals(this.hybridCode);
    }

    public boolean isMatchMode() {
        return this.isMatchMode;
    }

    public void setMatchMode(boolean matchMode) {
        this.isMatchMode = matchMode;
    }
}
