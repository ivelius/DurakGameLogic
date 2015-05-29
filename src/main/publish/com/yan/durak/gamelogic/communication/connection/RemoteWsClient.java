package com.yan.durak.gamelogic.communication.connection;

import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocket.StringCallback;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Yan-Home on 5/29/2015.
 */
public class RemoteWsClient implements ISocketClient {

    private final WebSocket mWebSocket;
    private final Queue<String> mMessageQueue;

    public RemoteWsClient(WebSocket webSocket) {
        this.mWebSocket = webSocket;
        mMessageQueue = new LinkedBlockingDeque<>();

        //set socket callbacks
        mWebSocket.setStringCallback(new StringCallback() {
            @Override
            public void onStringAvailable(final String msg) {
                mMessageQueue.add(msg);
            }
        });
    }

    @Override
    public void sendMessage(String msg) {
        mWebSocket.send(msg);
    }

    @Override
    public String readMessage() {
        return mMessageQueue.poll();
    }

    @Override
    public void disconnect() {
        mWebSocket.close();
    }
}
