package ar.com.oxen.nibiru.ui.api.view;

import java.io.InputStream;


public interface Embedded extends Component {
	void setData(InputStream data, String format);
}
