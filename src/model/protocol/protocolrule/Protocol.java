package model.protocol.protocolrule;

/**
 * Created by jonathan on 29-10-15.
 */
public interface Protocol<T> {


    public boolean conforms(ProtocolRule rule, T subject);

}
