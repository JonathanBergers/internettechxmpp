package model.protocol;

/**
 * Created by jonathan on 12-10-15.
 *
 *
 *
 */
public interface Command extends Writable{


    public CommandType getType();
    public String getId();




}