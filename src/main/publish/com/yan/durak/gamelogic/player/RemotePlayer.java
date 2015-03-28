package com.yan.durak.gamelogic.player;


import com.google.gson.Gson;
import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.cards.Pile;
import com.yan.durak.gamelogic.communication.connection.RemoteClient;
import com.yan.durak.gamelogic.communication.protocol.data.CardData;
import com.yan.durak.gamelogic.communication.protocol.messages.*;
import com.yan.durak.gamelogic.game.GameSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yan-Home on 12/30/2014.
 */
public class RemotePlayer extends BasePlayer {

    private RemoteClient mRemoteClient;
    private Gson mGson;

    public RemotePlayer(int indexInGame, GameSession gameSession, int pileIndex, RemoteClient remoteClient) {
        super(indexInGame, gameSession, pileIndex);
        mRemoteClient = remoteClient;
        mGson = new Gson();
    }

    @Override
    public Card getCardForAttack() {

        //send a message to remote client requesting a card for attack
        List<Card> cardsInHand = mGameSession.getPilesStack().get(getPileIndex()).getCardsInPile();
        RequestCardForAttackMessage request = new RequestCardForAttackMessage(cardsInHand);
        mRemoteClient.sendMessage(request.toJsonString());

        //waiting for client response (blocking)
        String response = mRemoteClient.readMessage();

        //get the card from the response
        Card cardForAttack = extractCardForAttackFromResponse(response);

        //TODO : validate that player has the card in hand ?

        return cardForAttack;
    }

    private Card extractCardForAttackFromResponse(String response) {
        ResponseCardForAttackMessage responseMessage = mGson.fromJson(response, ResponseCardForAttackMessage.class);
        String rank = responseMessage.getMessageData().getCardForAttack().getRank();
        String suit = responseMessage.getMessageData().getCardForAttack().getSuit();
        return new Card(rank, suit);
    }

    @Override
    public List<Pile> retaliatePiles(List<Pile> pilesToRetaliate) {

        //send a message to remote client requesting to cover piles
        RequestRetaliatePilesMessage request = new RequestRetaliatePilesMessage(pilesToRetaliate);
        mRemoteClient.sendMessage(request.toJsonString());

        //waiting for client response (blocking)
        String response = mRemoteClient.readMessage();

        //TODO : use message to assemble the new piles list
        List<Pile> retaliatedPiles = extractRetaliatedPilesFromResponse(response);

        //TODO : validate that player has the card in hand ?

        return retaliatedPiles;
    }

    @Override
    public List<Card> getThrowInCards(Collection<String> allowedRanksToThrowIn, int allowedAmountOfCardsToThrowIn) {

        List<Card> possibleThrowInCards = getPossibleThrowInCards(allowedRanksToThrowIn);

        //in case there is no cards that can be possibly throwed in
        //player will not be requested to throw in
        if (possibleThrowInCards.isEmpty())
            return possibleThrowInCards;

        //send a message to remote client requesting to cover piles
        RequestThrowInsMessage request = new RequestThrowInsMessage(possibleThrowInCards);
        mRemoteClient.sendMessage(request.toJsonString());

        //waiting for client response (blocking)
        String response = mRemoteClient.readMessage();

        return extractThrowInsFromResponse(response);
    }

    private List<Card> extractThrowInsFromResponse(String response) {

        ResponseThrowInsMessage responseMessage = mGson.fromJson(response, ResponseThrowInsMessage.class);
        if (responseMessage == null) {
            //TODO : do something !
            //probably player had disconnected and must be substituted with bots
        }

        List<CardData> selectedThrowIns = responseMessage.getMessageData().getSelectedThrowInCards();
        List<Card> retList = new ArrayList<>();

        for (CardData cardData : selectedThrowIns) {
            retList.add(new Card(cardData.getRank(), cardData.getSuit()));
        }

        return retList;
    }


    private List<Pile> extractRetaliatedPilesFromResponse(String response) {
        ResponseRetaliatePilesMessage responseMessage = mGson.fromJson(response, ResponseRetaliatePilesMessage.class);
        if (responseMessage == null) {
            //TODO : do something !
            //probably player had disconnected and must be substituted with bots
        }

        List<List<CardData>> pilesAfterRetaliation = responseMessage.getMessageData().getPilesAfterRetaliation();
        List<Pile> retList = new ArrayList<>();

        for (List<CardData> cardDataList : pilesAfterRetaliation) {
            Pile pile = new Pile();
            for (CardData cardData : cardDataList) {
                pile.addCardToPile(new Card(cardData.getRank(), cardData.getSuit()));
            }
            retList.add(pile);
        }
        return retList;
    }

    public RemoteClient getRemoteClient() {
        return mRemoteClient;
    }
}
