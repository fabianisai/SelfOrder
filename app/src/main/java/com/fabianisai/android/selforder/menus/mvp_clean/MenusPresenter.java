package com.fabianisai.android.selforder.menus.mvp_clean;

import com.fabianisai.android.selforder.menus.events.MenusEvent;

/**
 * Created by fabianisai on 7/16/16.
 */

public interface MenusPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
    void getMenus();
    void onEventMainThread(MenusEvent event);
}
