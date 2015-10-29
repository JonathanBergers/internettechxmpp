package model.protocol;

import model.interfaces.Writable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by jonathan on 27-10-15.
 */
public class XMPPAttribute implements Writable , XMPPObject{

    private final String name, value;

    public XMPPAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {


        writer.writeAttribute(name, value);
    }

    @Override
    public String toString() {
        return name + "=\"" + value + "\"";
    }

    @Override
    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    @Override
    public String getDisplayMessage() {
        return "Attribute: name: " + name + " , value: "+ value;
    }

    @Override
    public boolean hasValue() {

        if(value!= null){

            if(!value.isEmpty()){

                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }
}
