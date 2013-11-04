package ar.com.oxen.nibiru.application.api;

public class ApplicationThemeChangeEvent {
	private String theme;

	public ApplicationThemeChangeEvent(String theme) {
		super();
		this.theme = theme;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
