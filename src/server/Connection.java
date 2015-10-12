package server;

import com.sun.xml.internal.stream.writers.XMLEventWriterImpl;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import jdk.internal.util.xml.impl.XMLWriter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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



                    if(xmlStreamReader.hasText()){
                        System.out.println(xmlStreamReader.getText());
                    }
                    if(xmlStreamReader.hasName()){
                        System.out.println(xmlStreamReader.getName());
                    }
                    System.out.println(xmlStreamReader.next());
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
