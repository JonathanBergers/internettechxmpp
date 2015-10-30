package test;

import client.TestClient;
import model.User;
import server.Server;
import xmpp.StanzaFactory;
import xmpp.StanzaType;

/**
 * Created by jonathan on 30-10-15.
 */
public class Test {


    public static void main(String[] args) {
        new Test().testSendMessage();
    }
    public  void testSendMessage(){


//        Server s = new Server();
//        s.run();

        User user1 = new User("user@user1.com");
        User user2 = new User("user@user2.com");

        TestClient client = new TestClient(user1);
        client.run();
        TestClient client1 = new TestClient(user1);

        client1.run();


        client.write(StanzaFactory.Server.buildMessage(user1.getEmail(), user2.getEmail(), StanzaType.Message.CHAT, "jo", "KP", "jo"));

//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
