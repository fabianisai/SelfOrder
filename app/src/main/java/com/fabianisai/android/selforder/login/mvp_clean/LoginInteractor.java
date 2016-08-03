package com.fabianisai.android.selforder.login.mvp_clean;

/**
 * Created by fabianisai on 7/20/16.
 */

public interface LoginInteractor {
    void checkAlreadyAuthenticated();
    void checkSession();
    void doSignIn(String email, String password);
}
