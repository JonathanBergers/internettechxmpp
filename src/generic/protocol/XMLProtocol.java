package generic.protocol;

import generic.Element;
import interfaces.Nested;
import generic.xml.XMLAttribute;
import generic.xml.XMLElement;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 29-10-15.
 */
public abstract class XMLProtocol<T extends Element> implements Nested<XMLProtocol>, Protocol<T> {

    private LinkedList<XMLProtocol> children = new LinkedList<>();
    private XMLProtocol parent;
    protected LinkedList<Protocol<XMLAttribute>> attributes = new LinkedList<>();

    public XMLProtocol(XMLProtocol parent) {
        this.parent = parent;
    }

    public XMLProtocol(){
        this.parent = null;
    }


    @Override
    public LinkedList<XMLProtocol> getChildren() {
        return children;
    }

    @Override
    public XMLProtocol getParent() {
        return parent;
    }

    @Override
    public XMLProtocol getChildAt(int index) {
        return children.get(index);
    }

    public Protocol<XMLAttribute> addAttributeProtocol(Protocol<XMLAttribute> attributeProtocol) {
        attributes.addLast(attributeProtocol);
        return attributeProtocol;

    }

    public XMLProtocol addChildProtocol(XMLProtocol elementProtocol) {
        elementProtocol.setParent(this);
        children.add(elementProtocol);
        return elementProtocol;
    }


    @Override
    public abstract boolean conforms(T input);


    /**checks recursive if the element conforms the protocol.
     *
     * @param element
     * @param protocol
     * @return
     */
    public boolean checkRecursive(XMLElement element, XMLProtocol<XMLElement> protocol) {

        boolean startBoolean = true;
        if (!protocol.conforms(element)) {

            return false;
        } else if(!checkAttributes(element, protocol)) {

            System.out.println("ATTRIBUTE CHECK failed");
            return false;
        }else{

            List<XMLElement> elementChildren = element.getChildren();
            List<XMLProtocol> childProtocols = getChildren();


            int elSize = elementChildren.size();
            int proSize = childProtocols.size();

            // aantal elementen komt niet overeen
            if (elSize != proSize) {
                System.out.println("Size of elements doesnt match");
                System.out.println("element size = : " + elSize);
                System.out.println("protocol el size = : " + proSize);
                return false;
            }

            for (int i = 0; i < elSize; i++) {

                XMLElement elmt = elementChildren.get(i);
                XMLProtocol prot = childProtocols.get(i);

                boolean b = prot.checkRecursive(elmt, prot, startBoolean);

                if (!b) {
                    return false;
                }

            }
            return true;

        }

    }

    private boolean checkRecursive(XMLElement element, XMLProtocol protocol, boolean b) {

        boolean startBoolean = true;
        if (!protocol.conforms(element)) {

            return false;
        } else if(!checkAttributes(element, protocol)) {

            return false;
        }else{

                List<XMLElement> elementChildren = element.getChildren();
                List<XMLProtocol> childProtocols = getChildren();


                int elSize = elementChildren.size();
                int proSize = childProtocols.size();

                // aantal elementen komt niet overeen
                if (elSize != proSize) {
                    return false;
                }

                for (int i = 0; i < elSize; i++) {

                    XMLElement elmt = elementChildren.get(i);
                    XMLProtocol prot = childProtocols.get(i);

                    b = prot.checkRecursive(elmt, prot, startBoolean);

                    if (!b) {
                        return false;
                    }

                }
                return b;

            }
    }

    /**
     * checks if the attributes are valid
     * @param element
     * @param attributeProtocol
     * @return
     */
    public boolean checkAttributes(XMLElement element, XMLProtocol attributeProtocol) {

        List<XMLAttribute> elementChildren = element.getAttributes();
        List<Protocol<XMLAttribute>> childProtocols = attributeProtocol.getAttributes();

        int elSize = elementChildren.size();
        int proSize = childProtocols.size();

        // aantal elementen komt niet overeen
        if (elSize != proSize) {
            System.out.println("Size of attributes doesnt match");

            return false;
        }

        for (int i = 0; i < elSize; i++) {

            XMLAttribute elmt = elementChildren.get(i);
            Protocol<XMLAttribute> prot = childProtocols.get(i);



            if (!prot.conforms(elmt)) {
                return false;
            }
        }
        return true;

    }

    public LinkedList<Protocol<XMLAttribute>> getAttributes() {
        return attributes;
    }


    private void setParent(XMLProtocol parent) {
        this.parent = parent;
    }


    @Override
    public void addChild(XMLProtocol child) {
        getChildren().addLast(child);
    }
}
