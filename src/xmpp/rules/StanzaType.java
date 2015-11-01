package xmpp.rules;

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
        public static String ERROR = "error";
        public static String[] getAsList(){

            String[] list = {CHAT, NORMAL, HEADLINE, GROUPCHAT};
            return list;
        }


    }

    public static class Query{

        public static String GET = "get";
        public static String SET = "set";
        public static String RESULT = "result";
        public static String ERROR = "error";

        public static String[] getAsList(){

            String[] list = {GET, SET, RESULT};
            return list;
        }

    }


    public static class Stream{

        public static String OPEN = "open";
        public static String CLOSE = "close";

        public static String[] getAsList(){

            String[] list = {OPEN, CLOSE};
            return list;
        }


    }

}
