package test;

import client.TestClient;
import model.RegisteredUser;
import model.User;
import server.Server;
import server.ServerSettings;
import xmpp.rules.Authentication;
import xmpp.rules.Message;
import xmpp.rules.Registration;
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





    static String EMAIL1 = "user@user1.com", EMAIL2 = "user@user2.com";

    @Test
    public void registerUser1(){

        User user1 = new RegisteredUser(EMAIL1, "pass1");
        TestClient client1 = new TestClient(user1);
        client1.start();
        // registreer user 1
        client1.write(Registration.Client.register(new ServerSettings().getXmppAddress(), user1.getEmail(), user1.getEmail(), "pass1"));

    }
    @Test
    public void registerUser2(){

        User user1 = new RegisteredUser(EMAIL2, "pass1");
        TestClient client1 = new TestClient(user1);
        client1.run();
        // registreer user 1
        client1.write(Registration.Client.register(new ServerSettings().getXmppAddress(), user1.getEmail(), user1.getEmail(), "pass1"));

    }
    @Test
    public void authenticateUser1(){
        User user1 = new RegisteredUser(EMAIL1, "pass1");
        TestClient client1 = new TestClient(user1);
        client1.run();
        client1.write(Authentication.Client.login(new ServerSettings().getXmppAddress(), user1.getEmail(), user1.getEmail(), "pass1"));
    }








}
