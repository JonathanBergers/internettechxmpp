package model.protocol;

/**
 * Created by jonathan on 12-10-15.
 */
public enum MessageType {

    NORMAL("normal"),
    CHAT("chat"),
    HEADLINE("headline"),
    GROUPCHAT("groupchat"),
    ERROR("error");


    private final String asString;

    MessageType(final String asString) {
        this.asString = asString;
    }


    @Override
    public String toString() {
        return asString;
    }
}
