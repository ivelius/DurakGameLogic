package com.yan.durak.gamelogic.commands.hooks.notifiers.unicast;


import com.yan.durak.gamelogic.commands.control.RetaliationValidationControlCommand;
import com.yan.durak.gamelogic.commands.hooks.CommandHook;
import com.yan.durak.gamelogic.communication.connection.SocketClient;
import com.yan.durak.gamelogic.communication.protocol.data.CardData;
import com.yan.durak.gamelogic.communication.protocol.data.RetaliationSetData;
import com.yan.durak.gamelogic.communication.protocol.messages.RetaliationInvalidProtocolMessage;
import com.yan.durak.gamelogic.player.Player;
import com.yan.durak.gamelogic.player.RemotePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RemoteClientsWrongCoverageNotifierUnicastHook implements CommandHook<RetaliationValidationControlCommand> {

    @Override
    public Class<RetaliationValidationControlCommand> getHookTriggerCommandClass() {
        return RetaliationValidationControlCommand.class;
    }

    @Override
    public void onHookTrigger(RetaliationValidationControlCommand hookCommand) {

        //we don't want to send wrong retaliation if it is actually not wrong
        if(hookCommand.getFailedValidationsList().isEmpty())
            return;

        //obtain retaliated player
        Player retaliatedPlayer = hookCommand.getRetaliatedPlayer();

        //obtain remote client from the player
        SocketClient client;
        if (retaliatedPlayer instanceof RemotePlayer) {
            client = ((RemotePlayer) retaliatedPlayer).getSocketClient();
        } else {
            //in case the player is not a remote player , we have nothing to do
            return;
        }

        //adapt data to protocol message
        List<RetaliationSetData> retaliationSetDataList = new ArrayList<>();
        for (RetaliationValidationControlCommand.ValidationDetails validationDetails : hookCommand.getFailedValidationsList()) {

            //add validation data to the array list
            retaliationSetDataList.add(new RetaliationSetData(new CardData(validationDetails.getCoveredCard().getRank(), validationDetails.getCoveredCard().getSuit()),
                    new CardData(validationDetails.getCoveringCard().getRank(), validationDetails.getCoveringCard().getSuit())));
        }

        //prepare json message
        String jsonMsg = new RetaliationInvalidProtocolMessage(retaliationSetDataList).toJsonString();

        //send message to client
        client.sendMessage(jsonMsg);
    }
}
