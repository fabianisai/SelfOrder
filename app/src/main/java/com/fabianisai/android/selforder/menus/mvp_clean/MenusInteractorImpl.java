package com.fabianisai.android.selforder.menus.mvp_clean;

/**
 * Created by fabianisai on 7/16/16.
 */

public class MenusInteractorImpl implements MenusInteractor {
    private MenusRepository menusReposirtory;

    public MenusInteractorImpl(){
        this.menusReposirtory=new MenusRepositoryImpl();
    }

    @Override
    public void getMenusList() {
        menusReposirtory.getMenus();
    }
}
