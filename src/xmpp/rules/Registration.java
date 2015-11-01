package xmpp.rules;




import generic.xml.XMLProtocol;
import generic.xml.XMLElement;
import server.ServerSettings;

import static xmpp.rules.XMPPProtocols.attributeHasNameValue;

/**
 * Created by jonathan on 1-11-15.
 * bevat alle protocollen van de berichten die ontvangen moeten kunnen worden als een gebruiker wilt registreren
 * bevat ook alle berichten die verstuurd moeten worden als antwoord op de requests
 */
public interface Registration {


    String CLIENT_ID_REQ_REGISTER_FIELDS = "register1";
    String SERVER_ID_RETURN_REGISTER_FIELDS = "regiser1";
    String CLIENT_ID_SEND_REGISTER_FIELDS_FILLED = "register2";
    String SERVER_ID_RETURN_REGISTER_RESPONSE = "register2";


    /**Het protocol voor het bericht wat de client als eerste stuurt wanneer deze wilt registreren
     *
     * @return
     */
    static XMLProtocol requestRegistrationFields(final ServerSettings serverSettings){

        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        // bericht aan model
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("to", serverSettings.getXmppAddress()));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.GET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_REQ_REGISTER_FIELDS));



        // moet een query bevatten
        root.addChildProtocol(XMPPProtocols.elementHasNameAndNoChildren(XMPPStanzas.NAME_QUERY));

        return root;

    }


    /**Het bericht dat de model verstuurd als antwoord op het request registreer bericht van de client
     *
     * @return
     */
    static XMLElement responseRegistrationFields(final String to, final ServerSettings serverSettings){

        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.RESULT, SERVER_ID_RETURN_REGISTER_FIELDS);


        // met query result
        XMLElement queryElement = element.addElement(XMPPStanzas.NAME_QUERY);
        queryElement.addElement("email");
        queryElement.addElement("password");
        return element;


    }

    /**Het protocol voor het bericht dat verstuurd wordt wanneer een client registreeert met zijn gegevens
     *
     * @return
     */
    static XMLProtocol requestRegistration(final ServerSettings serverSettings){

        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_COMMAND, false);
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("to", serverSettings.getXmppAddress()));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Query.SET));
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("id", CLIENT_ID_SEND_REGISTER_FIELDS_FILLED));
        // moet ingevulde username hebben


        // moet een query bevatten
        XMLProtocol queryProtocol = root.addChildProtocol(XMPPProtocols.elementHasName(XMPPStanzas.NAME_QUERY));

        queryProtocol.addChildProtocol(XMPPProtocols.elementHasNameWithText("email", true));
        queryProtocol.addChildProtocol(XMPPProtocols.elementHasNameWithText("password", true));

        return root;

    }

    /**De query die verstuurt moet worden wanneer heeft geprobeerd te registreren
     *
     * @param to
     * @return
     */
    static XMLElement responseRegistration(final ServerSettings serverSettings, final String to){

        XMLElement rootElement =  XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.RESULT, SERVER_ID_RETURN_REGISTER_RESPONSE);

        // met leeg element
        rootElement.addElement(XMPPStanzas.NAME_QUERY);
        return rootElement;

    }

    /**De query die verstuurt moet worden wanneer heeft geprobeerd te registreren maar het niet gelukt is
     *
     * @param to
     * @return
     */
    static XMLElement responseRegistration(final ServerSettings serverSettings, final String to, XMLElement error, XMLElement query){

       XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, serverSettings.getXmppAddress(), StanzaType.Query.ERROR, SERVER_ID_RETURN_REGISTER_RESPONSE);
        element.addElement(query);
        element.addElement(error);
        return element;

    }

    static class Client{



        public static XMLElement register(final String to, final String from, final String email, final String password){

            XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_COMMAND, to, from, StanzaType.Query.SET, CLIENT_ID_SEND_REGISTER_FIELDS_FILLED);
            XMLElement queryElement = element.addElement(XMPPStanzas.NAME_QUERY);

            queryElement.addElement("email", email);
            queryElement.addElement("password", password);

            return element;
        }

    }



}
