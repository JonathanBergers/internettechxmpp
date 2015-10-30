package xmpp;

import generic.protocol.XMLProtocol;
import generic.xml.XMLAttribute;
import generic.xml.XMLElement;

/**
 * Created by jonathan on 30-10-15.
 */
public class ProtocolFactory {



    public static String  SERVER_NAME = "server@server.com";

    public static void main(String[] args) {

        XMLElement message = StanzaFactory.Server.buildMessage("falco", "jona", StanzaType.Message.CHAT, "idididi", "subjeeect", "bodeeyy");
        System.out.println(message.toString());

        XMLProtocol messageProtocol = ProtocolFactory.messageProtocol();

        boolean b = messageProtocol.checkRecursive(message, messageProtocol);

        System.out.println("RESULT: " + b);


    }


    public static XMLProtocol messageProtocol(){


        XMLProtocol<XMLElement> root = elementHasName("message");

        root.addAttributeProtocol(attributeHasName("to"));
        root.addAttributeProtocol(attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(attributeHasNameValue("type", StanzaType.Message.getAsList()));
        root.addAttributeProtocol(attributeHasName("id"));


        // moet een subject en een body hebben
        root.addChildProtocol(elementHasName("subject"));
        root.addChildProtocol(elementHasName("body"));

        return root;


    }



    public static XMLProtocol streamProtocol(boolean open){


        XMLProtocol<XMLElement> root = elementHasNameWithText("stream", false);

        root.addAttributeProtocol(attributeHasName("to"));
        root.addAttributeProtocol(attributeHasName("from"));
        // type moet een van de values van message type bevatten
        if(open)root.addAttributeProtocol(ProtocolFactory.attributeHasNameValue("type", StanzaType.Stream.OPEN)); else root.addAttributeProtocol(ProtocolFactory.attributeHasNameValue("type", StanzaType.Stream.CLOSE));
        root.addAttributeProtocol(attributeHasName("id"));


        return root;


    }


    public static XMLProtocol queryGetContacts() {


        XMLProtocol<XMLElement> root = elementHasName("message");


        // query voor inlog request
        // SERVER : query voor inlog answer, met username en pass veld
        // query query voor inlog request met username en pass
        // SERVER query antwoord voor inlog request

        return null;
    }


    /**eerste vraag om authenticatie
     *
     * @return
     */
    public static XMLProtocol requestAuth1() {


        XMLProtocol<XMLElement> root = elementHasNameWithText("iq", false);
        root.addAttributeProtocol(attributeHasNameValue("to", SERVER_NAME));
        root.addAttributeProtocol(attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(ProtocolFactory.attributeHasNameValue("type", StanzaType.Query.GET));
        root.addAttributeProtocol(attributeHasNameValue("id", StanzaIDs.AUTH_1));



        return null;
    }

    /**tweede vraag om authenticatie
     *
     * @return
     */
    public static XMLProtocol requestAuth2() {


        XMLProtocol<XMLElement> root = elementHasNameWithText("iq", false);
        root.addAttributeProtocol(attributeHasNameValue("to", SERVER_NAME));
        root.addAttributeProtocol(attributeHasName("from"));
        // type moet een van de values van message type bevatten
        root.addAttributeProtocol(ProtocolFactory.attributeHasNameValue("type", StanzaType.Query.SET));
        root.addAttributeProtocol(attributeHasNameValue("id", StanzaIDs.AUTH_2));
        // moet ingevulde username hebben
        root.addChildProtocol(elementHasNameWithText("username", true));
        root.addChildProtocol(elementHasNameWithText("password", true));


        return root;
    }



    // element protocols for xmpp
    private static XMLProtocol<XMLElement> elementHasName(final String name){
        return new XMLProtocol<XMLElement>() {
            @Override
            public boolean conforms(XMLElement input) {
                return input.hasName(name);
            }
        };
    }
    // element protocols for xmpp
    private static XMLProtocol<XMLElement> elementHasNameWithText(final String name, boolean b){
        return new XMLProtocol<XMLElement>() {
            @Override
            public boolean conforms(XMLElement input) {

                System.out.println("INPUT" + input.toString());
                return input.withText() == b && input.hasName(name);
            }
        };
    }




    // attribute protocols for XMPP
    private static XMLProtocol<XMLAttribute> attributeHasName(final String name){
        return new XMLProtocol<XMLAttribute>() {
            @Override
            public boolean conforms(XMLAttribute input) {
                return input.hasName(name);
            }
        };
    }
    private static XMLProtocol<XMLAttribute> attributeHasNameValue(final String name, final String value){
        return new XMLProtocol<XMLAttribute>() {
            @Override
            public boolean conforms(XMLAttribute input) {
                return input.hasName(name) && input.hasValue(value);
            }
        };
    }
    private static XMLProtocol<XMLAttribute> attributeHasNameValue(final String name, final String[] values){
        return new XMLProtocol<XMLAttribute>() {
            @Override
            public boolean conforms(XMLAttribute input) {
                if(!input.hasName(name)) return false;
                for(String s: values){
                    if(input.hasValue(s)) return true;
                }
                return false;
            }
        };
    }


}
