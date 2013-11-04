package ar.com.oxen.nibiru.mail.api;

import java.util.Collection;
import java.util.HashSet;

/**
 * A mail message.
 */
public class MailMessage {
	private String from;
	private Collection<String> to;
	private String subject;
	private String body;
	private String contentType;

	public MailMessage(String from, String subject, String body) {
		this(from, subject, body, "text/html");
	}

	public MailMessage(String from, String subject, String body,
			String contentType) {
		super();
		this.from = from;
		this.subject = subject;
		this.body = body;
		this.contentType = contentType;
		this.to = new HashSet<String>();
	}

	public String getFrom() {
		return from;
	}

	public Collection<String> getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getContentType() {
		return contentType;
	}

}
