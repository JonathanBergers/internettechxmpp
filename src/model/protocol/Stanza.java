package model.protocol;

import model.User;

/**
 * Created by jonathan on 29-10-15.
 */
public interface Stanza {

    public User to();
    public User from();
    public String type();

    // recommended for message and presence, required for command
    public String id();






}
