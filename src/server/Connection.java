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





                    if(xmlStreamReader.isStartElement()) {


                        XMPPElement el =
                        readElement(xmlStreamReader, new XMPPElement("ROOT"));

                        System.out.println(el.toString());
                    }

                    xmlStreamReader.next();




//                        if(xmlStreamReader.hasName()){
//
//                            String name = xmlStreamReader.getName().toString();
//                            if(name.equals("iq")){
//                                System.out.println("RECIEVED A COMMAND");
//                                Command command = readCommand(xmlStreamReader);
//
//                                //TODO doe iets met het command
//                            }
//
//                            if(name.equals(Message.START_NAME)){
//                                System.out.println("RECIEVED A MESSAGE");
//                                Message message = readMessage(xmlStreamReader);
//
//                                //TODO doe iets met de message
//                            }
//
//                            xmlStreamReader.next();
//                            continue;
//                        }
//
//                    }
//
//                    //voor debuggen
//                    if(xmlStreamReader.hasName()){
////                        System.out.println(xmlStreamReader.getName().toString());
//                        xmlStreamReader.next();
//                    }else{
//                        xmlStreamReader.next();
//                    }
//


                    }



            } catch (XMLStreamException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private XMPPElement readElement(XMLStreamReader xmlStreamReader, XMPPElement parentElement){



        try {

            //start element
            // get name
            String name = xmlStreamReader.getLocalName();
            System.out.println(name);

            XMPPElement element = new XMPPElement(name);

            // check of attributen
            int attrCount = xmlStreamReader.getAttributeCount();

            System.out.println(attrCount);

            if (attrCount > 0) {

                // voeg attributen toe
                for (int i = 0; i < attrCount; i++) {

                    String attrName = xmlStreamReader.getAttributeName(i).getLocalPart();
                    String attrValue = xmlStreamReader.getAttributeValue(i);

                    XMPPAttribute attribute = new XMPPAttribute(attrName, attrValue);
//                    System.out.println("Attribute: " + attrName + " " + attrValue);
//                    System.out.println(attribute.toString());

                    element.addAttribute(attribute);


                }
            }

            xmlStreamReader.next();

            // kijk of het element text heeft
            if (xmlStreamReader.hasText()) {

                String text = xmlStreamReader.getText();
                System.out.println("text: " + text);
                element.setText(text);

                // is het element klaar ? of bevat het meer elementen ?
                xmlStreamReader.next();
                if(xmlStreamReader.isEndElement()){
                    // element is afgerond
                    System.out.println("element is afgerond");
                    parentElement.addElement(element);
                    return element;
                }else if(xmlStreamReader.isStartElement()){
                    // element bevat meer elementen
                    return readElement(xmlStreamReader, element);

                }


            } else if (xmlStreamReader.isStartElement()) {
                return readElement(xmlStreamReader, element);
            }

        }catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return parentElement;

            //xmlStreamReader.next();




       // return null;

    }


    /**reads a command from the stream
     * starting on the start element
     *
     * @param reader
     * @return
     */
    private Command readCommand(XMLStreamReader reader){

        System.out.println("reading command");


        System.out.println("Command = ");

        return null;
    }

    private Message readMessage(XMLStreamReader reader){


        System.out.println("reading message");
        User from, to;
        String subject, body;
        String typeString;

        Message message = null;

        try {
            //start element

            to = new User(reader.getAttributeValue(0));
            from = new User(reader.getAttributeValue(1));
            typeString = reader.getAttributeValue(2);


            // attributes
//            System.out.println("to: " + to.toString());
//            System.out.println("from: " + from.toString());
//            System.out.println("type: " + typeString);
            reader.next();


            //subject
            subject = reader.getElementText();
//            System.out.println("SUBJECT: " + subject);

            reader.next();
            body = reader.getElementText();
//            System.out.println("body: "+ body);

            message = new Message(to, from, subject, body, MessageType.getTypeFromString(typeString) );



        } catch (XMLStreamException e) {
            e.printStackTrace();
        }



        if(message ==null){
            System.out.println("Message not read");
            return null;
        }
        System.out.println("Message = " + message.toString());
        return message;
    }

    private Query readQuery(XMLStreamReader reader){
        System.out.println("reading query");



        System.out.println("Query = ");



        return null;
    }


}
