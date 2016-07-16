package com.fabianisai.android.selforder.orden.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.lib.glide.GlideImageLoader;
import com.fabianisai.android.selforder.lib.glide.ImageLoader;
import com.fabianisai.android.selforder.negocios.ui.NegociosActivity;
import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.entities.OrdenProducto;
import com.fabianisai.android.selforder.orden.mvp_clean.OrdenPresenter;
import com.fabianisai.android.selforder.orden.mvp_clean.OrdenPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;

public class OrdeneActivity extends AppCompatActivity implements OrdenView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.txtOrden)
    TextView txtOrden;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.txtTotal)
    TextView txtTotal;
    @Bind(R.id.recyclerViewOrdenProductos)
    RecyclerView recyclerViewOrdenProductos;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.container)
    CoordinatorLayout container;
    
    private OrdenProductoAdapter adapter;
    private OrdenPresenter presenter;
    private Orden orden;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        presenter=new OrdenPresenterImpl(this);
        setSupportActionBar(toolbar);
        setupNameNegocio();
        presenter.verificaOrden();
      //  SelfOrderApp.SHARE
    }

    private void setupNameNegocio() {
        sharedPreferences=SelfOrderApp.getSharedPreferences();
        String nombreNegocio=sharedPreferences.getString(SelfOrderApp.getNegocioDescr(),null);

        if(nombreNegocio!=null){
            setTitle(nombreNegocio);
        }
    }

    private void setupRecyclerView() {
        recyclerViewOrdenProductos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrdenProductos.setAdapter(adapter);
    }

    private void setupAdapter() {
        ImageLoader loader=new GlideImageLoader(this.getApplicationContext());
        adapter= new OrdenProductoAdapter(new ArrayList<OrdenProducto>(),loader);
    }

    @Override
    public void showProductos() {
        recyclerViewOrdenProductos.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProductos() {
        recyclerViewOrdenProductos.setVisibility(View.GONE);
    }

    @Override
    public void showOrden() {
        txtOrden.setVisibility(View.VISIBLE);
        txtStatus.setVisibility(View.VISIBLE);
        txtTotal.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOrden() {
        txtOrden.setVisibility(View.GONE);
        txtStatus.setVisibility(View.GONE);
        txtTotal.setVisibility(View.GONE);
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
    public void onOrdenError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onOrdenCreate(Orden orden) {
        Snackbar.make(container,R.string.orden_notice_create, Snackbar.LENGTH_SHORT).show();
        txtOrden.setText(String.valueOf(orden.getOrdenId()));
        txtStatus.setText(orden.getEstatusDescr());
        txtTotal.setText(String.valueOf(orden.getTotal()));
    }

    @Override
    public void onOrdenEnvida() {
        Snackbar.make(container,R.string.orden_notice_send, Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void setProductos(Orden orden) {
        txtOrden.setText(String.valueOf(orden.getOrdenId()));
        txtStatus.setText(orden.getEstatusDescr());
        txtTotal.setText(String.valueOf(orden.getTotal()));

        adapter.setItems(orden.getProductoList());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_orden, menu);
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
        sharedPreferences=SelfOrderApp.getSharedPreferences();
        sharedPreferences.edit().clear().commit();
     //   Toast.makeText(getApplication() ,"Salio", LENGTH_SHORT).show();
        Intent intent = new Intent(this, NegociosActivity.class);

        //para que el usuario no pueda darle back
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}