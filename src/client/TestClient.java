package client;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.internal.stream.writers.XMLDOMWriterImpl;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import model.User;
import model.protocol.Message;
import model.protocol.MessageType;
import model.protocol.StreamMessage;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jonathan on 12-10-15.
 */
public class TestClient {

    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 2000;

    private User from  = new User("jonathan"), to = new User("falco");

    public static void main(String[] args) {
        new TestClient().run();
    }



    public void run(){


        while(true) {


            try {

                // connect to server
                Socket socket = new Socket(HOST_NAME, HOST_PORT);
                System.out.println("Client: socket connection established with server");

                // start the threads

                XMLStreamWriter writer = XMLStreamWriterFactory.create(socket.getOutputStream());


                StreamMessage m = new StreamMessage(to, from);
                try {
                    m.write(writer);
                    new Message(to, from, "Nieuwbericht", "jo dit is een nieuw bericht jo", MessageType.CHAT).write(writer);

                    m.write(writer);
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }


                socket.close();


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

    }




}
