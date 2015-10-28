package server;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import model.User;
import model.interfaces.Writable;
import model.protocol.*;
import org.xml.sax.InputSource;

import javax.lang.model.element.Element;
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


                        XMPPElement el =
                        readStream(xmlStreamReader);

                        System.out.println(el.toString());
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



    private XMPPElement readStream(XMLStreamReader streamReader) throws XMLStreamException {

        XMPPElement el = null;
        try {
            streamReader.next();
            if (streamReader.isStartElement()) {

                // recursion
                el = new XMPPElement(streamReader.getLocalName());
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
    private XMPPElement readElement(XMLStreamReader streamReader, XMPPElement element) throws XMLStreamException {

        while (streamReader.hasNext()) {
            try {

                streamReader.next();



                if (streamReader.isStartElement()) {



                    // recursion
                    XMPPElement el = new XMPPElement(element, streamReader.getLocalName());
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






    private List<XMPPAttribute> checkAttributes(XMLStreamReader reader){


        List<XMPPAttribute> attributes = new ArrayList<>();
        int attrCount = reader.getAttributeCount();

            System.out.println("Attribute count : "+ attrCount);

            if (attrCount > 0) {

                // voeg attributen toe
                for (int i = 0; i < attrCount; i++) {

                    String attrName = reader.getAttributeName(i).getLocalPart();
                    String attrValue = reader.getAttributeValue(i);

                    XMPPAttribute attribute = new XMPPAttribute(attrName, attrValue);
                    System.out.println("Attribute: " + attrName + " " + attrValue);
                    System.out.println(attribute.toString());

                    attributes.add(attribute);



                }

            }
        return attributes;

    }



}
