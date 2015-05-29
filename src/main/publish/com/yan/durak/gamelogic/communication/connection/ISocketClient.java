package com.yan.durak.gamelogic.communication.connection;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public interface ISocketClient {

    void sendMessage(String msg);
    String readMessage();
    void disconnect();
}
