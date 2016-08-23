package com.fabianisai.android.selforder.login.mvp_clean;

import com.fabianisai.android.selforder.login.events.LoginEvent;

/**
 * Created by fabianisai on 7/20/16.
 */

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password,Integer sesion);

    void onEventMainThread(LoginEvent event);
}
