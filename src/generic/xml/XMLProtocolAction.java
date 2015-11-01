package generic.xml;

import generic.protocol.ProtocolAction;

/**
 * Created by jonathan on 30-10-15.
 *
 * when a element conforms a protocol , describe the action to do
 *
 * hiermee kunnen actiess gedefineerd worden die uitgevoerd moeten worden wanneer een element voldoet aan een bepaald protocol.
 * bijvoorbeeld wanneer een bericht ingelezen is, en deze voldoet aan het message protocol, dan moet deze verstuurd worden naar de gebruiker.
 *
 */
public abstract class XMLProtocolAction extends ProtocolAction<XMLElement, XMLProtocol<XMLElement>> {


    protected XMLProtocolAction(XMLProtocol protocol) {
        super(protocol);
    }

    @Override
    public boolean handle(XMLElement element) {

        if(protocol.checkRecursive(element, protocol)) return onHandle(element);
        return false;
    }



}
