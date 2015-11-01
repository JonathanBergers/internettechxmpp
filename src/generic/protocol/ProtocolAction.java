package generic.protocol;

import generic.Element;
import generic.protocol.Protocol;

/**
 * Created by jonathan on 1-11-15.
 *
 /**
 * Created by jonathan on 30-10-15.
 *
 * when a element conforms a protocol , describe the action to do
 *
 * hiermee kunnen actiess gedefineerd worden die uitgevoerd moeten worden wanneer een element voldoet aan een bepaald protocol.
 * bijvoorbeeld wanneer een bericht ingelezen is, en deze voldoet aan het message protocol, dan moet deze verstuurd worden naar de gebruiker.
 *
 */
public abstract class ProtocolAction<E extends Element, P extends Protocol<E>> {


    protected final P protocol;

    protected ProtocolAction(P protocol) {
        this.protocol = protocol;
    }

    public boolean handle(E element){

        if(protocol.conforms(element)){
            return onHandle(element);
        }
        return false;
    }


    public abstract boolean onHandle(E element);

}
