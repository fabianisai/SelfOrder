package com.fabianisai.android.selforder.negocios.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.lib.glide.GlideImageLoader;
import com.fabianisai.android.selforder.lib.glide.ImageLoader;
import com.fabianisai.android.selforder.negocios.entities.Negocio;
import com.fabianisai.android.selforder.negocios.mvp_clean.NegociosPresenter;
import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.negocios.mvp_clean.NegociosPresenterImpl;
import com.fabianisai.android.selforder.orden.ui.OrdeneActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.widget.Toast.*;

public class NegociosActivity extends AppCompatActivity implements NegociosView, OnNegocioClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewNegocios)
    RecyclerView recyclerViewNegocios;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.container)
    CoordinatorLayout container;

    private NegociosPresenterImpl presenter;
    private NegociosAdapter adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocios);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        presenter= new NegociosPresenterImpl(this);
        presenter.onCreate();
        presenter.getNegocios();
        setSupportActionBar(toolbar);

        sharedPreferences=SelfOrderApp.getSharedPreferences();
        Integer negocioId=sharedPreferences.getInt(SelfOrderApp.getNegocioKey(),-1);

        if(negocioId!=-1&&negocioId!=null){
            navigationToOrden();
        }
    }

    private void setupAdapter() {
        ImageLoader loader=new GlideImageLoader(this.getApplicationContext());
        adapter= new NegociosAdapter(new ArrayList<Negocio>(),this,loader);
    }

    private void setupRecyclerView() {
        recyclerViewNegocios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNegocios.setAdapter(adapter);
    }



    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showList() {
        recyclerViewNegocios.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerViewNegocios.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNegociosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setNegocios(List<Negocio> items) {
        adapter.setItems(items);
    }

    @Override
    public void onNegocioClick(Negocio negocio) {
        sharedPreferences=SelfOrderApp.getSharedPreferences();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(SelfOrderApp.getNegocioKey(),negocio.getId());
        editor.putString(SelfOrderApp.getNegocioDescr(),negocio.getNombre());
        editor.commit();

        navigationToOrden();

       // Toast.makeText(getApplication() ,String.valueOf(negocio.getId())+" "+negocio.getNombre(), LENGTH_SHORT).show();
       // Log.e("oko","si");
    }

    private void navigationToOrden(){
        Intent intent = new Intent(this, OrdeneActivity.class);

        //para que el usuario no pueda darle back
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * opciones de menu**************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_negocios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        Toast.makeText(getApplication() ,"Salio", LENGTH_SHORT).show();
      /*  Intent intent = new Intent(this, LoginActivity.class);

        //para que el usuario no pueda darle back
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        */
    }
}
