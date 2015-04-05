import com.yan.durak.gamelogic.GameStarter;

public class Main {

    public static void main(String[] args) {
        startGameWith3Bots();
    }

    private static void startGameWith3Bots() {
        (new GameStarter(null, null, null)).start();
    }

}
