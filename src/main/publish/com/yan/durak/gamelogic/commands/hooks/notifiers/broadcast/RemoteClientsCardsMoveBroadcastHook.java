package com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast;


import com.yan.durak.gamelogic.commands.core.MoveCardFromPileToPileCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.SocketClient;
import com.yan.durak.gamelogic.communication.protocol.messages.CardMovedProtocolMessage;
import com.yan.durak.gamelogic.player.Player;
import com.yan.durak.gamelogic.player.RemotePlayer;

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

        for (Player player : hookCommand.getGameSession().getPlayers()) {
            if (player instanceof RemotePlayer) {
                RemotePlayer remotePlayer = (RemotePlayer) player;
                SocketClient client = remotePlayer.getSocketClient();
                sendMessageToClient(jsonMsg, client);
            }
        }
    }

    private void sendMessageToClient(String jsonMsg, SocketClient client) {
        client.sendMessage(jsonMsg);
        //TODO : imitate waiting
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
