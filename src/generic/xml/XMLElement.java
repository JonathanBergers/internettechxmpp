package generic.xml;

import generic.Element;
import generic.NestedElement;
import interfaces.Nested;
import interfaces.Writable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 27-10-15.
 */
public class XMLElement extends NestedElement<XMLElement> implements Writable{



    public static void main(String[] args) {

        XMLElement e = new XMLElement(null, "StanzaMessage", "dit is een bericht");
        e.addAttribute(new XMLAttribute("id", "100"));
        e.addElement("body", "jooo");
        System.out.println(e.toString());

    }


    private LinkedList<XMLAttribute> attributes = new LinkedList<>();




    public XMLElement(final String name, final String value) {
        super(null, name, value);
    }
    public XMLElement(final String name) {
        super(null, name);
    }

    public XMLElement(XMLElement parent, final String name){
        super(parent, name);
    }
    public XMLElement(XMLElement parent, final String name, final String value){
        super(parent, name, value);
    }






    public boolean withText(){
        return value !=null;
    }


    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {


        writer.writeStartElement(name);


        for(XMLAttribute a: attributes) a.write(writer);
        if(value != null){
            writer.writeCharacters(value);
        }

        for(XMLElement e: getChildren()){

            e.write(writer);

        }

        writer.writeEndElement();


    }

    public XMLElement addElement(final String name, final String text){
        XMLElement element = new XMLElement(this, name, text);
        addChild(element);
        return element;
    }
    public XMLElement addElement(final String name){
        XMLElement element = new XMLElement(this, name);
        addChild(element);
        return element;
    }
    public XMLElement addElement(XMLElement element){
        element.setParent(this);
        addChild(element);
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

        if(value != null){
            toString += value;
        }

        for(XMLElement e: getChildren()){
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

        if(value != null){
            toString += value;
        }
        if(getChildren().isEmpty()){
            toString += "</" + name + ">";
            return toString;
        }
        for(XMLElement e: getChildren()){
            toString += "\n" + e.createToString(toString, tabs + 1) + "\n";
        }

        toString += space + "</" + name + ">";

        return toString;
    }



    public void setValue(String value) {
        this.value = value;
    }


    public XMLAttribute getAttributeAt(int index){
        return attributes.get(index);
    }

    public LinkedList<XMLAttribute> getAttributes() {
        return attributes;
    }


}
