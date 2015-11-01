package generic.action;

import generic.Element;
import generic.protocol.Protocol;

/**
 * Created by jonathan on 30-10-15.
 *
 * when a element conforms a protocol , describe the action to do
 *
 * hiermee kunnen actiess gedefineerd worden die uitgevoerd moeten worden wanneer een element voldoet aan een bepaald protocol.
 * bijvoorbeeld wanneer een bericht ingelezen is, en deze voldoet aan het message protocol, dan moet deze verstuurd worden naar de gebruiker.
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
