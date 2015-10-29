package model.protocol;

/**
 * Created by jonathan on 29-10-15.
 */
public interface XMPPObject {


    public boolean hasName(final String name);

    public String getDisplayMessage();

    /**checks if the object has a value
     *
     * @return
     */
    public boolean hasValue();
}
