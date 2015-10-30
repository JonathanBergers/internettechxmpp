package server;

import generic.Element;
import generic.protocol.Protocol;
import generic.protocol.XMLProtocol;
import generic.xml.XMLElement;

/**
 * Created by jonathan on 30-10-15.
 *
 * when a element conforms a protocol , describe the action to do
 *
 */
public abstract class ProtocolAction<T extends Element> {


    private final Protocol protocol;

    protected ProtocolAction(Protocol protocol) {
        this.protocol = protocol;
    }

    public boolean handle(T element){

        if(protocol.conforms(element)){
            return onHandle(element);
        }
        return false;
    }


    public abstract boolean onHandle(T element);






}
