package model;

import model.xml.XMLElement;
import model.xml.XMLObject;

/**
 * Created by jonathan on 29-10-15.
 */
public class StanzaFactory {


    /**Builds a message read from stream
     *
     * @param element
     * @return
     * @throws StanzaBuildException
     */
    public static XMPPMessage buildMessage(XMLElement element) throws StanzaBuildException {
        // start element is message
        assertHasName("message", element);
        assertIsStanza(element);

        assertHasAmountChildren(2, element);

        assertHasNameAndValue("subject", element.getElementAt(0));
        assertHasName("body", element.getElementAt(1));


        return new XMPPMessage(element);


    }

    /**Builds a message to write to the stream
     *
     * @param to
     * @param from
     * @param type
     * @param id
     * @param subject
     * @param body
     * @return
     */
    public static XMLElement buildMessage(final String to, final String  from, final String type, final String id,
                                           final String subject, final String body){

        XMLElement element = createRootStanzaElement(to, from, type, id);
        element.addElement("subject", subject);
        element.addElement("body", body);

        return element;


    }

    private static XMLElement createRootStanzaElement(final String to, final String  from, final String type, final String id){
        return new XMLElement("message").addAttribute("to", to).addAttribute("from" , from).addAttribute("type", type).addAttribute("id", id);
    }
















    private static boolean assertHasName(String name, XMLObject object) throws StanzaBuildException {

        if(object== null){
            throw new StanzaBuildException("no object with name: " + name, object);
        }

        if(object.hasName(name)){
            return true;
        }
        throw new StanzaBuildException("object: " + object.getDisplayMessage() + " doesnt have name: "+ name + " but is : " + object.getDisplayMessage(), object);
    }


    private static boolean assertHasNameAndValue(String name, XMLObject object) throws StanzaBuildException {

        assertHasName(name, object);
        if(object.hasValue()){
            return true;
        }
        throw new StanzaBuildException("object : " + object.getDisplayMessage() + " doesnt have a value ", object);


    }

    /**checkt of het element een stanza is
     *
     * pre = moet root element zijn
     *
     * @param element
     * @return
     * @throws StanzaBuildException
     */
    private static boolean assertIsStanza(XMLElement element) throws StanzaBuildException{



        // elke stanza moet deze attributen hebben
        assertHasName("to", element.getAttributeAt(0));
        assertHasName("from", element.getAttributeAt(1));
        assertHasName("type", element.getAttributeAt(2));
        assertHasName("id", element.getAttributeAt(3));

        return true;

    }

    private static boolean assertHasAmountChildren(int amount, XMLElement object) throws StanzaBuildException {

        if(object.hasAmountChildren(amount )){
            return true;
        }
        throw new StanzaBuildException("object : " + object.getDisplayMessage() + " doesnt have " + amount + " children", object);


    }


}
