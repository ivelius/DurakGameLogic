package com.yan.durak.gamelogic.commands.custom.startgame;


import com.yan.durak.gamelogic.commands.BaseSessionCommand;
import com.yan.durak.gamelogic.commands.composite.PrepareGameSessionCommand;
import com.yan.durak.gamelogic.commands.composite.StartRoundCommand;
import com.yan.durak.gamelogic.commands.core.AddBotPlayerCommand;
import com.yan.durak.gamelogic.commands.core.AddRemotePlayerCommand;
import com.yan.durak.gamelogic.commands.custom.IdentifyNextRoundPlayersCommand;
import com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast.PlayerActionAttackBroadcastHook;
import com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast.PlayerActionRetaliateBroadcastHook;
import com.yan.durak.gamelogic.commands.hooks.notifiers.broadcast.RemoteClientsCardsMoveBroadcastHook;
import com.yan.durak.gamelogic.commands.hooks.notifiers.unicast.RemoteClientsGameSetupUnicastHook;
import com.yan.durak.gamelogic.commands.hooks.notifiers.unicast.RemoteClientsWrongCoverageNotifierUnicastHook;
import com.yan.durak.gamelogic.communication.connection.RemoteClient;

/**
 * Created by Yan-Home on 12/22/2014.
 * <p/>
 * This scenario starts a game with 2 bots and one human players.
 */
public class ScriptedPlayerWithBotsGameStartCommand extends BaseSessionCommand {

    private RemoteClient playerZero;
    private RemoteClient playerOne;
    private RemoteClient playerTwo;

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

    private void addPlayer(RemoteClient playerClient) {

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

        //add hook that will notify active player of wrong retaliation coverage
        getGameSession().addPostHook(new RemoteClientsWrongCoverageNotifierUnicastHook());

    }

    public void setRemotePlayers(RemoteClient playerZero, RemoteClient playerOne, RemoteClient playerTwo) {
        this.playerZero = playerZero;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

}