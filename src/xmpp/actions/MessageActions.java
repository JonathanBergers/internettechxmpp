package xmpp.actions;

import generic.xml.XMLProtocolAction;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import model.Model;
import model.RegisteredUser;
import model.Connection;
import xmpp.rules.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 */
public class MessageActions extends AuthenticatedXMPPAction {


    public MessageActions(Model model, Connection connection, RegisteredUser registeredUser) {
        super(model, connection, registeredUser);
    }

    /**Actie die uitgevoerd wordt wanneer een client een bericht verstuurd
     *
     * @return
     */
    XMLProtocolAction sendMessage() {
        XMLProtocol protocol = Message.messageProtocol();

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {

                String to = element.getAttributeAt(STANZA_INDEX_TO).getValue();


                if(!model.hasUserWithEmail(to)){
                    connection.writeResponse(xmpp.rules.Error.userNotFound());
                    return false;
                }
                model.writeTo(to, element);
                return true;
            }
        };

    }

    @Override
    protected List<XMLProtocolAction> getActionsAsList() {

        List<XMLProtocolAction> actions = new ArrayList<>();
        actions.add(sendMessage());
        return actions;
    }


}
