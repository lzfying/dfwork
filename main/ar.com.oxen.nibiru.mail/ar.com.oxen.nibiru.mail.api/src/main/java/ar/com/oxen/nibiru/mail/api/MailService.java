package ar.com.oxen.nibiru.mail.api;

/**
 * A service for sending e-mails.
 */
public interface MailService {
	/**
	 * Sends a mail message.
	 * 
	 * @param message
	 *            The mail message
	 */
	void sendMail(MailMessage message);
}
