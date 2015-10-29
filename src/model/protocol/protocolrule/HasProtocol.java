package model.protocol.protocolrule;

/**
 * Created by jonathan on 29-10-15.
 */
public interface HasProtocol<T extends Protocol, Z> {

    public boolean check(T protocol, Z self);
}
