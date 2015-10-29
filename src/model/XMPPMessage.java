package model;

import model.protocol.StanzaMessage;
import model.protocol.XMPPElement;
import model.protocol.XMPPObject;

/**
 * Created by jonathan on 29-10-15.
 */
public class XMPPMessage implements StanzaMessage {

    // to from type

    private final XMPPElement wrappedElement;


    public XMPPMessage(XMPPElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    @Override
    public String subject() {
        return wrappedElement.getElementAt(0).getText();
    }

    @Override
    public String body() {
        return wrappedElement.getElementAt(1).getText();
    }

    @Override
    public String id() {
        return wrappedElement.getAttributeAt(3).getValue();
    }

    @Override
    public User from() {
        User u = new User(wrappedElement.getAttributeAt(1).getValue());
        return u;
    }

    @Override
    public User to() {
        User u = new User(wrappedElement.getAttributeAt(0).getValue());
        return u;
    }

    @Override
    public String type() {
        return wrappedElement.getAttributeAt(2).getValue();
    }


    @Override
    public String toString() {
        return wrappedElement.toString();
    }
}
