package org.example.org.mailtrap;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        // recipient email (Mailtrap accepts any email)
        String to = "e.kembou@gmx.de";

        // sender email (must match Mailtrap dashboard 'from' address)
        String from = "e.kix@yahoo.com";

        // Mailtrap SMTP credentials
        final String username =  "api";       //"api";
        final String password = "f1a49f06bac95277f646e7dc22123378";

        // Mailtrap SMTP host
        String host = "smtp.mailtrap.io";

        // configure SMTP details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // create the mail Session object
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // create a MimeMessage object
            Message message = new MimeMessage(session);

            // set From email field
            message.setFrom(new InternetAddress(from));

            // set To email field
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // set email subject field
            message.setSubject("Hello from Jakarta Mail + Mailtrap");

            // set the content of the email message
            message.setText("This is a test email from Jakarta Mail using Mailtrap SMTP.");

            // send the email message
            Transport.send(message);

            System.out.println("Email Message Sent Successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
