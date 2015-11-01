package xmpp.actions;

import generic.xml.XMLProtocolAction;
import generic.xml.XMLElement;
import model.Model;
import model.Connection;

import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 */
public abstract class XMPPAction {

    protected Model model;
    protected Connection connection;
    protected final int STANZA_INDEX_TO = 0;
    protected final int STANZA_INDEX_FROM = 1;


    public XMPPAction(Model model, Connection connection) {
        this.model = model;
        this.connection = connection;
    }

    protected abstract List<XMLProtocolAction> getActionsAsList();

    public void handle(XMLElement element){


        for(XMLProtocolAction p: getActionsAsList()){
            p.handle(element);
        }
    }


}
