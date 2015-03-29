package factories;


import com.yan.durak.gamelogic.cards.Pile;
import com.yan.durak.gamelogic.game.GameSession;

/**
 * Created by Yan-Home on 1/22/2015.
 */
public class GameSessionFactory {

    public static GameSession createEmptyGameSession() {
        return new GameSession();
    }

    public static GameSession createGameSessionForThreePlayers() {
        GameSession gameSession = createEmptyGameSession();
        gameSession.getPilesStack().add(PileFactory.createEmptyPileWithTag(Pile.PileTags.STOCK_PILE_TAG));
        gameSession.getPilesStack().add(PileFactory.createEmptyPileWithTag(Pile.PileTags.DISCARD_PILE_TAG));
        gameSession.getPilesStack().add(PileFactory.createEmptyPileWithTag(Pile.PileTags.PLAYER_PILE_TAG));
        gameSession.getPilesStack().add(PileFactory.createEmptyPileWithTag(Pile.PileTags.PLAYER_PILE_TAG));
        gameSession.getPilesStack().add(PileFactory.createEmptyPileWithTag(Pile.PileTags.PLAYER_PILE_TAG));
        return gameSession;
    }
}
