package com.yan.durak.gamelogic.commands.custom.startgame;


import com.yan.durak.gamelogic.commands.BaseSessionCommand;
import com.yan.durak.gamelogic.commands.composite.PrepareGameSessionCommand;
import com.yan.durak.gamelogic.commands.composite.StartRoundCommand;
import com.yan.durak.gamelogic.commands.core.AddBotPlayerCommand;
import com.yan.durak.gamelogic.commands.core.AddRemotePlayerCommand;
import com.yan.durak.gamelogic.commands.custom.IdentifyNextRoundPlayersCommand;
import com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast.*;
import com.yan.durak.gamelogic.commands.hooks.notifiers.unicast.RemoteClientsGameSetupUnicastHook;
import com.yan.durak.gamelogic.commands.hooks.notifiers.unicast.RemoteClientsWrongCoverageNotifierUnicastHook;
import com.yan.durak.gamelogic.communication.connection.SocketClient;

/**
 * Created by Yan-Home on 12/22/2014.
 * <p/>
 * This command starts the game with provided player clients.
 */
public class GameStartCommand extends BaseSessionCommand {

    private SocketClient playerZero;
    private SocketClient playerOne;
    private SocketClient playerTwo;

    @Override
    public void execute() {

        //in order to keep our structure modular
        //we are using hooks to analyse commands and
        //take desired actions when needed.
        addHooks();

        //clear the game session and put discard and stock piles
        getGameSession().executeCommand(new PrepareGameSessionCommand());

        //Add 3 players
        addPlayer(playerZero);
        addPlayer(playerOne);
        addPlayer(playerTwo);

        //define who attacks and who defends
        IdentifyNextRoundPlayersCommand identifyCommand = new IdentifyNextRoundPlayersCommand();
        getGameSession().executeCommand(identifyCommand);

        //let player attack and the next player by it to defend
        StartRoundCommand startRoundCommand = new StartRoundCommand();
        startRoundCommand.setRoundAttackingPlayerIndex(identifyCommand.getNextRoundAttackerPlayerIndex());
        startRoundCommand.setRoundDefendingPlayerIndex(identifyCommand.getNextRoundDefenderPlayerIndex());
        getGameSession().executeCommand(startRoundCommand);
    }

    private void addPlayer(SocketClient playerClient) {

        //if there is no remote client for the player
        //add a bot player instead
        if (playerClient == null) {
            getGameSession().executeCommand(new AddBotPlayerCommand());
            return;
        }

        //add remote client player
        AddRemotePlayerCommand addRemotePlayerCommand = new AddRemotePlayerCommand();
        addRemotePlayerCommand.setRemoteClient(playerClient);
        getGameSession().executeCommand(addRemotePlayerCommand);
    }

    private void addHooks() {
        //this post hook notifies all remote clients about cards movement
        getGameSession().addPostHook(new RemoteClientsCardsMoveBroadcastHook());

        //this post hook notifies joined remote player about game setup
        //take in mind that it takes only the information that is available for that
        //point of time
        getGameSession().addPostHook(new RemoteClientsGameSetupUnicastHook());

        //Those pre hooks are used to notify the next player action
        //That allows the client to change the UI state accordingly
        getGameSession().addPreHook(new PlayerActionAttackBroadcastHook());
        getGameSession().addPreHook(new PlayerActionRetaliateBroadcastHook());
        getGameSession().addPreHook(new PlayerActionThrowInBroadcastPreHook());
        getGameSession().addPreHook(new PlayerActionTakeCardsBroadcastHook());

        //notify of player throw in decision
        getGameSession().addPostHook(new PlayerActionThrowInBroadcastPostHook());

        //add hook that will notify active player of wrong retaliation coverage
        getGameSession().addPostHook(new RemoteClientsWrongCoverageNotifierUnicastHook());

        //add post hook that will notify of game over conditions
        getGameSession().addPostHook(new GameOverBroadcastHook());

    }

    public void setRemotePlayers(SocketClient playerZero, SocketClient playerOne, SocketClient playerTwo) {
        this.playerZero = playerZero;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

}