package xmpp.actions;

import generic.xml.XMLProtocolAction;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import model.Model;
import model.RegisteredUser;
import model.User;
import model.Connection;
import xmpp.rules.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 */
public class ContactActions extends AuthenticatedXMPPAction {


    public ContactActions(Model model, Connection connection, RegisteredUser registeredUser) {
        super(model, connection, registeredUser);
    }

    /**Actie die uitgevoerd wordt wanneer een client een contact wilt toevoegen
     *
     * stuur bericht naar client waaraan bericht is verstuurd.
     * als deze client niet bestaat dan return error message
     * anders return ok response
     *
     * @return
     */
    XMLProtocolAction requestAddContact() {
        XMLProtocol protocol = Contacts.requestAddContact();


        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {


                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();

                XMLElement queryElement = element.getChildAt(0);
                String email = queryElement.getChildAt(0).getValue();

                boolean b = model.hasUserWithEmail(from);

                if(!b){
                    connection.writeResponse(Contacts.serverResponseAddContact(from, model.getServerSettings(), xmpp.rules.Error.userNotFound(), queryElement));
                }else{

                    connection.writeResponse(Contacts.serverResponseAddContact(from, model.getServerSettings()));
                }
                return b;
            }
        };

    }


    /**wanneer een client een contactverzoek accepteerd
     *
     * voeg contact toe aan gebruiker.
     * verstuur bericht aan toegevoegde gebruiker
     *
     * @return
     */
    XMLProtocolAction addContact() {
        XMLProtocol protocol = Contacts.clientResponseAddContact();

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {

                String to = element.getAttributeAt(STANZA_INDEX_TO).getValue();
                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();


                String response = element.getChildAt(0).getValue();

                if(response.equals("accept")){
                   return model.addContact(to, from);
                }

                return true;




            }
        };

    }

    /** wanneer client contac lijst opvraagt
     *
     * @return
     */
    XMLProtocolAction contactList() {

        XMLProtocol protocol = Contacts.requestContactList(model.getServerSettings());

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {
                List<User> contacts = model.getContactList(user);


                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();

                assert user.getEmail().equals(from): "action can onky be used if user is authenticated";
                if(!user.getEmail().equals(from)) return false;

                connection.writeResponse(Contacts.responseContacts(from, model.getServerSettings(), contacts));

                return true;
            }
        };

    }

    @Override
    protected List<XMLProtocolAction> getActionsAsList() {

        List<XMLProtocolAction> actions = new ArrayList<>();
        actions.add(addContact());
        actions.add(contactList());
        return actions;
    }
}
