package model.xml;

import model.interfaces.Writable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 27-10-15.
 */
public class XMLElement implements Writable, XMLObject {



    public static void main(String[] args) {

        XMLElement e = new XMLElement(null, "StanzaMessage", "dit is een bericht");
        e.addAttribute(new XMLAttribute("id", "100"));
        e.addElement("body", "jooo");
        System.out.println(e.toString());

    }

    private XMLElement parent;
    private final String name;
    private String text;

    private LinkedList<XMLElement> childElements = new LinkedList<>();

    private LinkedList<XMLAttribute> attributes = new LinkedList<>();


    public XMLElement(XMLElement parent, String name) {
        this.parent = parent;
        this.name = name;
        this.text = null;
    }
    public XMLElement(XMLElement parent, String name, String text) {
        this.parent = parent;
        this.name = name;
        this.text = text;
    }

    public XMLElement(String name, String text) {
        this.parent = null;
        this.name = name;
        this.text = text;
    }
    public XMLElement(String name) {
        this.parent = null;
        this.name = name;
        this.text = null;
    }




    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {


        writer.writeStartElement(name);


        for(XMLAttribute a: attributes) a.write(writer);
        if(text != null){
            writer.writeCharacters(text);
        }

        for(XMLElement e: childElements){

            e.write(writer);

        }

        writer.writeEndElement();


    }

    public XMLElement addElement(final String name, final String text){
        XMLElement element = new XMLElement(this, name, text);
        childElements.addLast(element);
        return element;
    }
    public XMLElement addElement(final String name){
        XMLElement element = new XMLElement(this, name);
        childElements.addLast(element);
        return element;
    }

    public XMLElement addAttribute(XMLAttribute attribute){
        attributes.addLast(attribute);
        return this;
    }
    public XMLElement addAttribute(String name, String value){
        attributes.addLast(new XMLAttribute(name, value));
        return this;
    }

    public XMLElement addElement(XMLElement element){
        element.setParent(this);
        childElements.addLast(element);
        return element;
    }

    public XMLElement addAttributes(List<XMLAttribute> attributes){
        this.attributes.addAll(attributes);
        return this;
    }


    @Override
    public String toString() {

        String toString  = "<" + name;

        for(XMLAttribute a: attributes){
            toString += " "+  a.toString();
        }

        toString += ">";

        if(text != null){
            toString += text;
        }

        for(XMLElement e: childElements){
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

        for(XMLAttribute a: attributes){
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
        for(XMLElement e: childElements){
            toString += "\n" + e.createToString(toString, tabs + 1) + "\n";
        }

        toString += space + "</" + name + ">";

        return toString;
    }




    public void setParent(XMLElement parent) {
        this.parent = parent;
    }

    public boolean hasName(final String name){
        return this.name.equals(name);
    }

    public void setText(String text) {
        this.text = text;
    }


    public XMLElement getElementAt(int index){

        return childElements.get(index);

    }

    public XMLAttribute getAttributeAt(int index){


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
