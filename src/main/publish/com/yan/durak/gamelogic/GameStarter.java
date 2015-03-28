package com.yan.durak.gamelogic;


import com.yan.durak.gamelogic.commands.custom.startgame.ScriptedPlayerWithBotsGameStartCommand;
import com.yan.durak.gamelogic.communication.connection.RemoteClient;
import com.yan.durak.gamelogic.game.GameRules;
import com.yan.durak.gamelogic.game.GameSession;

/**
 * Created by ybra on 19.12.2014.
 * <p/>
 * Temporary class that binds logic together
 * Later planned to be removed and replaced by more sophisticated system,
 */
public class GameStarter {
    private final GameSession mGameSession;

    /**
     * Accepts up to 3 players which are can be remote clients.
     * If instead of remote client null is provided , than player replaced with a BOT.
     */
    public GameStarter(RemoteClient playerZero, RemoteClient playerOne, RemoteClient playerTwo) {
        mGameSession = new GameSession();
        mGameSession.setGameRules(new GameRules());
        ScriptedPlayerWithBotsGameStartCommand gameStartCommand = new ScriptedPlayerWithBotsGameStartCommand();
        gameStartCommand.setRemotePlayers(playerZero,playerOne,playerTwo);
        mGameSession.addCommand(gameStartCommand);
    }

    public void start() {
        mGameSession.startSession();
    }
}