package com.yan.durak.gamelogic.communication.protocol.messages;


import com.google.gson.annotations.SerializedName;
import com.yan.durak.gamelogic.communication.protocol.BaseProtocolMessage;
import com.yan.durak.gamelogic.communication.protocol.data.CardData;
import com.yan.durak.gamelogic.communication.protocol.data.PlayerData;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class GameSetupProtocolMessage extends BaseProtocolMessage<GameSetupProtocolMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "gameSetup";


    public GameSetupProtocolMessage(PlayerData myPlayerData, CardData trumpCard) {
        super();
        setMessageName(MESSAGE_NAME);
        setMessageData(new ProtocolMessageData(myPlayerData, trumpCard));
    }

    public static class ProtocolMessageData {

        //TODO : in future perhaps we will need more information

        @SerializedName("myPlayerData")
        PlayerData mMyPlayerData;

        @SerializedName("trumpCard")
        CardData mTrumpCard;

        public ProtocolMessageData(PlayerData myPlayerData, CardData trumpCard) {
            mMyPlayerData = myPlayerData;
            mTrumpCard = trumpCard;
        }

        public PlayerData getMyPlayerData() {
            return mMyPlayerData;
        }

        public CardData getTrumpCard() {
            return mTrumpCard;
        }
    }
}
