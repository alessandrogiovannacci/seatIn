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
    
    private static String username, _PASSWORD, host, password, activationCode, message;
    
    public EmailSender(String email){
        username = email;
        _PASSWORD = ""; //TODO change
        host = "smtp.office365.com";
    }
    //------------------------------------------------------------------------------------------------
    
    public EmailSender(String email, String dbPassword, String dbActivationCode){
        username = email;
        _PASSWORD = ""; //TODO change
        host = "smtp.office365.com";
        password = dbPassword;
        activationCode = dbActivationCode;
    }
    
    //------------------------------------------------------------------------------------------------
    
    public static void sendRegEmail() throws SendFailedException, MessagingException{
        Properties props = System.getProperties();
	props.put("mail.smtp.host",host);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port",587);
        
        Session session = Session.getInstance(props);
        
        String from = username;
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(username, false)); //change parameter to send to another index
	msg.setSubject("Profile activation");
        
        message = "Use this password to login, and"
                + " then insert the activation code to activate your profile."
                + "\nTemporary password: " + password + "\nActivation code: " + activationCode;
        
	msg.setText(message);
        Transport.send(msg,username,_PASSWORD);
        System.out.println("Mail was sent successfully."); 
    }
    
    //------------------------------------------------------------------------------------------------
    
    public static void sendPasswordRecoveryEmail() throws SendFailedException, MessagingException{
        Properties props = System.getProperties();
	props.put("mail.smtp.host",host);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port",587);
        
        Session session = Session.getInstance(props);
        
        String from = username;
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(username, false));
	msg.setSubject("Password Recovery");
        
        message = "New password: " + password + "\nActivation code: " + activationCode;
        
	msg.setText(message);
        
        Transport.send(msg,username,_PASSWORD);
        System.out.println("Mail was sent successfully."); 
    }
    
    //------------------------------------------------------------------------------------------------
    
    public static void sendCourseSubscriptionEmail(String email) throws SendFailedException, MessagingException{
        Properties props = System.getProperties();
	props.put("mail.smtp.host",host);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port",587);
        
        Session session = Session.getInstance(props);
        
        String from = username;
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email, false));
        msg.setSubject("Course subscription");
        
        message = "The subscription to the course has been successfully completed";
        
	msg.setText(message);
        
        Transport.send(msg,username,_PASSWORD);
        System.out.println("Mail was sent successfully."); 
    
    }
    
}
