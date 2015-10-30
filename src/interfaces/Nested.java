package interfaces;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 29-10-15.
 */
public interface Nested<T> {


    public LinkedList<T> getChildren();
    public T getParent();
    public T getChildAt(int index);
    public void addChild(T child);



}
