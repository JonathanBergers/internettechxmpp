package model.protocol;

/**
 * Created by jonathan on 12-10-15.
 */
public enum CommandType {

    RESULT("result"),
    ERROR("error");


    private final String asString;

    CommandType(final String asString) {
        this.asString = asString;
    }


    @Override
    public String toString() {
        return asString;
    }
}
