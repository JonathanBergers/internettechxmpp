package model.protocol;

import model.interfaces.Writable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 27-10-15.
 */
public class XMPPElement implements Writable{


    public static void main(String[] args) {

        XMPPElement e = new XMPPElement("Message", "dit is een bericht").addAttribute(new XMPPAttribute("id", "100")).addElement(new XMPPElement("body", "jooo"));
        System.out.println(e.toString());

    }

    private final String name;
    private String value;

    private LinkedList<XMPPElement> childElements = new LinkedList<>();

    private LinkedList<XMPPAttribute> attributes = new LinkedList<>();


    public XMPPElement(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public XMPPElement(String name) {
        this.name = name;
        this.value = null;
    }

    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {


        writer.writeStartElement(name);
        if(value!= null){
            writer.writeCharacters(value);
        }

        for(XMPPAttribute a: attributes) a.write(writer);


        for(XMPPElement e: childElements){

            e.write(writer);

        }

        writer.writeEndElement();


    }

    public XMPPElement addElement(XMPPElement element){
        childElements.addLast(element);
        return this;
    }

    public XMPPElement addAttribute(XMPPAttribute attribute){
        attributes.addLast(attribute);
        return this;
    }


    @Override
    public String toString() {

        String toString  = "<" + name;

        for(XMPPAttribute a: attributes){
            toString += " "+  a.toString();
        }

        toString += ">";

        if(value!= null){
            toString += value;
        }

        for(XMPPElement e: childElements){
            toString += "\n" + "\t" +  e.toString() + "\n";
        }

        toString += "</" + name + ">";
        return toString;
    }


}
