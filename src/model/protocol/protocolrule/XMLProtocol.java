package model.protocol.protocolrule;

import model.xml.XMLAttribute;
import model.xml.XMLElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 29-10-15.
 */
public class XMLProtocol extends NestedProtocol<XMLProtocol>{


    protected List<Protocol<XMLAttribute>> attributes;


    public void addAttributeProtocol(Protocol<XMLAttribute> attributeProtocol){
        attributes.add(attributeProtocol);
    }

    public XMLProtocol(Protocol<XMLProtocol> parentProtocol) {
        super(parentProtocol);
    }


    @Override
    public boolean conforms(XMLProtocol subject) {
        return false;
    }
}
