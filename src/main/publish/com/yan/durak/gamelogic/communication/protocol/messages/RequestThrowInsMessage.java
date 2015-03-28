package com.yan.durak.gamelogic.communication.protocol.messages;


import com.google.gson.annotations.SerializedName;
import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.communication.protocol.BaseProtocolMessage;
import com.yan.durak.gamelogic.communication.protocol.data.CardData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RequestThrowInsMessage extends BaseProtocolMessage<RequestThrowInsMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "requestThrowIns";

    public RequestThrowInsMessage(List<Card> possibleThrowInCards) {
        super();
        setMessageName(MESSAGE_NAME);
        List<CardData> cardDataList = convertCardDataList(possibleThrowInCards);
        setMessageData(new ProtocolMessageData(cardDataList));
    }

    private List<CardData> convertCardDataList(List<Card> possibleThrowInCards) {
        List<CardData> retList = new ArrayList<>();
        for (Card card : possibleThrowInCards) {
            retList.add(new CardData(card.getRank(), card.getSuit()));
        }
        return retList;
    }


    public static class ProtocolMessageData {

        @SerializedName("possibleThrowInCards")
        List<CardData> mPossibleThrowInCards;


        public ProtocolMessageData(List<CardData> possibleThrowInCards) {
            mPossibleThrowInCards = possibleThrowInCards;
        }

        public List<CardData> getPossibleThrowInCards() {
            return mPossibleThrowInCards;
        }

    }
}
