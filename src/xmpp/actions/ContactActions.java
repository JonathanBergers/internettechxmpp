package xmpp.actions;

import generic.action.ProtocolAction;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import xmpp.rules.Authentication;
import xmpp.rules.Contacts;

/**
 * Created by jonathan on 1-11-15.
 */
public class ContactActions {




    /**Actie die uitgevoerd wordt wanneer een client een contact wilt toevoegen
     *
     * @return
     */
    ProtocolAction<XMLElement> requestAddContact() {
        XMLProtocol protocol = Contacts.requestAddContact();

    }


    /**wanneer een client een contactverzoek accepteerd
     *
     * @return
     */
    ProtocolAction<XMLElement> addContact() {
        XMLProtocol protocol = Contacts.clientResponseAddContact();

    }

    /** wanneer client contac lijst opvraagt
     *
     * @return
     */
    ProtocolAction<XMLElement> contactList() {


    }

}
