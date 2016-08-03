package com.fabianisai.android.selforder.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.addUsuario.ui.AddUsuarioActivity;
import com.fabianisai.android.selforder.login.mvp_clean.LoginPresenter;
import com.fabianisai.android.selforder.login.mvp_clean.LoginPresenterImpl;
import com.fabianisai.android.selforder.negocios.ui.NegociosActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.editTxtEmail)
    EditText editTxtEmail;
    @Bind(R.id.editTxtPassword)
    EditText editTxtPassword;
    @Bind(R.id.btnSignin)
    Button btnSignin;
    @Bind(R.id.btnSignup)
    Button btnSignup;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout layoutMainContainer;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter=new LoginPresenterImpl(this);
        presenter.onCreate();
        presenter.checkForAuthenticatedUser();


    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignin)
    @Override
    public void handleSignIn() {
        if (emailValid(editTxtEmail.getText().toString())) {
            presenter.validateLogin(editTxtEmail.getText().toString(), editTxtPassword.getText().toString());
        }
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(this, NegociosActivity.class);

        //para que el usuario no pueda darle back
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btnSignup)
    @Override
    public void navigateToAddUser() {
        Intent intent = new Intent(this, AddUsuarioActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginError(String error) {
        editTxtPassword.setText("");
        String msgError=String.format(getString(R.string.login_error_message_signin),error);
        editTxtPassword.setError(msgError);
    }

    private void setInputs(boolean enabled){
        editTxtEmail.setEnabled(enabled);
        editTxtPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
    }

    private boolean emailValid(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            editTxtEmail.setError(String.format(getString(R.string.login_error_message_email_novalido)));
            return false;
        } else {
            editTxtEmail.setError(null);
        }

        return true;
    }
}
