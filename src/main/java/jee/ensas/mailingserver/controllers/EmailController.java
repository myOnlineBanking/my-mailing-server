package jee.ensas.mailingserver.controllers;

import jee.ensas.mailingserver.models.MailModel;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@RestController
@RequestMapping("/Mailing")
@CrossOrigin(origins = "*")
public class EmailController {

    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody MailModel mailModel) {
        try {
            sendmail(mailModel);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return "Email sent successfully";
    }


    private void sendmail(MailModel mailModel) throws AddressException, MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("<YOUR_EMAIL>", "<YOUR_PASSWORD>");
            }
        });


        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("<YOUR_EMAIL>", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailModel.getEmailReceiver()));
        msg.setSubject(mailModel.getSubject());
        msg.setContent(mailModel.getContent(), "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mailModel.getContent(), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}