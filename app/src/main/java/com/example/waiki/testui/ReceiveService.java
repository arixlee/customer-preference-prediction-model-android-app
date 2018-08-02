package com.example.waiki.testui;

import android.os.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Fung Desk on 29-Sep-17.
 */

public class ReceiveService {

    Thread serviceThread;
    ServerSocket serverSocket;
    public  ReceiveService(){
        startReceiveService();
    }
    public void startReceiveService(){
        serviceThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int port=12345;
                    serverSocket = new ServerSocket(port);

                    Socket connectedSocket = serverSocket.accept();
                    Message clientMessage = Message.obtain();
                    ObjectInputStream ois = new ObjectInputStream(connectedSocket.getInputStream());
                    String strMessage = ois.toString();
                    clientMessage.obj=strMessage;
                    //mHandler.sendMessage(clientMessage);
                    ObjectOutputStream oos=new ObjectOutputStream(connectedSocket.getOutputStream());
                    oos.write("from android htc e9+".getBytes());
                    ois.close();
                    oos.close();
                    serverSocket.close();
                    System.out.print(strMessage);
                }catch(Exception e){

                }
            }
        });
        serviceThread.start();
    }
}
