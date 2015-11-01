package xmpp.rules;

import generic.xml.XMLElement;

/**
 * Created by jonathan on 1-11-15.
 */
public interface Error {



    static XMLElement alreadyRegistered(){
        return new XMLElement(XMPPStanzas.NAME_ERROR, "user already registered");
    }
    static XMLElement userNotFound(){
        return new XMLElement(XMPPStanzas.NAME_ERROR, "user not found");
    }
    static XMLElement invalidCredentials(){
        return new XMLElement(XMPPStanzas.NAME_ERROR, "invalid credentials");
    }



}
