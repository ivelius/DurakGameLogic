package com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast;


import com.yan.durak.gamelogic.commands.composite.FinishGameCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.SocketClient;
import com.yan.durak.gamelogic.communication.protocol.data.PlayerData;
import com.yan.durak.gamelogic.communication.protocol.messages.GameOverProtocolMessage;
import com.yan.durak.gamelogic.player.Player;
import com.yan.durak.gamelogic.player.RemotePlayer;

/**
 * Created by Yan-Home on 12/24/2014.
 * <p/>
 * This can be used as a post hook , to notify clients of game over results.
 */
public class GameOverBroadcastHook implements CommandHook<FinishGameCommand> {

    @Override
    public Class<FinishGameCommand> getHookTriggerCommandClass() {
        return FinishGameCommand.class;
    }

    @Override
    public void onHookTrigger(FinishGameCommand hookCommand) {

        //create json string from the message
        String jsonMsg = new GameOverProtocolMessage(new PlayerData(hookCommand.getLoosingPlayerIndex())).toJsonString();

        for (Player player : hookCommand.getGameSession().getPlayers()) {
            if (player instanceof RemotePlayer) {
                RemotePlayer remotePlayer = (RemotePlayer) player;
                SocketClient client = remotePlayer.getSocketClient();
                client.sendMessage(jsonMsg);
            }
        }
    }
}
