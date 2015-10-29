package model.protocol;

import model.User;
import model.protocol.StanzaType;

/**
 * Created by jonathan on 29-10-15.
 */
public interface Stanza {

    public User to();
    public User from();
    public String type();
    public String id();






}
