package model.protocol.protocolrule;

import model.XMPPMessage;
import model.protocol.StanzaMessage;
import model.xml.XMLAttribute;
import model.xml.XMLElement;

import java.util.List;

/**
 * Created by jonathan on 29-10-15.
 */
public class MessageProtocol implements Protocol<XMPPMessage> {


    private XMLProtocol xmlProtocol;


    public MessageProtocol() {

        xmlProtocol = new XMLProtocol(null);

        xmlProtocol.addAttributeProtocol(new Protocol<XMLAttribute>() {
            @Override
            public boolean conforms(XMLAttribute subject) {
                return subject.hasName("to");
            }
        });


    }


    @Override
    public boolean conforms(XMPPMessage subject) {
        return subject.check()
    }
}
