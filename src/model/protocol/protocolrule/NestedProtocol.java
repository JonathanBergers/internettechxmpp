package model.protocol.protocolrule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 29-10-15.
 */
public abstract class NestedProtocol<T extends NestedProtocolInterface<T>> implements NestedProtocolInterface<T>{

    protected List<NestedProtocolInterface<T>> childProtocols = new ArrayList<>();
    protected final Protocol<T> parentProtocol;


    public NestedProtocol(Protocol<T> parentProtocol) {
        this.parentProtocol = parentProtocol;
    }

    public void addChildProtocol(NestedProtocolInterface<T> childProtocol){
        childProtocols.add(childProtocol);
    }

    public NestedProtocolInterface<T> getChildAt(int index){

        return childProtocols.get(index);
    }

    public boolean isLeaf(){

        return childProtocols.isEmpty();

    }

    public boolean conformsRec(T subject, boolean conforms){

        if(!conforms){
            return false;
        }
        else{
            conforms = subject.conformsRec(subject, conforms);
            return conforms;
        }
    }
}
