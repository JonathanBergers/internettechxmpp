package model.protocol.protocolrule;

/**
 * Created by jonathan on 29-10-15.
 */
public interface NestedProtocolInterface<T extends NestedProtocol<T>>  extends Protocol<T>{

    public boolean conformsRec(T subject, boolean conforms);

}
