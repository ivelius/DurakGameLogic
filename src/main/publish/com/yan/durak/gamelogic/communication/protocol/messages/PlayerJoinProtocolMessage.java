package com.yan.durak.gamelogic.communication.protocol.messages;


import com.google.gson.annotations.SerializedName;
import com.yan.durak.gamelogic.communication.protocol.BaseProtocolMessage;
import com.yan.durak.gamelogic.communication.protocol.data.PlayerData;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class PlayerJoinProtocolMessage extends BaseProtocolMessage<PlayerJoinProtocolMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "playerJoin";

    public PlayerJoinProtocolMessage(PlayerData playerData) {
        super();
        setMessageName(MESSAGE_NAME);
        setMessageData(new ProtocolMessageData(playerData));
    }

    public static class ProtocolMessageData {

        @SerializedName("joinedPlayerData")
        PlayerData mJoinedPlayerData;

        public ProtocolMessageData(PlayerData joinedPlayerData) {
            mJoinedPlayerData = joinedPlayerData;
        }

        public PlayerData getJoinedPlayerData() {
            return mJoinedPlayerData;
        }
    }
}
