package model;

import model.xml.XMLObject;

/**
 * Created by jonathan on 29-10-15.
 */
public class StanzaBuildException extends Exception {


    private final XMLObject object;


    public StanzaBuildException(String message, XMLObject object) {
        super(message);
        this.object = object;
    }

    public XMLObject getObject() {
        return object;
    }


    public StanzaBuildException(String message) {
        super(message);
        object = new XMLObject() {
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
