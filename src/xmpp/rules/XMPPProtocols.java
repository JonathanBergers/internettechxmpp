package xmpp.rules;

import generic.xml.XMLProtocol;
import generic.xml.XMLAttribute;
import generic.xml.XMLElement;

/**
 * Created by jonathan on 1-11-15.
 */
public interface XMPPProtocols {


    // element protocols for xml
    public static XMLProtocol<XMLElement> elementHasName(final String name){
        return new XMLProtocol<XMLElement>() {
            @Override
            public boolean conforms(XMLElement input) {
                return input.hasName(name);
            }
        };
    }
    public static XMLProtocol<XMLElement> elementHasNameAndNoChildren(final String name){
        return new XMLProtocol<XMLElement>() {
            @Override
            public boolean conforms(XMLElement input) {

                return input.hasName(name) && input.getChildren().size() == 0;
            }
        };
    }
    // element protocols for xml
    static XMLProtocol<XMLElement> elementHasNameWithText(final String name, boolean b){
        return new XMLProtocol<XMLElement>() {
            @Override
            public boolean conforms(XMLElement input) {

                //System.out.println("INPUT" + input.toString());
                return input.withText() == b && input.hasName(name);
            }
        };
    }




    // attribute protocols for XML
    static XMLProtocol<XMLAttribute> attributeHasName(final String name){
        return new XMLProtocol<XMLAttribute>() {
            @Override
            public boolean conforms(XMLAttribute input) {
                return input.hasName(name);
            }
        };
    }
    static XMLProtocol<XMLAttribute> attributeHasNameValue(final String name, final String value){
        return new XMLProtocol<XMLAttribute>() {
            @Override
            public boolean conforms(XMLAttribute input) {
                return input.hasName(name) && input.hasValue(value);
            }
        };
    }
    static XMLProtocol<XMLAttribute> attributeHasNameValue(final String name, final String[] values){

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
