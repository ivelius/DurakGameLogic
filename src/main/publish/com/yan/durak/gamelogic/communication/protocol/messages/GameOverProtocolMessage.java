package com.yan.durak.gamelogic.communication.protocol.messages;


import com.google.gson.annotations.SerializedName;
import com.yan.durak.gamelogic.communication.protocol.BaseProtocolMessage;
import com.yan.durak.gamelogic.communication.protocol.data.PlayerData;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class GameOverProtocolMessage extends BaseProtocolMessage<GameOverProtocolMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "gameOver";

    public GameOverProtocolMessage(PlayerData loosingPlayer) {
        super();
        setMessageName(MESSAGE_NAME);
        setMessageData(new ProtocolMessageData(loosingPlayer));
    }

    public static class ProtocolMessageData {

        @SerializedName("loosingPlayer")
        PlayerData mMovedCard;

        public ProtocolMessageData(PlayerData movedCard) {
            mMovedCard = movedCard;
        }

        public PlayerData getMovedCard() {
            return mMovedCard;
        }
    }
}
