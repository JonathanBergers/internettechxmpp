package server;

import interfaces.Writable;
import model.QueuedMessage;
import model.RegisteredUser;
import model.User;
import model.Model;

import java.net.Socket;
import java.util.*;

/**
 * Created by jonathan on 30-10-15.
 */
public class ThreadPool extends Thread implements Model {

    private volatile List<RegisteredUser> registeredUsers = new ArrayList<>();
    private volatile HashMap<User, XMPPConnection> activeUsers = new HashMap<>();
    private volatile Set<QueuedMessage> queuedMessages = new HashSet<>();
    private final ServerSettings serverSettings;

    private void addQueuedMessage(QueuedMessage message){
        queuedMessages.add(message);
    }

    private List<User> getActiveUsers() {
        List<User> users = new ArrayList<>();

        for(User user: activeUsers.keySet()){
            users.add(user);
        }
        return users;
    }

    private XMPPConnection getConnectionOfUser(User u){

        return activeUsers.get(u);
    }

    private XMPPConnection getConnectionOfUser(final String email){

        for(User u: getActiveUsers()){
            if(u.getEmail().equals(email)){
                return activeUsers.get(u);
            }
        }
        return null;
    }




    public ThreadPool(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }

    public void addConnection(Socket socket){
        XMPPConnection connection = new XMPPConnection(this, socket);
        connection.start();

        //writeQuedMessages();
    }

    public void addActiveUser(XMPPConnection xmppConnection){

        activeUsers.put(xmppConnection.getUser(), xmppConnection);

    }

    public RegisteredUser getRegisteredUser(String email){

        for (RegisteredUser u: registeredUsers){
            if(u.getEmail().equals(email)) return u;
        }
        return null;
    }



    @Override
    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    @Override
    public RegisteredUser registerUser(String email, String password) {

        if(getRegisteredUser(email)!= null) return null;

        RegisteredUser user = new RegisteredUser(email, password);
        registeredUsers.add(user);
        return user;

    }

    @Override
    public boolean addContact(String subjectEmail, String userEmail) {

        User u = getRegisteredUser(subjectEmail);

        if(u == null) return false;

        User c = getRegisteredUser(userEmail);

        if(c == null) return false;

        u.addContact(c);
        return true;
    }


    /**stuurt bericht naar de juiste persoon
     * als de persoon niet online is dan wordt het bericht gequeeud
     *
     * elke keer bij het versturen van een bericht wordt er geprobeerd de gequede berichten te versturen
     * @param email
     * @param writable
     */
    public void writeTo(String email, Writable writable) {


        User reciepient = getRegisteredUser(email);
        XMPPConnection c = getConnectionOfUser(email);

        if(reciepient == null){
            return;
        }

        if(c != null){
            c.writeResponse(writable);
        }else{
            addQueuedMessage(new QueuedMessage(writable, reciepient));
        }

        // kijken of er berichten verstuurd kunnen worden
        writeQuedMessages();



    }

    /**verstuurd berichten naar mensen die online zijn
     *
     */
    private void writeQuedMessages(){

        List<QueuedMessage> sentMessages = new ArrayList<>();

        for(QueuedMessage q: queuedMessages){

            User recipient = q.getUser();
            XMPPConnection c = getConnectionOfUser(recipient);

            if(c!=null){
                c.writeResponse(q);
            }
            sentMessages.add(q);

        }

        queuedMessages.removeAll(sentMessages);

    }

    @Override
    public RegisteredUser authenticateUser(String email, String password) {

        RegisteredUser u = getRegisteredUser(email);

        if(u == null) return null;

        if(!u.authenticate(password)){
            return null;
        }

        return u;
    }


    @Override
    public boolean hasUserWithEmail(String email) {

        return getRegisteredUser(email) != null;

    }

    @Override
    public List<User> getContactList(RegisteredUser user) {

        ArrayList<User> contacts = new ArrayList<>();
        RegisteredUser r = getRegisteredUser(user.getEmail());

        if(r == null){
            return contacts;
        }

        return r.getContacts();
    }



}
