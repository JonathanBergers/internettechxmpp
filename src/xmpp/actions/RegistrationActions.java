package xmpp.actions;

import generic.xml.XMLProtocolAction;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import model.Model;
import model.RegisteredUser;
import model.Connection;
import xmpp.rules.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 */
public class RegistrationActions extends XMPPAction {


    public RegistrationActions(Model model, Connection connection) {
        super(model, connection);
    }

    /**De actie die uitgevoerd moet worden wanneer client om login velden vraagt
     *
     * @return
     */
    XMLProtocolAction requestRegisterFields(){
        XMLProtocol protocol = Registration.requestRegistrationFields(model.getServerSettings());

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {
                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();

                connection.writeResponse(Registration.responseRegistrationFields(from, model.getServerSettings()));
                return true;

            }
        };
    }

    XMLProtocolAction requestRegistration(){

        XMLProtocol protocol = Registration.requestRegistration(model.getServerSettings());

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {
                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();

                XMLElement queryElement = element.getChildAt(0);


                String email = queryElement.getChildAt(0).getValue();
                String password = queryElement.getChildAt(1).getValue();

                RegisteredUser user = model.registerUser(email, password);
                if(user == null){
                    connection.writeResponse(Registration.responseRegistration(model.getServerSettings(), from, xmpp.rules.Error.alreadyRegistered(), queryElement));
                    return false;
                }

                System.out.println("handling registration");

                connection.setActiveUser(user);
                connection.writeResponse(Registration.responseRegistration(model.getServerSettings(), from));

                return true;


            }
        };



    }


    @Override
    protected List<XMLProtocolAction> getActionsAsList() {

        List<XMLProtocolAction> actions = new ArrayList<>();
        actions.add(requestRegisterFields());
        actions.add(requestRegistration());
        return actions;
    }
}
