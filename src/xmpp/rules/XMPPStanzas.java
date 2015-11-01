package xmpp.rules;

import generic.xml.XMLElement;

/**
 * Created by jonathan on 1-11-15.
 */
public interface XMPPStanzas {

    String NAME_MESSAGE = "message";
    String NAME_QUERY = "q";
    String NAME_STREAM = "stream";
    String NAME_COMMAND = "iq";
    String NAME_ERROR = "error";


    static XMLElement createRootStanzaElement(final String rootName, final String to, final String  from, final String type, final String id){
        XMLElement element = new XMLElement(rootName);
        element.addAttribute("to", to).addAttribute("from" , from).addAttribute("type", type).addAttribute("id", id);
        return element;
    }
}
