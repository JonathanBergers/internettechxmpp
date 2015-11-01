package xmpp.rules;

import generic.xml.XMLProtocol;
import generic.xml.XMLElement;

/**
 * Created by jonathan on 1-11-15.
 * protocol en elementen voor openen en sluiten van streams
 *
 *
 * Waarom geen berichten BINNEN een stream
 *
 * een stream kan volgens xmpp allerlij berichten en commands bevatten.
 * omdat elk element recursief wordt uitgelezen zou je pas acties kunnen uitvoeren wanneer een volledige stream is ingelezen.
 *
 * op dit moment kunnen er geen streams opgesteld worden tussen users alleen met model en user
 *
 *
 *
 *
 */
public interface Stream {


    /**protocol voor een stream element
     *
     * @param open
     * @return
     */
    static XMLProtocol stream(boolean open){


        XMLProtocol<XMLElement> root = XMPPProtocols.elementHasNameWithText(XMPPStanzas.NAME_STREAM, false);

        root.addAttributeProtocol(XMPPProtocols.attributeHasName("to"));
        root.addAttributeProtocol(XMPPProtocols.attributeHasName("from"));

        // type moet een van de values van message type bevatten
        if(open)root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Stream.OPEN));
        else root.addAttributeProtocol(XMPPProtocols.attributeHasNameValue("type", StanzaType.Stream.CLOSE));

        root.addAttributeProtocol(XMPPProtocols.attributeHasName("id"));

        return root;


    }

    /**stream element
     *
     * @param open
     * @param to
     * @param from
     * @return
     */
    static XMLElement stream(boolean open, final String to, final String from){
        String type;
        if(open) type = StanzaType.Stream.OPEN; else type = StanzaType.Stream.CLOSE;

        return XMPPStanzas.createRootStanzaElement(XMPPStanzas.NAME_STREAM, to, from, type, "closeStream");
    }



}
