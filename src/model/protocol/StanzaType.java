package model.protocol;

/**
 * Created by jonathan on 29-10-15.
 */
public class StanzaType {

    public static String ERROR = "error";


    public static class Message{

        public static String CHAT = "chat";
        public static String NORMAL = "set";
        public static String HEADLINE = "headline";
        public static String GROUPCHAT = "groupchat";


    }

    public static class Query{

        public static String GET = "get";
        public static String SET = "set";
        public static String RESULT = "result";

    }


}
