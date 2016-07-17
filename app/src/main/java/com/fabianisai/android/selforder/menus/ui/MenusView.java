package com.fabianisai.android.selforder.menus.ui;

import com.fabianisai.android.selforder.menus.entities.Menu;

import java.util.List;

/**
 * Created by fabianisai on 7/16/16.
 */

public interface MenusView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void onMenusError(String error);
    void setMenus(List<Menu> items);
}
