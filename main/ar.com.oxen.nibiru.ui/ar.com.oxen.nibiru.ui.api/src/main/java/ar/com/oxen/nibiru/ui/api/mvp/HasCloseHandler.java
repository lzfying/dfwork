package ar.com.oxen.nibiru.ui.api.mvp;

public interface HasCloseHandler {
	// TODO: estaria bueno devolver un HandlerRegistration, como hace GWT
	void setCloseHandler(CloseHandler closeHandler);
}
