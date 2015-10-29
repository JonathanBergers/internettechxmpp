package model.protocol;

import model.User;
import old.MessageType;

/**
 * Created by jonathan on 29-10-15.
 * StanzaMessage SAMZA
 *
 */
public interface StanzaMessage extends Stanza{


    public String subject();
    public String body();








}
