
import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class TestEmail {

	public static void main(String[] args) throws AddressException, MessagingException {
		
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "relay.smtp.av.net");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.socketFactory.port", "");
		props.put("mail.smtp.socketFactory.class", "");
		props.put("mail.smtp.auth", "false");
		props.put("auth.forgot.password", "XXXX");
		props.put("auth.forgot.password.email", "a@av.com");
		
		
		String fromAddress = "a@av.com";
		String toAddress = "dy@in.com";
		String ccAddress = "";
		String bccAddress = "";

		String messageSubjectPart = "Test mail from test jar";
		String messageBodyPart ="Dummy test Message body part";

		List<String> attachmentFiles = null;
		Session session = Session.getDefaultInstance(props);

		
		
		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(fromAddress));

		// Set To: header field of the header.

		if (toAddress != null && !toAddress.equals(""))
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));

		if (ccAddress != null && !ccAddress.equals(""))
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));

		if (bccAddress != null && !bccAddress.equals(""))
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccAddress));

		// Set Subject: header field
		message.setSubject(messageSubjectPart);

		// set body text
		message.setSubject(messageSubjectPart);
		MimeBodyPart mimemessageBodyPart = new MimeBodyPart();
		mimemessageBodyPart.setContent(messageBodyPart != null ? messageBodyPart : "", "text/html");

		// add text body part to mutipart
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimemessageBodyPart);

		if (attachmentFiles != null && !attachmentFiles.isEmpty()) {
			for (String attachment : attachmentFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
				attachPart.setFileName(new File(attachment).getName());
				multipart.addBodyPart(attachPart);
			}
		}

		message.setContent(multipart);
		System.out.println("Send mail Transport...");
		// Send message
		Transport.send(message);

		System.out.println("MAIL_SENT SUCCESSFUL");
		
	}

}
