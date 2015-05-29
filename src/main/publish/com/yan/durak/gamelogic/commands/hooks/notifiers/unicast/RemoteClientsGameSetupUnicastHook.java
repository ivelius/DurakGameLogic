package com.yan.durak.gamelogic.commands.hooks.notifiers.unicast;


import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.cards.Pile;
import com.yan.durak.gamelogic.commands.core.AddRemotePlayerCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.ISocketClient;
import com.yan.durak.gamelogic.communication.protocol.data.CardData;
import com.yan.durak.gamelogic.communication.protocol.data.PlayerData;
import com.yan.durak.gamelogic.communication.protocol.messages.GameSetupProtocolMessage;
import com.yan.durak.gamelogic.player.RemotePlayer;

import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RemoteClientsGameSetupUnicastHook implements CommandHook<AddRemotePlayerCommand> {


    @Override
    public Class<AddRemotePlayerCommand> getHookTriggerCommandClass() {
        return AddRemotePlayerCommand.class;
    }

    @Override
    public void onHookTrigger(AddRemotePlayerCommand hookCommand) {

        RemotePlayer addedPlayer = hookCommand.getAddedPlayer();

        //obtain remote client from the added player
        ISocketClient client = addedPlayer.getSocketClient();

        //obtain trump card
        List<Card> cardsInStockPile = hookCommand.getGameSession().findPileByTag(Pile.PileTags.STOCK_PILE_TAG).getCardsInPile();

        //get the trump card
        Card trumpCard = cardsInStockPile.get(0);

        //prepare game setup message
        String jsonMsg = new GameSetupProtocolMessage(
                new PlayerData(addedPlayer.getGameIndex(), addedPlayer.getPileIndex()), new CardData(trumpCard.getRank(), trumpCard.getSuit())).toJsonString();

        //send game setup message to client
        client.sendMessage(jsonMsg);
    }
}
