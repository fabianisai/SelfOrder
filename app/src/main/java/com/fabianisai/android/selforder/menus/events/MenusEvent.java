package com.fabianisai.android.selforder.menus.events;

import com.fabianisai.android.selforder.menus.entities.Menu;

import java.util.List;

/**
 * Created by fabianisai on 7/16/16.
 */

public class MenusEvent {
    private String error;
    private List<Menu> menus;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
