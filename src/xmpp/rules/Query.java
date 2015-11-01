package xmpp.rules;

import generic.xml.XMLElement;
import model.User;

import java.util.List;

/**
 * Created by jonathan on 1-11-15.
 * protocollen of elemenenten
 * de queries die ontvangen en verstuurd kunnen worden
 */
public interface Query {


    static XMLElement contactList(List<User> users){

        XMLElement queryElement = new XMLElement(XMPPStanzas.NAME_QUERY);
        for(User u : users){
            XMLElement userElement = queryElement.addElement("user");
            userElement.addElement("email", u.getEmail());

        }
        return queryElement;
    }



}
