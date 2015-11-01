package xmpp.rules;

import generic.xml.XMLElement;
import generic.xml.XMLProtocol;

/**
 * Created by jonathan on 1-11-15.
 */
public interface Message {


    static XMLProtocol messageProtocol(){


        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasName("message");

        root.addAttributeProtocol(XMPPProtocols.attributeHasName("to"));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Message.getAsList()));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("id"));


        // moet een subject en een body hebben
        root.addChildProtocol(XMPPProtocols.elementHasName("subject"));
        root.addChildProtocol(XMPPProtocols.elementHasName("body"));

        return root;


    }

    static XMLElement messageElement(final String to, final String  from, final String type, final String id,
                                          final String subject, final String body){

        XMLElement element = XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_MESSAGE, to, from, type, id);
        element.addElement("subject", subject);
        element.addElement("body", body);

        return element;


    }



}
