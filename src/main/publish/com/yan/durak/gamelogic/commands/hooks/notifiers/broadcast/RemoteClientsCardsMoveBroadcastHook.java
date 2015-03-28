package com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast;


import com.yan.durak.gamelogic.commands.core.MoveCardFromPileToPileCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.RemoteClientsManager;
import com.yan.durak.gamelogic.communication.connection.SocketClient;
import com.yan.durak.gamelogic.communication.protocol.messages.CardMovedProtocolMessage;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RemoteClientsCardsMoveBroadcastHook implements CommandHook<MoveCardFromPileToPileCommand> {

    @Override
    public Class<MoveCardFromPileToPileCommand> getHookTriggerCommandClass() {
        return MoveCardFromPileToPileCommand.class;
    }

    @Override
    public void onHookTrigger(MoveCardFromPileToPileCommand hookCommand) {
        String jsonMsg = new CardMovedProtocolMessage(hookCommand.getCardToMove().getRank(), hookCommand.getCardToMove().getSuit(), hookCommand.getFromPileIndex(), hookCommand.getToPileIndex()).toJsonString();

        for (SocketClient client : RemoteClientsManager.getInstance().getRemoteClients()) {
            client.sendMessage(jsonMsg);
            //TODO : imitate waiting
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
