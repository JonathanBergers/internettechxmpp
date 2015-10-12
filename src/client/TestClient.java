package client;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.internal.stream.writers.XMLDOMWriterImpl;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jonathan on 12-10-15.
 */
public class TestClient {

    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 2000;


    public static void main(String[] args) {
        new TestClient().run();
    }



    public void run(){

        try {

            // connect to server
            Socket socket = new Socket(HOST_NAME, HOST_PORT);
            System.out.println("Client: socket connection established with server");

            // start the threads

            XMLStreamWriter writer = XMLStreamWriterFactory.create(socket.getOutputStream());


            writer.writeStartElement("jonathan");



            writer.writeEndElement();

            socket.close();





        } catch (IOException e) {
            System.out.println("Cannot connect to server");
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }


    }




}
