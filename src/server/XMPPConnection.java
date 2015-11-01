package server;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import interfaces.Writable;
import generic.xml.XMLAttribute;
import generic.xml.XMLElement;
import model.Connection;
import model.RegisteredUser;
import model.User;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 12-10-15.
 */
public class XMPPConnection implements Runnable, Connection {


    XMLStreamReader xmlStreamReader;
    XMLStreamWriter xmlStreamWriter;
    final ThreadPool threadPool;
    XMPPActionHandler actionHandler;



    private final Socket socket;
    private User user;


    public XMPPConnection(ThreadPool threadPool, Socket socket) {
        this.threadPool = threadPool;
        this.socket = socket;
    }

    @Override
    public void run() {


        try {
            xmlStreamReader = XMLStreamReaderFactory.create(new InputSource(socket.getInputStream()), false);
            xmlStreamWriter = XMLStreamWriterFactory.create(socket.getOutputStream());
            actionHandler = new XMPPActionHandler(threadPool, this);
            try {



                while (xmlStreamReader.hasNext()) {


                    XMLElement el = readStream(xmlStreamReader);

                    if(el == null){
                        return;
                        //wanneer client klaar is met berichten sturen
                    }

                    System.out.println(el.toString());
                    actionHandler.handleMessage(el);

                    xmlStreamReader.next();




                }


            } catch (XMLStreamException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private XMLElement readStream(XMLStreamReader streamReader) throws XMLStreamException {

        XMLElement el = null;
        try {
            streamReader.next();
            if (streamReader.isStartElement()) {

                // recursion
                String localName = streamReader.getLocalName();
//                el = new XMLElement(streamReader.getLocalName());
                el = new XMLElement(localName);

                //System.out.println("ELEMENT READ" + el.toString());
                el.addAttributes(checkAttributes(streamReader));
                return readElement(streamReader, el);

            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return el;


    }


    /**
     * Reads xml from the stream and builds elements
     * recursive
     *
     * @param streamReader
     * @param element
     * @return
     */
    private XMLElement readElement(XMLStreamReader streamReader, XMLElement element) throws XMLStreamException {

        while (streamReader.hasNext()) {
            try {

                streamReader.next();


                if (streamReader.isStartElement()) {


                    // recursion
                    XMLElement el = new XMLElement(element, streamReader.getLocalName());
                    el.addAttributes(checkAttributes(streamReader));
                    element.addElement(readElement(streamReader, el));

                } else if (streamReader.isCharacters()) {
                    element.setValue(streamReader.getText());
                } else {
                    return element;
                }
                //streamReader.next();

            } catch (XMLStreamException e) {
                e.printStackTrace();
            }

        }

        return element;


    }


    private List<XMLAttribute> checkAttributes(XMLStreamReader reader) {


        List<XMLAttribute> attributes = new ArrayList<>();
        int attrCount = reader.getAttributeCount();

        //System.out.println("Attribute count : " + attrCount);

        if (attrCount > 0) {

            // voeg attributen toe
            for (int i = 0; i < attrCount; i++) {

                String attrName = reader.getAttributeName(i).getLocalPart();
                String attrValue = reader.getAttributeValue(i);

                XMLAttribute attribute = new XMLAttribute(attrName, attrValue);
                //System.out.println("Attribute: " + attrName + " " + attrValue);
               // System.out.println(attribute.toString());

                attributes.add(attribute);


            }

        }
        return attributes;

    }




    /**Wanneer een gebruiker geauthenticeerd of registered is dan wordt deze toegevoegd aan de connectie
     * Ook wordt de handler geinformeerd dat de gebruiker geauthenticeerd is
     *
     * @param user
     */
    @Override
    public void setActiveUser(RegisteredUser user) {
        this.user = user;
        threadPool.addActiveUser(this);
        actionHandler.setRegisteredUser(user);
    }

    public User getUser() {
        return user;
    }



    @Override
    public void writeResponse(Writable writable) {

        System.out.println("Writing: "+ writable.toString());

        try {
            writable.write(xmlStreamWriter);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
