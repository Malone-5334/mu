package com.baselibrary.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtil {

    public void initial(int port) {
        try {
            ServerSocket serSco = new ServerSocket(port);

            Socket accept = serSco.accept();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
