import com.yan.durak.gamelogic.GameStarter;
import com.yan.durak.gamelogic.game.IGameRules;

public class Main {

    public static void main(String[] args) {
        startGameWith3Bots();
    }

    private static void startGameWith3Bots() {
        (new GameStarter(new IGameRules() {
            @Override
            public int getTotalPlayersInGameAmount() {
                return 3;
            }
        },null, null, null)).start();
    }

}
