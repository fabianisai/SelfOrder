package com.fabianisai.android.selforder.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.addUsuario.ui.AddUsuarioActivity;
import com.fabianisai.android.selforder.entities.Session;
import com.fabianisai.android.selforder.login.mvp_clean.LoginPresenter;
import com.fabianisai.android.selforder.login.mvp_clean.LoginPresenterImpl;
import com.fabianisai.android.selforder.negocios.ui.NegociosActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

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
    @Bind(R.id.btnLogin)
    LoginButton btnFace;

    private LoginPresenter presenter;
    private CallbackManager callbackManager;

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {

            //solo se ejecuta cuando empieza sesion
            AccessToken accessToken = loginResult.getAccessToken();
            if (accessToken!=null) {
                getvaluesUser(accessToken);
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenterImpl(this);
        presenter.onCreate();


        callbackManager = CallbackManager.Factory.create();

        //verificar si hay session con face
        if (AccessToken.getCurrentAccessToken()!=null) {
            //vamos por los datos de la sesion y disparamos el inicio
            getvaluesUser(AccessToken.getCurrentAccessToken());

        } else {  //ir a revisar si hay sesion de correo
            presenter.checkForAuthenticatedUser();
        }

        btnFace.setReadPermissions(Arrays.asList("public_profile","email"));
        btnFace.registerCallback(callbackManager,facebookCallback);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);  //callback de face para admnistrar la session
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
        progressBar.bringToFront();
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
            presenter.validateLogin(editTxtEmail.getText().toString(), editTxtPassword.getText().toString(), Session.CORREO);
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
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        editTxtPassword.setError(msgError);
    }

    private void setInputs(boolean enabled) {
        editTxtEmail.setEnabled(enabled);
        editTxtPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
        btnFace.setEnabled(enabled);
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



    private void getvaluesUser(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,     //AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {

                            if(object!=null) {
                                Log.e("oko", object.getString("id"));
                                if (object.getString("email")==null || object.getString("email").equals("")){
                                    SelfOrderApp.getInstance().logout();
                                    Toast.makeText(getApplication(), R.string.login_error_message_signin_facebook_noemail, Toast.LENGTH_SHORT).show();
                                } else{
                                    presenter.validateLogin(object.getString("email"), SelfOrderApp.getInstance().getResources().getString(R.string.login_data_pass_face), Session.FACEBOOK);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }



                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();


    }

}
