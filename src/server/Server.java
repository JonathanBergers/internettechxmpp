package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jonathan on 12-10-15.
 */
public class Server {

    private final String hostName;
    private final int port;

    private ThreadPool threadPool;

    public Server(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public Server(){
        this.hostName = ServerSettings.STANDARD_HOSTNAME;
        this.port = ServerSettings.STANDARD_PORT;
    }



    // run the server
    public static void main(String[] args) {

        new Server().run();
    }


    public void run(){


        threadPool = new ThreadPool();


        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server socket initialized");

            // wait for connection then add thread
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client socket accepted");
                threadPool.addConnection(new Connection(socket));




                System.out.println("client socket connection established");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
