package xmpp.rules;

import generic.xml.XMLProtocol;
import generic.xml.XMLElement;
import model.User;
import server.ServerSettings;

import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 * de protocollen van de berichten die verstuurd worden door de client voor het opvragen van contacten en toevoegen van contacten
 * de berichten die verstuurd kunnen worden door de model als antwoord
 *
 * gebruikers kunnen elkaar contact verzoeken doen, wanneer een gebruiker accepteerd dan wordt het contact toegevoegd
 */
public interface Contacts {


    String CLIENT_ID_REQ_CONTACTLIST = "contactlist1";
    String SERVER_ID_RESPONSE_CONTACTLIST = "contactlist1";

    String CLIENT_ID_REQ_ADDCONTACT = "contactadd1";
    String SERVER_ID_RESPONSE_ADDCONTACT = "contactadd1";

    String CLIENT_ID_RESPONSE_ADDCONTACT = "contactadd2";





    // protocol vraag contacten


    static XMLProtocol requestContactList(final ServerSettings serverSettings){


        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("to", serverSettings.getXmppAddress()));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.GET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_REQ_CONTACTLIST));

        // lege query
        root.addChild(XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_QUERY, false));

        return root;

    }

    // antwoord lijst contacten

    /**De query die verstuurt moet worden wanneer gebruiker contacten wilt inzien
     *
     * @param to
     * @return
     */
    static XMLElement responseContacts(final String to, final ServerSettings serverSettings, final List<User> users) {
        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.RESULT, SERVER_ID_RESPONSE_CONTACTLIST);

        // voeg lijst met user toe aan command
        element.addElement(Query.contactList(users));
        return element;
    }


    /**Het protocol voor het bericht dat de c stuurt wanneer deze een gebruiker wilt toevoegen
     *
     * verzoek om gebruiker toe te voegen
     *
     * @return
     */
    static XMLProtocol requestAddContact(){

        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        // bericht moet aan de model zijn
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("to"));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.GET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_REQ_ADDCONTACT));


        // moet een query bevatten met daarin username
        root.addChildProtocol(XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_QUERY, true));


        return root;

    }



    // message return result

    /**bericht dat wordt verzonden op antwoord van contact toevoeg request
     * wanneer bericht goed is aan gekomen en deze verstuurd is naar de gebruiker, of zal worden
     *
     * @param to
     * @param serverSettings
     * @return
     */
    static XMLElement serverResponseAddContact(final String to, final ServerSettings serverSettings){
        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.RESULT, SERVER_ID_RESPONSE_ADDCONTACT);
        element.addElement(XMPPStanzas.NAME_QUERY);
        return element;

    }
    /**bericht dat wordt verzonden op antwoord van contact toevoeg request
     * wanneer bericht goed is aan gekomen en deze verstuurd is naar de gebruiker, of zal worden
     *
     * @param to
     * @param serverSettings
     * @return
     */
    static XMLElement serverResponseAddContact(final String to, final ServerSettings serverSettings, final XMLElement error, final XMLElement query){
        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.ERROR, SERVER_ID_RESPONSE_ADDCONTACT);
        element.addElement(query);
        element.addElement(error);
        return element;

    }



    /**Het protocol voor het bericht dat de c stuurt wanneer deze een gebruiker wilt toevoegen
     *
     * verzoek om gebruiker toe te voegen
     *
     * @return
     */
    static XMLProtocol clientResponseAddContact(){

        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        // bericht moet aan de model zijn
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("to"));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.SET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_RESPONSE_ADDCONTACT));


        // moet een query bevatten met daarin antwoord op verzoek
        root.addChildProtocol(XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_QUERY, true));


        return root;

    }



}
