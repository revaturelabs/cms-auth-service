package com.revature.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.revature.entity.User;



public class JavaMailUtil {
	
public static boolean sendMail(User user) throws MessagingException {
		
		//Configuring the properties fo the mail, we can put a key and a value to this properties
		System.out.println("Preparing to send mail to: "+user.getEmail());
		
		final String myAccountEmail = "buy.stuffs2019@gmail.com";
		final String password = "/g4^7Uk?98";
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); //TLS
		
		
		//Session is part of the API
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		//Message is part of the API
		Message message = prepareMessage(session, myAccountEmail, user);
		
		//Transport is part of the API
		Transport.send(message);
		System.out.println("Message sent successfully ");
		return true;
		
	}

	private static Message prepareMessage(Session session, String myAccountEmail, User user) {
		
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setSubject("Password Reset Request");
			message.setText("Please find below your new password \n"+user.getPassword());
//			String html = "<h1>This is an example using html </h1><br> <h2><b>Welcome to the Java mail API</b></h2>";
//			message.setContent(html, "text/html");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
		
		return null;
	}

}
