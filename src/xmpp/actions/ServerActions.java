package xmpp.actions;

import interfaces.Writable;
import model.RegisteredUser;
import model.User;
import server.ServerSettings;

/**
 * Created by jonathan on 1-11-15.
 */
public interface ServerActions {


    ServerSettings getServerSettings();

    boolean registerUser(final String email, final String password);

    /**Voegt contact toe aan contactenlijst van gebruiker.
     *
     * @param subjectEmail gebruiker email waaraan contact toegevoegd moet worden
     * @param userEmail email vancontact
     * @return true als het gelukt is, false als er een van beide contacten niet geregistreert is
     */
    boolean addContact(final String subjectEmail, final String userEmail);



    void writeTo(final String email, Writable writable);




}
