package model.protocol;

import model.interfaces.Writable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 27-10-15.
 */
public class XMPPElement implements Writable, XMPPObject{



    public static void main(String[] args) {

        XMPPElement e = new XMPPElement(null, "StanzaMessage", "dit is een bericht");
        e.addAttribute(new XMPPAttribute("id", "100"));
        e.addElement("body", "jooo");
        System.out.println(e.toString());

    }

    private XMPPElement parent;
    private final String name;
    private String text;

    private LinkedList<XMPPElement> childElements = new LinkedList<>();

    private LinkedList<XMPPAttribute> attributes = new LinkedList<>();


    public XMPPElement(XMPPElement parent, String name) {
        this.parent = parent;
        this.name = name;
        this.text = null;
    }
    public XMPPElement(XMPPElement parent, String name, String text) {
        this.parent = parent;
        this.name = name;
        this.text = text;
    }

    public XMPPElement(String name, String text) {
        this.parent = null;
        this.name = name;
        this.text = text;
    }
    public XMPPElement(String name) {
        this.parent = null;
        this.name = name;
        this.text = null;
    }




    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {


        writer.writeStartElement(name);


        for(XMPPAttribute a: attributes) a.write(writer);
        if(text != null){
            writer.writeCharacters(text);
        }

        for(XMPPElement e: childElements){

            e.write(writer);

        }

        writer.writeEndElement();


    }

    public XMPPElement addElement(final String name, final String text){
        XMPPElement element = new XMPPElement(this, name, text);
        childElements.addLast(element);
        return element;
    }
    public XMPPElement addElement(final String name){
        XMPPElement element = new XMPPElement(this, name);
        childElements.addLast(element);
        return element;
    }

    public XMPPElement addAttribute(XMPPAttribute attribute){
        attributes.addLast(attribute);
        return this;
    }
    public XMPPElement addAttribute(String name, String value){
        attributes.addLast(new XMPPAttribute(name, value));
        return this;
    }

    public XMPPElement addElement(XMPPElement element){
        element.setParent(this);
        childElements.addLast(element);
        return element;
    }

    public XMPPElement addAttributes(List<XMPPAttribute> attributes){
        this.attributes.addAll(attributes);
        return this;
    }


    @Override
    public String toString() {

        String toString  = "<" + name;

        for(XMPPAttribute a: attributes){
            toString += " "+  a.toString();
        }

        toString += ">";

        if(text != null){
            toString += text;
        }

        for(XMPPElement e: childElements){
            toString += "\n" + "\t" +  e.toString() + "\n";
        }

        toString += "</" + name + ">";
        return createToString("", 0);
    }

    public String createToString(String s, int tabs){


        String space = "";
        for (int i = 0; i < tabs; i++) {

            space +="\t";
        }
        String toString  = space + "<" + name;

        for(XMPPAttribute a: attributes){
            toString += " "+  a.toString();
        }

        toString += ">";

        if(text != null){
            toString += text;
        }
        if(childElements.isEmpty()){
            toString += "</" + name + ">";
            return toString;
        }
        for(XMPPElement e: childElements){
            toString += "\n" + e.createToString(toString, tabs + 1) + "\n";
        }

        toString += space + "</" + name + ">";

        return toString;
    }




    public void setParent(XMPPElement parent) {
        this.parent = parent;
    }

    public boolean hasName(final String name){
        return this.name.equals(name);
    }

    public void setText(String text) {
        this.text = text;
    }


    public XMPPElement getElementAt(int index){

        return childElements.get(index);

    }

    public XMPPAttribute getAttributeAt(int index){


        return attributes.get(index);
    }

    @Override
    public String getDisplayMessage() {

        String display = "Element: name: " + name;
        if(text == null){
            return display + "without text";
        }
        return display+" , text: "+ text;
    }

    @Override
    public boolean hasValue() {

        if(text != null){

            if(!text.isEmpty()){
                return true;
            }
        }

        return false;

    }


    public boolean hasAmountChildren(int amount){
        return childElements.size() == amount;
    }

    public String getText() {
        return text;
    }
}
