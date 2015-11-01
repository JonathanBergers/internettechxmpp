package server;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import generic.xml.XMLProtocol;
import interfaces.Writable;
import generic.xml.XMLAttribute;
import generic.xml.XMLElement;
import model.User;
import org.xml.sax.InputSource;
import xmpp.ProtocolFactory;

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
public class Connection implements Runnable{



    XMLStreamReader xmlStreamReader;
    XMLStreamWriter xmlStreamWriter;


    ThreadPool threadPool;

    public Connection(ThreadPool threadPool){

        this.threadPool = threadPool;

    }

    private Socket socket;
    private User user;


    public boolean hasUser(User u) throws Exception{

        if(user == null) throw new Exception("geen stream gestart, geen valide stream message ontvangen, sluit thread");
        return user.equals(u);
    }

    private ArrayList<Writable> reads = new ArrayList<>(), writes = new ArrayList<>();


    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {





        try {
            xmlStreamReader = XMLStreamReaderFactory.create(new InputSource(socket.getInputStream()), false);
            xmlStreamWriter = XMLStreamWriterFactory.create(socket.getOutputStream());
            try {


                XMLElement element = readStream(xmlStreamReader);

                System.out.println(element);
                XMLProtocol openStreamProtocol = ProtocolFactory.streamProtocol(true);


                // als het eerste bericht geen stream open bericht is dan returnt deze
                if(!openStreamProtocol.checkRecursive(element, openStreamProtocol)){
                    return;
                }else{
                    String email = element.getAttributeAt(1).getValue();

                    threadPool.addActiveUser(new User(email));

                }
                //TODO read stream message

                xmlStreamReader.next();

                while(xmlStreamReader.hasNext()){


                        XMLElement el =
                        readStream(xmlStreamReader);

                        System.out.println(el.toString());


//



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


    public boolean write(Writable writable){


        try {
            writable.write(xmlStreamWriter);
            return true;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean broadcastMessage(XMLElement element, User u){

        return threadPool.sendMessage(element, u);
    }



}
