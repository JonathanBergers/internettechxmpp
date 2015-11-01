package xmpp.rules;

import generic.xml.XMLProtocol;
import generic.xml.XMLElement;
import server.ServerSettings;

/**
 * Created by jonathan on 1-11-15.
 * bevat alle protocollen voor de berichten die ontvangen moeten kunnen worden voor het authenticatie process
 * bevat ook alle berichten die verstuurt moeten worden als antwoord op de protocollen.
 *
 *
 */
public interface Authentication {

    String CLIENT_ID_REQ_LOGIN_FIELDS = "login1";
    String SERVER_ID_RETURN_LOGIN_FIELDS = "login1";
    String CLIENT_ID_SEND_LOGIN_FIELDS_FILLED = "login2";
    String SERVER_ID_RETURN_LOGIN_RESPONSE = "login2";


    /**Het protocol voor het bericht wat de client als eerste stuurt wanneer deze wilt inloggen
     *
     *
     * @return
     */
    static XMLProtocol requestLoginFields(final ServerSettings serverSettings){

        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        // bericht moet aan de model zijn
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("to", serverSettings.getXmppAddress()));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.GET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_REQ_LOGIN_FIELDS));


        // moet een lege query bevatten
        root.addChildProtocol(XMPPProtocols.elementHasNameAndNoChildren(XMPPStanzas.NAME_QUERY));

        return root;

    }


    /**Het bericht dat de model verstuurd als antwoord op het request login bericht van de client
     *
     * @return
     */
    static XMLElement responseLoginFields(final String to, final ServerSettings serverSettings){

        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.RESULT, SERVER_ID_RETURN_LOGIN_FIELDS);
        XMLElement queryElement = element.addElement(XMPPStanzas.NAME_QUERY);

        queryElement.addElement("email");
        queryElement.addElement("password");
        return element;


    }

    /**Het protocol voor het bericht dat verstuurd wordt door client wanneer deze inlogt met zijn gegevens
     *
     * @return
     */
    static XMLProtocol requestAuthentication(final ServerSettings serverSettings){

        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("to", serverSettings.getXmppAddress()));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.SET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_SEND_LOGIN_FIELDS_FILLED));

        //TODO QUERY

        // moet ingevulde username hebben
        root.addChildProtocol(XMPPProtocols.elementHasNameWithText("username", true));
        root.addChildProtocol(XMPPProtocols.elementHasNameWithText("password", true));

        return root;

    }

    /**De query die verstuurt moet worden wanneer heeft geprobeerd in te loggen
     *
     * @param to
     * @return
     */
    static XMLElement responseAuthentication(final ServerSettings serverSettings, final String to){
        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.RESULT, SERVER_ID_RETURN_LOGIN_RESPONSE);
        element.addElement(XMPPStanzas.NAME_QUERY);

        return element;
    }

    /**De query die verstuurt moet worden wanneer heeft geprobeerd in te loggen maar het niet gelukt is
     *
     * @param to
     * @return
     */
    static XMLElement responseAuthentication(final ServerSettings serverSettings, final String to, XMLElement error, XMLElement query){
        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.ERROR, SERVER_ID_RETURN_LOGIN_RESPONSE);
        element.addElement(error);
        element.addElement(query);
        return element;

    }


    static class Client{



        static XMLElement login(final String to, final String from, final String email, final String password){

            XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, from, StanzaType.Query.GET, CLIENT_ID_SEND_LOGIN_FIELDS_FILLED);
            XMLElement queryElement = element.addElement(XMPPStanzas.NAME_QUERY);

            queryElement.addElement("email", email);
            queryElement.addElement("password", password);

            return element;
        }

    }




}
