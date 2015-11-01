package xmpp.actions;

import generic.action.ProtocolAction;
import generic.xml.XMLElement;
import generic.xml.XMLProtocol;
import xmpp.rules.Authentication;



/**
 * Created by jonathan on 1-11-15.
 *
 * de acties die uitgevoerd moeten bij het inlog process
 *
 *
 */
public abstract class AuthenticationActions {


    private final int STANZA_INDEX_TO = 0;
    private ServerActions server;




    /**De actie die uitgevoerd moet worden wanneer client om login velden vraagt
     *
     * @return
     */
    ProtocolAction<XMLElement> requestLoginFields(){
        XMLProtocol protocol = Authentication.requestLoginFields(server.getServerSettings());

        return new ProtocolAction<XMLElement>(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {
                String to = element.getAttributeAt(STANZA_INDEX_TO).getValue();
                if(to == null){
                    return false;

                }else if(to.isEmpty()){
                    return false;
                }
                server.writeTo(to, Authentication.responseLoginFields(to, server.getServerSettings()));
                return true;

            }
        };
    }

    ProtocolAction<XMLElement> requestAuthentication(){

        XMLProtocol protocol = Authentication.requestAuthentication(server.getServerSettings());

        return new ProtocolAction<XMLElement>(protocol) {
            @Override
            public boolean onHandle(XMLElement element) {

                //TODO
                return false;
            }
        }



    }



}
