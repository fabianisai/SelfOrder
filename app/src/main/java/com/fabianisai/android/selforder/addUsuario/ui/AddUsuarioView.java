package com.fabianisai.android.selforder.addUsuario.ui;

/**
 * Created by fabianisai on 7/28/16.
 */

public interface AddUsuarioView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignUp();

    void navigateToLogin();
    void addUsuarioError(String error);
}
