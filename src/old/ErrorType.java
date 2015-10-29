package old;

/**
 * Created by jonathan on 27-10-15.
 */
public enum ErrorType {

    AUTH("auth");


    private final String asString;

    ErrorType(final String asString) {
        this.asString = asString;
    }


    @Override
    public String toString() {
        return asString;
    }

    public boolean isType(String typeString){

        return asString.equals(typeString);
    }

    public ErrorType getTypeFromString(final String typeString){
        for(ErrorType m: ErrorType.values()){
            if(m.isType(typeString)) return m;
        }
        return null;

    }
}
