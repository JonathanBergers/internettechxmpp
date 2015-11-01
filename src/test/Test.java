package test;

import client.TestClient;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import model.RegisteredUser;
import model.User;
import server.Server;
import server.ServerSettings;
import xmpp.rules.Message;
import xmpp.rules.Registration;
import xmpp.rules.StanzaType;

/**
 * Created by jonathan on 30-10-15.
 */
public class Test {


    public static void main(String[] args) {
        new Test().testRegister();
    }
    public  void testSendMessage(){


//        Server s = new Server();
//        s.run();

        User user1 = new User("user@user1.com");
        User user2 = new User("user@user2.com");

        TestClient client = new TestClient(user1);
        client.run();
       // TestClient client1 = new TestClient(user1);

        //client1.run();


        client.write(Message.messageElement(user1.getEmail(), user2.getEmail(), StanzaType.Message.CHAT, "jo", "KP", "jo"));
       // client1.write(Message.messageElement(user1.getEmail(), user2.getEmail(), StanzaType.Message.CHAT, "jo", "KP", "jo"));








    }


    public  void testRegister(){


//        ServerSettings serverSettings = new ServerSettings();
//        Server s = new Server(serverSettings);
//        s.run();



        User user1 = new RegisteredUser("user@user1.com", "pass1");
        User user2 = new RegisteredUser("user@user2.com", "pass2");

        TestClient client = new TestClient(user1);
        client.run();
        //TestClient client1 = new TestClient(user1);
        //client1.run();


        client.write(Registration.Client.register(new ServerSettings().getXmppAddress(), user1.getEmail(), user1.getEmail(), "pass1"));


       // client1.write(Message.messageElement(user1.getEmail(), user2.getEmail(), StanzaType.Message.CHAT, "jo", "KP", "jo"));








    }


    public void testProtocols(){

        XMLElement element = Registration.Client.register(new ServerSettings().getXmppAddress(), "email", "email", "pass1");

        XMLProtocol protocol = Registration.requestRegistration(new ServerSettings());

        System.out.println(protocol.checkRecursive(element, protocol));


    }

}
