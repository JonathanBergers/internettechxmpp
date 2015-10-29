package model;

import model.protocol.StanzaMessage;
import model.xml.XMLElement;

/**
 * Created by jonathan on 29-10-15.
 */
public class XMPPMessage extends XMPPObject implements StanzaMessage {


    public XMPPMessage(XMLElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public String subject() {
        return wrappedElement.getElementAt(0).getText();
    }

    @Override
    public String body() {
        return wrappedElement.getElementAt(1).getText();
    }


}
