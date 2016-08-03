package com.fabianisai.android.selforder.login.mvp_clean;

/**
 * Created by fabianisai on 7/20/16.
 */

public interface LoginRepository {
    void signIn(String email, String password);
    void CheckSession();
    void checkAlreadyAuthenticated();
}
