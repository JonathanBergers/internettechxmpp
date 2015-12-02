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
 *
 * de acties die uitgevoerd moeten bij het inlog process
 *
 *
 */
public class AuthenticationActions  extends XMPPAction {


    public AuthenticationActions(Model model, Connection connection) {
        super(model, connection);
    }

    /**De actie die uitgevoerd moet worden wanneer client om login velden vraagt
     *
     * @return
     */
    XMLProtocolAction requestLoginFields(){
        XMLProtocol protocol = Authentication.requestLoginFields(model.getServerSettings());

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {
                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();
                if(from == null){
                    return false;

                }else if(from.isEmpty()){
                    return false;
                }
                connection.writeResponse(Authentication.responseLoginFields(from, model.getServerSettings()));
                return true;

            }
        };
    }

    /**actie wanneer client inlogt met gegevens
     *
     * @return
     */
    XMLProtocolAction requestAuthentication(){

        XMLProtocol protocol = Authentication.requestAuthentication(model.getServerSettings());

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {

                String from = element.getAttributeAt(STANZA_INDEX_FROM).getValue();

                XMLElement queryElement = element.getChildAt(0);


                String email = queryElement.getChildAt(0).getValue();
                String password = queryElement.getChildAt(1).getValue();

                RegisteredUser user  = model.authenticateUser(email, password);



                if(user == null){
                    connection.writeResponse(Authentication.responseAuthentication(model.getServerSettings(), from, xmpp.rules.Error.invalidCredentials(), queryElement));
                    return false;
                }


                System.out.println("USER "+ user.toString() + "authenticated" );
                connection.setActiveUser(user);
                connection.writeResponse(Authentication.responseAuthentication(model.getServerSettings(), from));
                return true;





            }
        };



    }


    /**De actie die uitgevoerd STREAM
     *
     * @return
     */
    XMLProtocolAction requestStream(){
        XMLProtocol protocol = Stream.stream(true);

        return new XMLProtocolAction(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {


                connection.writeResponse(Stream.stream(true, element.getAttributeAt(1).getValue(), "jonathan@" + element.getAttributeAt(1).getValue() ));
                return true;

            }
        };
    }


    @Override
    protected List<XMLProtocolAction> getActionsAsList() {

        List<XMLProtocolAction> actions = new ArrayList<>();
        actions.add(requestLoginFields());
        actions.add(requestAuthentication());
        actions.add(requestStream());
        return actions;

    }
}
