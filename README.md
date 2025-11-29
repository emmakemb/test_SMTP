# How to Send Emails in Java using SMTP and Email API: A Step-by-Step Guide

This guide provides a comprehensive walkthrough for sending emails in Java using SMTP (Simple Mail Transfer Protocol) and the Jakarta Mail API (formerly JavaMail).

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Setting Up Dependencies](#setting-up-dependencies)
3. [Basic Email Sending](#basic-email-sending)
4. [Sending HTML Emails](#sending-html-emails)
5. [Sending Emails with Attachments](#sending-emails-with-attachments)
6. [Common SMTP Server Configurations](#common-smtp-server-configurations)
7. [Best Practices](#best-practices)
8. [Troubleshooting](#troubleshooting)

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven or Gradle for dependency management
- Access to an SMTP server (Gmail, Outlook, or custom SMTP server)

## Setting Up Dependencies

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>jakarta.mail</artifactId>
    <version>2.0.1</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```groovy
implementation 'com.sun.mail:jakarta.mail:2.0.1'
```

## Basic Email Sending

Here's a simple example of sending a plain text email:

```java
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String to, String subject, String body) {
        // SMTP server configuration
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "YOUR_EMAIL_HERE";  // Replace with your email
        String password = "YOUR_APP_PASSWORD_HERE";  // Replace with your app password

        // Set properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create session with authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public static void main(String[] args) {
        sendEmail("recipient@example.com", "Test Subject", "Hello, this is a test email!");
    }
}
```

## Sending HTML Emails

To send HTML-formatted emails:

```java
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class HtmlEmailSender {

    public static void sendHtmlEmail(String to, String subject, String htmlContent) {
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "YOUR_EMAIL_HERE";  // Replace with your email
        String password = "YOUR_APP_PASSWORD_HERE";  // Replace with your app password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            
            // Set HTML content
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("HTML email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }

    public static void main(String[] args) {
        String htmlContent = """
            <html>
            <body>
                <h1>Welcome!</h1>
                <p>This is an <b>HTML</b> email.</p>
            </body>
            </html>
            """;
        sendHtmlEmail("recipient@example.com", "HTML Email Test", htmlContent);
    }
}
```

## Sending Emails with Attachments

To send emails with file attachments:

```java
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import java.util.Properties;

public class AttachmentEmailSender {

    public static void sendEmailWithAttachment(String to, String subject, 
            String body, String filePath) {
        
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "YOUR_EMAIL_HERE";  // Replace with your email
        String password = "YOUR_APP_PASSWORD_HERE";  // Replace with your app password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Create multipart message
            Multipart multipart = new MimeMultipart();

            // Text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);
            multipart.addBodyPart(textPart);

            // Attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(source.getName());
            multipart.addBodyPart(attachmentPart);

            // Set content
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }

    public static void main(String[] args) {
        sendEmailWithAttachment(
            "recipient@example.com",
            "Email with Attachment",
            "Please find the attached file.",
            "/path/to/your/file.pdf"
        );
    }
}
```

## Common SMTP Server Configurations

### Gmail

```properties
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
mail.smtp.auth=true
mail.smtp.starttls.enable=true
```

**Note:** For Gmail, you need to use an [App Password](https://support.google.com/accounts/answer/185833) instead of your regular password.

### Outlook/Office 365

```properties
mail.smtp.host=smtp.office365.com
mail.smtp.port=587
mail.smtp.auth=true
mail.smtp.starttls.enable=true
```

### Yahoo Mail

```properties
mail.smtp.host=smtp.mail.yahoo.com
mail.smtp.port=587
mail.smtp.auth=true
mail.smtp.starttls.enable=true
```

### Custom SMTP Server with SSL

```properties
mail.smtp.host=your-smtp-server.com
mail.smtp.port=465
mail.smtp.auth=true
mail.smtp.ssl.enable=true
```

## Best Practices

1. **Use Environment Variables**: Never hardcode credentials in your source code. Use environment variables or a secure configuration management system.

   ```java
   String username = System.getenv("SMTP_USERNAME");
   String password = System.getenv("SMTP_PASSWORD");
   ```

2. **Enable TLS/SSL**: Always use secure connections (STARTTLS or SSL) to protect email content and credentials.

3. **Handle Exceptions Properly**: Implement proper error handling and logging for production applications.

4. **Validate Email Addresses**: Validate email addresses before attempting to send.

5. **Use Connection Pooling**: For high-volume email sending, consider using connection pooling or a dedicated email service.

6. **Set Timeouts**: Configure appropriate timeouts to prevent hanging connections.

   ```java
   props.put("mail.smtp.connectiontimeout", "5000");
   props.put("mail.smtp.timeout", "5000");
   props.put("mail.smtp.writetimeout", "5000");
   ```

## Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| `AuthenticationFailedException` | Check username/password. For Gmail, use App Password. |
| `Could not connect to SMTP host` | Verify host, port, and firewall settings. |
| `STARTTLS is required` | Set `mail.smtp.starttls.enable=true` |
| `Connection timed out` | Check network connectivity and SMTP server availability. |

### Debugging

Enable debug mode to see detailed SMTP communication:

```java
session.setDebug(true);
```

## Additional Resources

- [Jakarta Mail Official Documentation](https://jakarta.ee/specifications/mail/)
- [RFC 5321 - Simple Mail Transfer Protocol](https://tools.ietf.org/html/rfc5321)
- [Gmail SMTP Settings](https://support.google.com/mail/answer/7126229)

## License

This guide is provided for educational purposes. Feel free to use and modify the code examples as needed.
