package ar.com.oxen.nibiru.sample.eventbus;

public class SampleEvent {
	private String message;

	public SampleEvent(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
