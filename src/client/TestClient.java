package client;

import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import generic.xml.XMLAttribute;
import generic.xml.XMLElement;
import interfaces.Writable;
import model.User;
import old.StreamMessage;
import xmpp.StanzaFactory;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jonathan on 30-10-15.
 */
public class TestClient {

    private final String hostName;
    private final int port;
    private final User u;
    private Socket socket;


    XMLStreamReader xmlStreamReader;
    XMLStreamWriter xmlStreamWriter;

    public TestClient(String hostName, int port, User u) {
        this.hostName = hostName;
        this.port = port;
        this.u = u;
    }

    public TestClient(){

        this.hostName = "localhost";
        this.port = 8080;
        this.u = new User("default@user.com");
    }
    public TestClient(User u){

        this.hostName = "localhost";
        this.port = 8080;
        this.u = u;
    }


    public void run(){

            try {

                // connect to server
              socket = new Socket(hostName, port);
                System.out.println("Client: socket connection established with server");

                // start the threads

                xmlStreamWriter = XMLStreamWriterFactory.create(socket.getOutputStream());
                // init message
                StanzaFactory.Server.buildStream("server@server.com", u.getEmail());

            } catch (IOException e) {
                System.out.println("Cannot connect to server");
                e.printStackTrace();
            }


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    public void write(Writable w) {

        try {
            w.write(xmlStreamWriter);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
