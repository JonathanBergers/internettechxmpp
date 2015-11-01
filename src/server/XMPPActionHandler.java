package server;

import generic.xml.XMLElement;
import model.Connection;
import model.Model;
import model.RegisteredUser;
import xmpp.actions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 *
 *
 * deze klasse handeld alle inkomende berichten af
 *
 */
public class XMPPActionHandler {



    private final Model model;
    private final Connection connection;
    private RegisteredUser registeredUser;


    private List<AuthenticatedXMPPAction> authenticatedXMPPActions = new ArrayList<>();
    private List<XMPPAction> xmppActions = new ArrayList<>();



    public XMPPActionHandler(Model model, Connection connection) {
        this.model = model;
        this.connection = connection;

        // voeg acties voor model toe
        xmppActions.add(new RegistrationActions(model, connection));
        xmppActions.add(new AuthenticationActions(model, connection));


    }



    public void handleMessage(XMLElement element){
        for(XMPPAction action: xmppActions){

            action.handle(element);
        }

        if(registeredUser!= null){

            for(XMPPAction action: authenticatedXMPPActions){

                action.handle(element);
            }
        }
    }



    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;

        authenticatedXMPPActions.add(new ContactActions(model, connection,registeredUser));
        authenticatedXMPPActions.add(new MessageActions(model, connection, registeredUser));
    }





}
