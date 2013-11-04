package ar.com.oxen.nibiru.ui.vaadin.view.adapter;

import java.io.InputStream;

import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Embedded;

public class EmbeddedAdapter extends AbstractComponentAdapter<Embedded>
		implements ar.com.oxen.nibiru.ui.api.view.Embedded {
	private Application application;

	public EmbeddedAdapter(Embedded embedded, Application application) {
		super(embedded);
		this.application = application;
		this.getAdapted().setType(Embedded.TYPE_BROWSER);
	}

	@Override
	public void setData(final InputStream data, String format) {
		this.getAdapted().setSource(new StreamResource(new StreamSource() {
			private static final long serialVersionUID = -5506151038490088459L;

			@Override
			public InputStream getStream() {
				return data;
			}
		}, "dummy." + format, this.application));
	}
}
