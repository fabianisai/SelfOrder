package com.fabianisai.android.selforder.login.ui;

/**
 * Created by fabianisai on 7/20/16.
 */

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignIn();

    void navigateToMainScreen();
    void navigateToAddUser();
    void loginError(String error);

}
