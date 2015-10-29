package client;

import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import model.StanzaFactory;
import model.User;
import model.protocol.*;
import old.Message;
import old.MessageType;
import old.StreamMessage;

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


        XMPPElement mE = new XMPPElement(null, "StanzaMessage", "dit is een bericht");
        mE.addAttribute(new XMPPAttribute("id", "100"));
        mE.addElement("bodasy", "jooo");
        mE.addElement("nogbody", "asd asd joo2").addElement("jo");

        System.out.println(mE.toString());

        Message message = new Message(to, from, "subjeect", "body", MessageType.CHAT);

        while(true) {


            try {

                // connect to server
                Socket socket = new Socket(HOST_NAME, HOST_PORT);
                System.out.println("Client: socket connection established with server");

                // start the threads

                XMLStreamWriter writer = XMLStreamWriterFactory.create(socket.getOutputStream());


                StreamMessage m = new StreamMessage(to, from);
                try {
                   // m.write(writer);
                    //new StanzaMessage(to, from, "Nieuwbericht", "jo dit is een nieuw bericht jo", MessageType.CHAT).write(writer);
                    StanzaFactory.buildMessage("falco", "jonathan", "chat", "mess", "dit is het onderwerp", "dit is de bdody").write(writer);

                    //message.write(writer);
                   // m.write(writer);
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
