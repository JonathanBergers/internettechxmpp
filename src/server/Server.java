package server;

import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        this.hostName = "localhost";
        this.port = 8080;
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
