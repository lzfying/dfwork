package ar.com.oxen.nibiru.sample.system.view;



import ar.com.oxen.nibiru.sample.system.view.RolePresenter.Display;
import ar.com.oxen.nibiru.ui.api.mvp.HasClickHandler;
import ar.com.oxen.nibiru.ui.api.mvp.HasCloseHandler;
import ar.com.oxen.nibiru.ui.api.mvp.View;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;

public class RolePresenter extends AbstractPresenter<Display>{
	public interface Display extends View {
		HasClickHandler getFreshBtn();
		
		HasClickHandler getAddBtn();
		
		HasClickHandler getEditBtn();

		HasCloseHandler getClose();
	}

	protected RolePresenter() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void go() {
		// TODO Auto-generated method stub
		//this.configureClose(this.getView());
	}

}
