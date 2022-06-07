package com.tcc.apiTwilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


public class WhatsApp{

    //Sid e Token da conta twilio
    public static final String ACCOUNT_SID = "seu sid";
    public static final String AUTH_TOKEN = "seu token";

    public void Send(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator( 
            new com.twilio.type.PhoneNumber("whatsapp: seu numero"), 
            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),  
            "⚠️Atenção⚠️! Houve uma detecção!")      
        .create();
    }   
}
