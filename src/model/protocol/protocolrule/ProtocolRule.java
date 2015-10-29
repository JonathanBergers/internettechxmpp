package model.protocol.protocolrule;

/**
 * Created by jonathan on 29-10-15.
 */
public interface ProtocolRule<T> {


    public boolean checkRule(Protocol subject, ProtocolRule rule);



}
