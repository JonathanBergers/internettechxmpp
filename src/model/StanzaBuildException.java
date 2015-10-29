package model;

import model.protocol.XMPPObject;

/**
 * Created by jonathan on 29-10-15.
 */
public class StanzaBuildException extends Exception {


    private final XMPPObject object;


    public StanzaBuildException(String message, XMPPObject object) {
        super(message);
        this.object = object;
    }

    public XMPPObject getObject() {
        return object;
    }


    public StanzaBuildException(String message) {
        super(message);
        object = new XMPPObject() {
            @Override
            public boolean hasName(String name) {
                return false;
            }

            @Override
            public String getDisplayMessage() {
                return "Null";
            }

            @Override
            public boolean hasValue() {
                return false;
            }
        };
     }
    }
