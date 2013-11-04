/**
 * 
 */
package ar.com.oxen.nibiru.sample.system.api;

import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseWidget;
import ar.com.oxen.nibiru.ui.api.mvp.HasTitle;
import ar.com.oxen.nibiru.ui.api.mvp.HasValue;
import ar.com.oxen.nibiru.ui.api.mvp.View;

/**
 * @author li_zhi
 *
 */
public interface IRoleView extends View , HasTitle{
	HasClickHandler getAdd();

	HasClickHandler getUpdate();

	HasClickHandler getDelete();

	void notifyOk();

	void notifyError(Exception error);
}
