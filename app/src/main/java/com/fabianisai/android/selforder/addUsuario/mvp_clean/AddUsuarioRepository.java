package com.fabianisai.android.selforder.addUsuario.mvp_clean;

/**
 * Created by fabianisai on 7/28/16.
 */

public interface AddUsuarioRepository {
    void createUser(String email, String password,String repassword,Integer SessionType);
}
