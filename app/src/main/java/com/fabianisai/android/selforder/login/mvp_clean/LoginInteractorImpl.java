package com.fabianisai.android.selforder.login.mvp_clean;

/**
 * Created by fabianisai on 7/20/16.
 */

public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        this.loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkAlreadyAuthenticated() {

    }

    @Override
    public void checkSession() {
        loginRepository.CheckSession();
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email,password);
    }
}
