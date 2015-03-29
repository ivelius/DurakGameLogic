package com.yan.durak.gamelogic.communication.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Yan-Home on 12/24/2014.
 * <p/>
 * Implemented as a singleton.
 * Remote clients manager manages connected peers
 *
 * @deprecated Will be removed.
 * This class serves no purpose anymore.
 */
@Deprecated()
public class RemoteClientsManager {

    private static final RemoteClientsManager INSTANCE = new RemoteClientsManager();
    private static final int DEFAULT_PORT = 7000;
    private ArrayList<RemoteClient> mRemoteClients;
    private int mListeningPort;


    public static final RemoteClientsManager getInstance() {
        return INSTANCE;
    }

    private RemoteClientsManager() {
        mRemoteClients = new ArrayList<>();
        mListeningPort = DEFAULT_PORT;
    }

    /**
     * Blocks until remote client connects via socket
     *
     * @throws IOException
     */
    public RemoteClient waitForClientToConnect() throws IOException {
        RemoteClient remoteClient = null;
        ServerSocket listener = new ServerSocket(mListeningPort);
        try {
            //listen to communication.connection (Blocking)
            Socket socket = listener.accept();

            //communication.connection received , create a remote client
            remoteClient = new RemoteClient(socket);
            mRemoteClients.add(remoteClient);
        } finally {
            listener.close();
        }

        return remoteClient;
    }

    public void setListeningPort(int listeningPort) {
        mListeningPort = listeningPort;
    }

//    public ArrayList<RemoteClient> getRemoteClients() {
//        return mRemoteClients;
//    }
}
