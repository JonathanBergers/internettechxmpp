package old;

/**
 * Created by jonathan on 12-10-15.
 */
public enum MessageType {

    NORMAL("normal"),
    CHAT("chat"),
    HEADLINE("headline"),
    GROUPCHAT("groupchat"),
    ERROR("error");


    private final String asString;

    MessageType(final String asString) {
        this.asString = asString;
    }


    @Override
    public String toString() {
        return asString;
    }

    public boolean isType(String typeString){

        return asString.equals(typeString);
    }

    public static MessageType getTypeFromString(final String typeString){
        for(MessageType m: MessageType.values()){
            if(m.isType(typeString)) return m;
        }
        return null;

    }

//
//    public static final String RESULT = "result";
//    public static final String GET = "get";
//    public static final String SET = "set";
//
////    RESULT("result"),
////    GET("get"),
////    SET("set"),
////    ERROR("error");
//
//
////    private final String asString;
////
////    CommandType(final String asString) {
////        this.asString = asString;
////    }
////
////
////    @Override
////    public String toString() {
////        return asString;
////    }



}
