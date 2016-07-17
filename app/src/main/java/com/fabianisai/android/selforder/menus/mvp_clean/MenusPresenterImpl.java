package com.fabianisai.android.selforder.menus.mvp_clean;

import com.fabianisai.android.selforder.lib.eventbus.EventBus;
import com.fabianisai.android.selforder.lib.eventbus.GreenRobotEventBus;
import com.fabianisai.android.selforder.menus.entities.Menu;
import com.fabianisai.android.selforder.menus.events.MenusEvent;
import com.fabianisai.android.selforder.menus.ui.MenusView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by fabianisai on 7/16/16.
 */

public class MenusPresenterImpl implements MenusPresenter {

    private EventBus eventBus;
    private MenusView menusView;
    private MenusInteractor menusInteractor;


    public MenusPresenterImpl(MenusView negociosView){
        this.menusView=negociosView;
        this.menusInteractor= new MenusInteractorImpl();
        this.eventBus= GreenRobotEventBus.getInstance();
       // eventBus.register(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        menusView=null;
    }

    @Override
    public void getMenus() {
        if (this.menusView != null){
            menusView.hideList();
            menusView.showProgress();
        }
        this.menusInteractor.getMenusList();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MenusEvent event) {
        String errorMsg = event.getError();
        if (this.menusView != null) {
            menusView.showList();
            menusView.hideProgress();
            if (errorMsg != null) {
                this.menusView.onMenusError(errorMsg);
            } else {
                List<Menu> items = event.getMenus();
                if (items != null && !items.isEmpty()) {
                    this.menusView.setMenus(items);
                }
            }
        }

    }
}
