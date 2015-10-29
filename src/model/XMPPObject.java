package model;

import model.protocol.Stanza;
import model.xml.XMLElement;

/**
 * Created by jonathan on 29-10-15.
 */
public abstract class XMPPObject implements Stanza {


    protected final XMLElement wrappedElement;


    public XMPPObject(XMLElement wrappedElement) {
        this.wrappedElement = wrappedElement;
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
