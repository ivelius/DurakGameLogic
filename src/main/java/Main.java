import com.yan.durak.gamelogic.GameStarter;
import com.yan.durak.gamelogic.communication.connection.RemoteClient;
import com.yan.durak.gamelogic.communication.connection.RemoteClientsManager;
import com.yan.durak.gamelogic.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//        startGameWith2RemoteClients();
        startGameWith3Bots();
    }

    private static void startGameWith3Bots() {
        (new GameStarter(null, null, null)).start();
    }

    private static void startGameWith2RemoteClients() {
        ArrayList<RemoteClient> clients = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            try {
                LogUtils.log("Waiting for remote player " + clients.size() + " to connect");
                RemoteClient playerClient = RemoteClientsManager.getInstance().waitForClientToConnect();
                LogUtils.log(" Remote player " + clients.size() + " connected !");
                clients.add(playerClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        (new GameStarter(clients.get(0), clients.get(1), null)).start();
    }
}
