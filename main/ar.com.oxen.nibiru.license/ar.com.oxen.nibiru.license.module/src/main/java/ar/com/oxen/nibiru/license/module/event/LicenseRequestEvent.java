package ar.com.oxen.nibiru.license.module.event;

/**
 * Event for requesting a license.
 */
public class LicenseRequestEvent {
	private boolean showInvalidLicenseMessage;
	private Object callbackEvent;
	private String callbackTopic;

	/**
	 * @param showInvalidLicenseMessage
	 *            True if an invalid license message must be shown
	 * @param callbackEvent
	 *            The event to be fired once the license is loaded
	 * @param callbackTopic
	 *            The same, for topic
	 */
	public LicenseRequestEvent(boolean showInvalidLicenseMessage,
			Object callbackEvent, String callbackTopic) {
		super();
		this.showInvalidLicenseMessage = showInvalidLicenseMessage;
		this.callbackEvent = callbackEvent;
		this.callbackTopic = callbackTopic;
	}

	public boolean getShowInvalidLicenseMessage() {
		return showInvalidLicenseMessage;
	}

	public Object getCallbackEvent() {
		return callbackEvent;
	}

	public String getCallbackTopic() {
		return callbackTopic;
	}
}
