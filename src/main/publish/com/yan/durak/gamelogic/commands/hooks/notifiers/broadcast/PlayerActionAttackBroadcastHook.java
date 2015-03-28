package com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast;


import com.yan.durak.gamelogic.commands.custom.PlayerAttackRequestCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.RemoteClientsManager;
import com.yan.durak.gamelogic.communication.connection.SocketClient;
import com.yan.durak.gamelogic.communication.protocol.messages.PlayerTakesActionMessage;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class PlayerActionAttackBroadcastHook implements CommandHook<PlayerAttackRequestCommand> {


    @Override
    public Class<PlayerAttackRequestCommand> getHookTriggerCommandClass() {
        return PlayerAttackRequestCommand.class;
    }

    @Override
    public void onHookTrigger(PlayerAttackRequestCommand hookCommand) {

        //create json string from the message
        String jsonMsg = new PlayerTakesActionMessage(hookCommand.getAttackingPlayerIndex(), PlayerTakesActionMessage.PlayerAction.ATTACK).toJsonString();

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
