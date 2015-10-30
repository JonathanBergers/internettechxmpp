package server;

import generic.Element;
import generic.xml.XMLElement;
import model.User;
import xmpp.ProtocolFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 30-10-15.
 * klasse die alle regels voor de applicatie bevat.
 * verteld wat er moet gebeuren bij welk bericht
 */
public class XMPPActions {


    private List<ProtocolAction> actions = new ArrayList<>();
    private final Connection connection;

    public XMPPActions(Connection connection) {
        this.connection = connection;
        addXMPPactions();

    }




    private void addXMPPactions(){



        //wanneer een persoon een bericht stuurt aan iemand
        // stuur deze door naar de persoon

        actions.add(new ProtocolAction<XMLElement>(ProtocolFactory.messageProtocol()) {

            @Override
            public boolean onHandle(XMLElement element) {

                User u = new User(element.getAttributeAt(1).getValue());
                return connection.broadcastMessage(element, u);

            }
        });








    }




}
