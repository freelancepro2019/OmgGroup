package com.elashry.omggroup.models;

import java.io.Serializable;

public class StopModel implements Serializable {
    private boolean isStop;

    public StopModel(boolean isStop) {
        this.isStop = isStop;
    }

    public boolean isStop() {
        return isStop;
    }
}
