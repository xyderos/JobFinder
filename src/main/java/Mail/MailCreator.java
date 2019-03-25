package Mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

class MailCreator {

    private String to;
    private String filePath;
    private String fileName;
    private static String from;
    private static String pass;
    private static final String SUBJECT="DAILY WORK ADVERTISEMENTS";
    private static final String DOMAIN="smtp.live.com";
    private static final int port=587;
    private static final String TRUE="true";
    private Session ses;

    private final Properties pr=System.getProperties();

    private void setProps(){
        pr.put("mail.smtp.starttls.enable",TRUE);
        pr.put("mail.smtp.host", DOMAIN);
        pr.put("mail.smtp.user", from);
        pr.put("mail.smtp.password", pass);
        pr.put("mail.smtp.port", port);
        pr.put("mail.smtp.auth", TRUE);

        this.ses= Session.getInstance(pr,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);
                    }
                });
    }

    private void createAndSend() throws Exception{

        Message message = new MimeMessage(ses);

        message.setFrom(new InternetAddress(from));

        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

        message.setSubject(SUBJECT);

        message.setText("PFA");

        MimeBodyPart messageBodyPart=new MimeBodyPart();

        Multipart multipart = new MimeMultipart();

        DataSource source = new FileDataSource(this.filePath);

        messageBodyPart.setDataHandler(new DataHandler(source));

        messageBodyPart.setFileName(this.fileName);

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    MailCreator(String from1,String pass1, String to, String fileName, String filePath) throws Exception{

        from=from1;
        pass=pass1;
        this.fileName=fileName;
        this.filePath=filePath;
        this.to=to;
        setProps();
        createAndSend();
    }
}
