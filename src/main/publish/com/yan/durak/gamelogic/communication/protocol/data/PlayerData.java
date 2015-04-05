package com.yan.durak.gamelogic.communication.protocol.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yan-Home on 4/5/2015.
 */
public class PlayerData {

    @SerializedName("playerIndex")
    private int mPlayerIndex;

    public PlayerData(int playerIndex) {
        mPlayerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return mPlayerIndex;
    }
}
