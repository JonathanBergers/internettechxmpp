package xmpp.actions;

import model.Model;
import model.RegisteredUser;
import model.Connection;

/**
 * Created by jonathan on 1-11-15.
 */
public abstract class AuthenticatedXMPPAction extends XMPPAction {

    protected RegisteredUser user;


    public AuthenticatedXMPPAction(Model model, Connection connection, RegisteredUser registeredUser) {
        super(model, connection);
        this.user = registeredUser;
    }
}
