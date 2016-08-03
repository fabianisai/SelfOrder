package com.fabianisai.android.selforder.addUsuario.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.addUsuario.mvp_clean.AddUsuarioPresenter;
import com.fabianisai.android.selforder.addUsuario.mvp_clean.AddUsuarioPresenterImpl;
import com.fabianisai.android.selforder.entities.Session;
import com.fabianisai.android.selforder.login.ui.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUsuarioActivity extends AppCompatActivity implements AddUsuarioView {

    @Bind(R.id.editTxtEmail)
    EditText editTxtEmail;
    @Bind(R.id.editTxtPassword)
    EditText editTxtPassword;
    @Bind(R.id.editTxtRePassword)
    EditText editTxtRePassword;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout layoutMainContainer;

    private AddUsuarioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usuario);
        ButterKnife.bind(this);

        presenter=new AddUsuarioPresenterImpl(this);
        presenter.onCreate();

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

    @OnClick(R.id.btnRegister)
    @Override
    public void handleSignUp() {
        if (emailValid(editTxtEmail.getText().toString())) {
            presenter.creaUser(editTxtEmail.getText().toString(), editTxtPassword.getText().toString(), editTxtRePassword.getText().toString(), Session.CORREO);
        }
    }

    @Override
    public void navigateToLogin() {
        Toast.makeText(getApplication() ,R.string.addusuario_message_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);

        //para que el usuario no pueda darle back
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void addUsuarioError(String error) {
        editTxtPassword.setText("");
        editTxtRePassword.setText("");
        String msgError=String.format(getString(R.string.addusuario_error_message_signup),error);
        editTxtPassword.setError(msgError);
    }

    private void setInputs(boolean enabled){
        editTxtEmail.setEnabled(enabled);
        editTxtPassword.setEnabled(enabled);
        editTxtRePassword.setEnabled(enabled);
        btnRegister.setEnabled(enabled);
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
