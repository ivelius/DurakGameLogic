package com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast;


import com.yan.durak.gamelogic.commands.custom.PlayerRetaliationRequestCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.RemoteClientsManager;
import com.yan.durak.gamelogic.communication.connection.SocketClient;
import com.yan.durak.gamelogic.communication.protocol.messages.PlayerTakesActionMessage;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class PlayerActionRetaliateBroadcastHook implements CommandHook<PlayerRetaliationRequestCommand> {


    @Override
    public Class<PlayerRetaliationRequestCommand> getHookTriggerCommandClass() {
        return PlayerRetaliationRequestCommand.class;
    }

    @Override
    public void onHookTrigger(PlayerRetaliationRequestCommand hookCommand) {

        //create json string from the message
        String jsonMsg = new PlayerTakesActionMessage(hookCommand.getPlayerIndex(), PlayerTakesActionMessage.PlayerAction.RETALIATION).toJsonString();

        //notify all listeners
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
