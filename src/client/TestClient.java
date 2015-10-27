package client;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.internal.stream.writers.XMLDOMWriterImpl;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import model.User;
import model.protocol.*;
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
    private static final int HOST_PORT = 12312;

    private User from  = new User("jonathan"), to = new User("falco");

    public static void main(String[] args) {
        new TestClient().run();
    }



    public void run(){




        XMPPElement messageElement = new XMPPElement("message").addAttribute(new XMPPAttribute("to", "falco")).addAttribute(new XMPPAttribute("from", "Jonathan")).addAttribute(new XMPPAttribute("type", "chat"));
        messageElement.addElement(new XMPPElement("subject", "joo")).addElement(new XMPPElement("body", "dit is de body"));

        System.out.println(messageElement.toString());

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
                    //new Message(to, from, "Nieuwbericht", "jo dit is een nieuw bericht jo", MessageType.CHAT).write(writer);
                    messageElement.write(writer);

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
