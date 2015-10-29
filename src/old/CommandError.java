//package old;
//
//import model.protocol.StanzaCommand;
//
//import javax.xml.stream.XMLStreamWriter;
//
///**
// * Created by jonathan on 12-10-15.
// */
//public final class CommandError implements StanzaCommand {
//
//    private final String id;
//    private final String message;
//
//    public CommandError(String id, String message) {
//        this.id = id;
//        this.message = message;
//    }
//
//
//    @Override
//    public CommandType getType() {
//        return CommandType.ERROR;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    @Override
//    public void write(XMLStreamWriter writer) {
//
//    }
//}
