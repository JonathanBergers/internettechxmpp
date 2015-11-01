package model;

import interfaces.Writable;
import model.RegisteredUser;

/**
 * Created by jonathan on 1-11-15.
 */
public interface Connection {



    void setActiveUser(RegisteredUser user);


    void writeResponse(Writable writable);



}
