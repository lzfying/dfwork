package ar.com.oxen.nibiru.sample.mail;

import ar.com.oxen.nibiru.mail.api.MailMessage;
import ar.com.oxen.nibiru.mail.api.MailService;
import ar.com.oxen.nibiru.sample.mail.MailSamplePresenter.Display;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class MailSamplePresenter extends AbstractPresenter<Display> {
	public interface Display extends View {
		HasValue<String> getTo();

		HasValue<String> getSubject();

		HasValue<String> getBody();

		HasClickHandler getSend();

		void notifyOk();

		void notifyError(Exception error);
	}

	private MailService mailService;

	public MailSamplePresenter(MailService mailService) {
		super(null);
		this.mailService = mailService;
	}

	@Override
	public void go() {
		this.getView().getSend().setClickHandler(new ClickHandler() {

			@Override
			public void onClick() {
				sendMail();
			}
		});
	}

	private void sendMail() {
		try {
			MailMessage message = new MailMessage("somebody@nibiru.com", this
					.getView().getSubject().getValue(), this.getView()
					.getBody().getValue());
			message.getTo().add(this.getView().getTo().getValue());
			this.mailService.sendMail(message);
			this.getView().notifyOk();
		} catch (Exception e) {
			this.getView().notifyError(e);
		}
	}
}
