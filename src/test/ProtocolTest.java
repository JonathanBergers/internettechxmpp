package test;

import client.TestClient;
import model.User;
import server.Server;
import xmpp.rules.Message;
import xmpp.rules.StanzaType;
import generic.xml.XMLProtocol;
import generic.xml.XMLElement;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jonathan on 30-10-15.
 */
public class ProtocolTest {



    @Test
    public void testMessage(){
        // maak message
        XMLElement message = Message.messageElement("falco", "jona", StanzaType.Message.CHAT, "idididi", "subjeeect", "bodeeyy");
        System.out.println(message.toString());
        // maak protocol
        XMLProtocol messageProtocol = Message.messageProtocol();

        // kijk of bericht voldoet aan protocol
        boolean b = messageProtocol.checkRecursive(message, messageProtocol);

        assertTrue(b);

    }




    public static void testSendMessage(){


        Server s = new Server();


        User user1 = new User("user@user1.com");
        User user2 = new User("user@user2.com");

        TestClient client = new TestClient(user1);
        TestClient client1 = new TestClient(user1);


    }


}
