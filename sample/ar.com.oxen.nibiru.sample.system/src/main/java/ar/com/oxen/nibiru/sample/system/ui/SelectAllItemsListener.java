/*
 * Copyright (c) InforSuite CVICSE Middleware Co., LTD.
 * All rights reserved.
 */

package ar.com.oxen.nibiru.sample.system.ui;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.Table.HeaderClickListener;

/**
 * <p>Handle selection status of all items in table.<p>
 * 
 * @date 2013-1-31 <br>
 * @author li_zhi1 <br>
 * @version 9.0.0 <br>
 * 
 */
public class SelectAllItemsListener implements HeaderClickListener {

    private static final long serialVersionUID = 1L;

    private Object selectColunmID;

    private boolean selected;

    public SelectAllItemsListener(Object selectColunmID) {
        this.selectColunmID = selectColunmID;
        this.selected = false;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.vaadin.ui.Table.HeaderClickListener#headerClick(com.vaadin.ui.Table
     * .HeaderClickEvent)
     */
    @Override
    public void headerClick(HeaderClickEvent event) {
        Table table = (Table) event.getComponent();
        Object colunmID = event.getPropertyId();

        if (selectColunmID.equals(colunmID)) {
            if (selected) {
                table.setColumnIcon(selectColunmID,  new ThemeResource(
        				"icons/uncheck.png"));
                selected = false;
            } else {
                table.setColumnIcon(selectColunmID, new ThemeResource("icons/checked.png"));
                selected = true;
            }

            IndexedContainer appContainer = (IndexedContainer) table.getContainerDataSource();
            for (int i = 0; i < appContainer.size(); i++) {
                Item item = appContainer.getItem(appContainer.getIdByIndex(i));
                CheckBox chkbx = (CheckBox) item.getItemProperty(selectColunmID).getValue();
                chkbx.setValue(selected);
            }
        }
    }

}
