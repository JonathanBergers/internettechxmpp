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

    private static final int PORT = 2000;
    private static final String HOST = "localhost";

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    // run the server
    public static void main(String[] args) {

        new Server().run();
    }


    public void run(){




        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("server socket initialized");

            // wait for connection then add thread
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client socket accepted");
                executorService.submit(new Connection(socket));










                System.out.println("client socket connection established");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
