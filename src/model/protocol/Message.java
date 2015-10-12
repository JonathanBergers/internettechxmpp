package model.protocol;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by jonathan on 12-10-15.
 *
 * XMPP Message volgens protocol
 *
 *
 * Ok, here is the explaination;
 to - specifies the receiver - of course it should be required.
 from - the server routing your message should fill this for you; just leave away.
 type - specifies how romeo's client should handle the messag.
 normal or left away - a small message window should pop up.
 chat - a chat window should pop up.
 headline - message should be handled as a news feed - no response is expected.
 groupchat - for multi-user chat environment; see the according section in this guide.
 error - well, an error message; for details regarding stanza error syntax, refer to RFC 3920.
 xml:lang - the default language of text in this message; this is not supported by any client I know of yet.
 subject - useful especially for type 'normal' and type 'headline'; not supported in combination with type 'chat' in most clients.
 body - yes, this is the message text ;)
 */
public final class Message implements Writable{

    public static final String START_NAME = "message";

    private final String to, from, subject, body;

    private final MessageType type;


    public Message(String to, String from, String subject, String body, MessageType type) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.type = type;
    }

    @Override
    public void write(XMLStreamWriter writer) {



        /* voorbeeld
        <message
          to='romeo@example.net'
          from='juliet@example.com/balcony'
          type='chat'
          xml:lang='en'>
          <subject>I implore you!</subject>
          <subject xml:lang='cz'>Úpěnlivě prosim!</subject>
          <body>Wherefore art thou, Romeo?</body>
          <body xml:lang='cz'>PročeŽ jsi ty, Romeo?</body>
          <thread>e0ffe42b28561960c6b12b944a092794b9683a38</thread>
      </message>
         */

        try {
            writer.writeStartDocument();
            writer.writeStartElement(START_NAME);   //<message
            writer.writeAttribute("to", to);        //<message to=""
            writer.writeAttribute("from", from);
            writer.writeAttribute("type", type.toString());



            writer.writeStartElement("subject");    //<message to="to"....> <subject> chars </subject>
            writer.writeCharacters(subject);
            writer.writeEndElement();


            writer.writeStartElement("body");
            writer.writeCharacters(body);
            writer.writeEndElement();


            writer.writeEndElement();
            writer.writeEndDocument();


        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
