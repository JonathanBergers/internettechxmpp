package server;

import generic.xml.XMLElement;
import model.User;
import xmpp.StanzaFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jonathan on 30-10-15.
 */
public class ThreadPool extends Thread{

    private volatile List<User> users = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();


    public void addConnection(Connection connection){
        connections.add(connection);
        connection.run();
    }




    public void addActiveUser(User u){
        if(!users.contains(u)){
            users.add(u);
        }

    }

    public User getUser(String email){

        for (User u: users){
            if(u.getEmail().equals(email)) return u;
        }
        return null;
    }


    public boolean sendMessage(XMLElement element, User u) {
        for(Connection c: connections){

            try {
                if(c.hasUser(u)){

                    return c.write(element);

                }
            } catch (Exception e) {
                // wannneer er geen geldige connectie is, verwijder
                e.printStackTrace();
                connections.remove(c);
            }
        }

        return false;

    }
}
