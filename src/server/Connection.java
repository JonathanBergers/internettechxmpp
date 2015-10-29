package server;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import model.StanzaBuildException;
import model.StanzaFactory;
import model.XMPPMessage;
import model.interfaces.Writable;
import model.xml.XMLAttribute;
import model.xml.XMLElement;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 12-10-15.
 */
public class Connection implements Runnable{



    private Socket socket;


    private ArrayList<Writable> reads = new ArrayList<>(), writes = new ArrayList<>();


    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {





        try {
            XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.create(new InputSource(socket.getInputStream()), false);
            try {
                while(xmlStreamReader.hasNext()){


                        XMLElement el =
                        readStream(xmlStreamReader);

                        System.out.println(el.toString());

                    try {
                        XMPPMessage message = StanzaFactory.buildMessage(el);
                        System.out.println("RECIEVED MESSAGE : "+ message.toString());


                    } catch (StanzaBuildException e) {
                        e.printStackTrace();
                    }
                    System.out.println("WELL");

//
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

                System.out.println("ELEMENT READ" + el.toString());
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
                    element.setText(streamReader.getText());
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






    private List<XMLAttribute> checkAttributes(XMLStreamReader reader){


        List<XMLAttribute> attributes = new ArrayList<>();
        int attrCount = reader.getAttributeCount();

            System.out.println("Attribute count : "+ attrCount);

            if (attrCount > 0) {

                // voeg attributen toe
                for (int i = 0; i < attrCount; i++) {

                    String attrName = reader.getAttributeName(i).getLocalPart();
                    String attrValue = reader.getAttributeValue(i);

                    XMLAttribute attribute = new XMLAttribute(attrName, attrValue);
                    System.out.println("Attribute: " + attrName + " " + attrValue);
                    System.out.println(attribute.toString());

                    attributes.add(attribute);



                }

            }
        return attributes;

    }



}
