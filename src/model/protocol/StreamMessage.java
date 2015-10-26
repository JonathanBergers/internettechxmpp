package model.protocol;

import model.User;
import model.interfaces.Writable;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by jonathan on 26-10-15.
 */
public class StreamMessage implements Writable {


    public static final String START_NAME = "stream";
    private final User from, to;
    private final String id;
    private boolean open = true;


    public StreamMessage(User to, User from) {
        this.from = from;
        this.to = to;
        this.id = from.getEmail() + to.getEmail();
    }


    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {

        if (!open) {
            writer.writeEndElement();
            writer.writeEndDocument();
        } else {
            writer.writeStartDocument();
            writer.writeStartElement(START_NAME);
            writer.writeAttribute("from", from.getEmail());
            writer.writeAttribute("to", to.getEmail());




        }
        open =!open;
    }




}
