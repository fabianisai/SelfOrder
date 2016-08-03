package com.fabianisai.android.selforder.addUsuario.mvp_clean;

/**
 * Created by fabianisai on 7/28/16.
 */

public class AddUsuarioInteractorImpl implements AddUsuarioInteractor {
    private AddUsuarioRepository addUsuarioRepository;

    public AddUsuarioInteractorImpl() {
        this.addUsuarioRepository = new AddUsuarioRepositoryImpl();
    }
    @Override
    public void createUser(String email, String password, String repassword,Integer SessionType) {
        addUsuarioRepository.createUser(email,password,repassword,SessionType);
    }
}
