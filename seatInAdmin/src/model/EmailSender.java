package model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Ale
 */
public class EmailSender {
    
    private static String username, _PASSWORD, host, message;
    
    //------------------------------------------------------------------------------------------------
    
    public EmailSender(String email){
        username = email;
        _PASSWORD = ""; //TODO change
        host = "smtp.office365.com";
    }
    
    //-----------------------------------------------------------------------------------------------
    
    public static void sendNotificationsToStudents(String subject, String body) throws SendFailedException, MessagingException{
        Properties props = System.getProperties();
	props.put("mail.smtp.host",host);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port",587);
        
        Session session = Session.getInstance(props);
        
        String from = username;
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(username, false));
	msg.setSubject(subject);
        
        message = body;
        
	msg.setText(message);
        
        Transport.send(msg,username,_PASSWORD);
    }
    
}