package xmpp.actions;

import generic.action.ProtocolAction;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import xmpp.rules.Contacts;
import xmpp.rules.Message;

/**
 * Created by jonathan on 1-11-15.
 */
public class MessageActions {



    /**Actie die uitgevoerd wordt wanneer een client een bericht verstuurd
     *
     * @return
     */
    ProtocolAction<XMLElement> sendMessage() {
        XMLProtocol protocol = Message.messageProtocol();


    }

}
