package model.protocol;

import javax.xml.stream.XMLStreamWriter;

/**
 * Created by jonathan on 12-10-15.
 */
public class CommandResult implements Command {


    final String id;

    public CommandResult(String id) {
        this.id = id;
    }

    @Override
    public CommandType getType() {
        return CommandType.RESULT;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void write(XMLStreamWriter writer) {

    }
}
