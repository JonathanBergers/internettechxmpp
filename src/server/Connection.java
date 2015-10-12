package server;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import model.protocol.Command;
import model.protocol.Message;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jonathan on 12-10-15.
 */
public class Connection implements Runnable{



    private Socket socket;


    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {





        try {
            XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.create(new InputSource(socket.getInputStream()), false);
            try {
                while(xmlStreamReader.hasNext()){





                    if(xmlStreamReader.isStartElement()){
                        if(xmlStreamReader.hasName()){

                            String name = xmlStreamReader.getName().toString();
                            if(name.equals("iq")){
                                System.out.println("RECIEVED A COMMAND");
                                Command command = readCommand(xmlStreamReader);

                                //TODO doe iets met het command
                            }

                            if(name.equals(Message.START_NAME)){
                                System.out.println("RECIEVED A MESSAGE");
                                Message message = readMessage(xmlStreamReader);

                                //TODO doe iets met de message
                            }

                            xmlStreamReader.next();
                            continue;
                        }

                    }

                    //voor debuggen
                    if(xmlStreamReader.hasName()){
                        System.out.println(xmlStreamReader.getName().toString());
                        xmlStreamReader.next();
                    }else{
                        xmlStreamReader.next();
                    }








                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**reads a command from the stream
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


        System.out.println("Message = ");
        return null;
    }


}
