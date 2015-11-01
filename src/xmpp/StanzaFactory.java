package xmpp;

import generic.xml.XMLElement;
import model.User;
import xmpp.rules.StanzaType;

import java.util.List;

/**
 * Created by jonathan on 29-10-15.
 */
public class StanzaFactory {


    private final static String MESSAGE_NAME ="message";
    private final static String COMMAND_NAME ="iq";
    private final static String QUERY_NAME ="q";
    private final static String STREAM_NAME ="stream";

    static XMLElement createRootStanzaElement(final String rootName, final String to, final String  from, final String type, final String id){
        XMLElement element = new XMLElement(rootName);
        element.addAttribute("to", to).addAttribute("from" , from).addAttribute("type", type).addAttribute("id", id);
        return element;
    }








    public static class Server{

        public static String SERVER_NAME = "server@server.com";


        public static String ID_AUTH_RESPONSE = "auth2";
        public static String ID_LOGIN_RESPONSE = "auth4";


        public static String ID_CONTACT_RESPONSE = "contact2";


        /**Builds a message to write to the stream
         *
         * @param to
         * @param from
         * @param type
         * @param id
         * @param subject
         * @param body
         * @return
         */
        public static XMLElement buildMessage(final String to, final String  from, final String type, final String id,
                                              final String subject, final String body){

            XMLElement element = createRootStanzaElement(MESSAGE_NAME, to, from, type, id);
            element.addElement("subject", subject);
            element.addElement("body", body);

            return element;


        }


        /**De query die verstuurt moet worden wanneer iemand vraagt wat de gegevens zijn om in te loggen
         *
         * @param to
         * @return
         */
        public static XMLElement buildQueryLogin(final String to){

            XMLElement element = createRootStanzaElement(QUERY_NAME, to, SERVER_NAME, StanzaType.Query.RESULT, ID_AUTH_RESPONSE);
            element.addElement("username");
            element.addElement("password");
            return element;

        }

        /**De query die verstuurt moet worden wanneer heeft geprobeerd in te loggen
         *
         * @param to
         * @return
         */
        public static XMLElement buildQueryLoginResult(final String to, boolean success){

            XMLElement element;

            if(success){
                element = createRootStanzaElement(QUERY_NAME, to, SERVER_NAME, StanzaType.Query.RESULT, ID_LOGIN_RESPONSE);
            }else{
                element = createRootStanzaElement(QUERY_NAME,to, SERVER_NAME, StanzaType.Query.ERROR, ID_LOGIN_RESPONSE);
                element.addElement("error", "not-authorized");

            }

            return element;

        }

        /**De query die verstuurt moet worden wanneer gebruiker contacten wilt inzien
         *
         * @param to
         * @return
         */
        public static XMLElement buildQueryContacts(final String to){

            XMLElement element;


            element = createRootStanzaElement(QUERY_NAME, to, SERVER_NAME, StanzaType.Query.RESULT, ID_LOGIN_RESPONSE);


            return element;

        }

        /**De query die verstuurt moet worden wanneer iemand vraagt wat de gegevens zijn om in te loggen
         *
         * @param to
         * @return
         */
        public static XMLElement buildQueryAddContactResult(final String to){

            XMLElement element = createRootStanzaElement(QUERY_NAME, to, SERVER_NAME, StanzaType.Query.RESULT, ID_CONTACT_RESPONSE);
            return element;

        }

        /**De query die verstuurt moet worden wanneer iemand vraagt wat de gegevens zijn om in te loggen
         *
         * @param to
         * @return
         */
        public static XMLElement buildQueryAddContactResultError(final String to, final String errorMessage){

            XMLElement element = createRootStanzaElement(QUERY_NAME, to, SERVER_NAME, StanzaType.Query.ERROR, ID_CONTACT_RESPONSE);
            element.addElement("error", errorMessage);
            return element;

        }


        public static XMLElement buildUser(User u){
            XMLElement element = new XMLElement("user");
            element.addElement("username", u.getEmail());

            return element;



        }

        public static XMLElement buildUsers(List<User> users){

            XMLElement element = new XMLElement("users");

            for(User u: users){
                element.addElement(buildUser(u));
            }
            return element;
        }

        /**bericht om de stream af te sluiten
         *
         * @param to
         * @param from
         * @return
         */
        public static XMLElement buildStream(final String to, final String  from){

            XMLElement element = createRootStanzaElement(STREAM_NAME, to, from, StanzaType.Stream.CLOSE, "closeStream");
            return element;


        }


        static class Client{


            /**bericht op een stream te openen
             *
             * @param to
             * @param from
             * @return
             */
            public static XMLElement buildStream(final String to, final String  from){

                XMLElement element = createRootStanzaElement(STREAM_NAME, to, from, StanzaType.Stream.OPEN, "startstream");
                return element;


            }



        }

    }



















}
