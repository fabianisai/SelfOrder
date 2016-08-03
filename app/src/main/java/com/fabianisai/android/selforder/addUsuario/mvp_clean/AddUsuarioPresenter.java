package com.fabianisai.android.selforder.addUsuario.mvp_clean;

import com.fabianisai.android.selforder.addUsuario.events.AddUsuarioEvent;

/**
 * Created by fabianisai on 7/28/16.
 */

public interface AddUsuarioPresenter {
    void onCreate();
    void onDestroy();
    void creaUser(String email, String password,String repassword,Integer sessionType);

    void onEventMainThread(AddUsuarioEvent event);
}
