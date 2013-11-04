package ar.com.oxen.nibiru.mail.javamail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ar.com.oxen.nibiru.mail.api.MailException;
import ar.com.oxen.nibiru.mail.api.MailMessage;
import ar.com.oxen.nibiru.mail.api.MailService;

public class JavaMailService implements MailService {
	private Session session;
	private String username;
	private String password;
	private String host;
	private int port;
	private boolean startTls;

	public void init() {
		final Properties mailProperties = new Properties();

		if (username != null) {
			mailProperties.put("mail.smtp.auth", "true");
		}
		if (startTls) {
			mailProperties.put("mail.smtp.starttls.enable", "true");
		}
		mailProperties.put("mail.smtp.host", this.host);
		mailProperties.put("mail.smtp.port", String.valueOf(this.port));

		this.session = Session.getDefaultInstance(mailProperties,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
	}

	@Override
	public void sendMail(MailMessage message) {
		try {
			MimeMessage mimeMessage = new MimeMessage(this.session);
			mimeMessage.setFrom(new InternetAddress(message.getFrom()));
			for (String recipient : message.getTo()) {
				mimeMessage.addRecipient(Message.RecipientType.TO,
						new InternetAddress(recipient));
			}
			mimeMessage.setSubject(message.getSubject());

			if (message.getContentType() == null
					| message.getContentType().equals("text/plain")) {
				mimeMessage.setText(message.getBody());
			} else {
				mimeMessage.setContent(message.getBody(),
						message.getContentType());
			}

			Transport.send(mimeMessage);

		} catch (AddressException e) {
			throw new MailException(e);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setStartTls(boolean startTls) {
		this.startTls = startTls;
	}
}
