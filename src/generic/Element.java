package generic;

/**
 * Created by jonathan on 30-10-15.
 */
public abstract class Element {

    protected final String name;
    protected String value;

    protected Element(String name) {
        this.name = name;
    }

    public Element(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    public boolean hasName(final String name){
        return this.name.equals(name);
    }

    public boolean hasValue(final String value){
        if(this.value == null) return false;

        return this.value.equals(value);
    }

}
