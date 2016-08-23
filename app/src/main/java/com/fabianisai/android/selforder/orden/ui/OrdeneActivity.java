package com.fabianisai.android.selforder.orden.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.SelfOrderApp;
import com.fabianisai.android.selforder.lib.glide.GlideImageLoader;
import com.fabianisai.android.selforder.lib.glide.ImageLoader;
import com.fabianisai.android.selforder.menus.ui.MenusActivity;
import com.fabianisai.android.selforder.orden.entities.Orden;
import com.fabianisai.android.selforder.orden.entities.OrdenProducto;
import com.fabianisai.android.selforder.orden.mvp_clean.OrdenPresenter;
import com.fabianisai.android.selforder.orden.mvp_clean.OrdenPresenterImpl;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.fab)
    FloatingActionButton fab;

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
        presenter = new OrdenPresenterImpl(this);
        setSupportActionBar(toolbar);
        setupNameNegocio();
        adapter.deleteAll();
        presenter.verificaOrden();
        //  SelfOrderApp.SHARE
    }

    @Override
    protected void onResume() {
     //   adapter.deleteAll();
     //   presenter.verificaOrden();
        super.onResume();
    }

    private void setupNameNegocio() {
        sharedPreferences = SelfOrderApp.getSharedPreferences();
        String nombreNegocio = sharedPreferences.getString(SelfOrderApp.getNegocioDescr(), null);

        if (nombreNegocio != null) {
            setTitle(nombreNegocio);
        }
    }

    private void setupRecyclerView() {
        recyclerViewOrdenProductos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrdenProductos.setAdapter(adapter);
    }

    private void setupAdapter() {
        ImageLoader loader = new GlideImageLoader(this.getApplicationContext());
        adapter = new OrdenProductoAdapter(new ArrayList<OrdenProducto>(), loader);
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
        Snackbar.make(container, R.string.orden_notice_create, Snackbar.LENGTH_SHORT).show();
        txtOrden.setText(String.valueOf(orden.getOrdenId()));
        txtStatus.setText(orden.getEstatusDescr());
        txtTotal.setText(String.valueOf(orden.getTotal()));
        this.orden=orden;
    }

    @Override
    public void onOrdenEnvida() {
        Snackbar.make(container, R.string.orden_notice_send, Snackbar.LENGTH_SHORT).show();
        refresh();
    }


    @Override
    public void setProductos(Orden orden) {
        txtOrden.setText(String.valueOf(orden.getOrdenId()));
        txtStatus.setText(orden.getEstatusDescr());
        txtTotal.setText(String.valueOf(orden.getTotal()));
        this.orden=orden;
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
        }else if(id==R.id.action_refresh){
            refresh();
            return true;
        } else if(id==R.id.action_send){
            enviaOrden();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh(){
        presenter.verificaOrden();
    }

    private void enviaOrden(){
       // Toast.makeText(getApplication() ,"envia..."+orden.getEstatusDescr(),Toast.LENGTH_SHORT).show();


        if (!orden.getEstatusDescr().equals("NUEVA")){
            Snackbar.make(container, R.string.orden_valida_status, Snackbar.LENGTH_SHORT).show();
        }else{

            if(orden.getProductoList().size()<=0){
                Snackbar.make(container, R.string.orden_valida_productosEmpy, Snackbar.LENGTH_SHORT).show();
            }else {
                orden.setEstatusDescr("POR ATENDER");
                presenter.enviaOrden(orden);
            }
        }


    }

    private void logout() {

        //   Toast.makeText(getApplication() ,"Salio", LENGTH_SHORT).show();

        //validar orden activa

        SelfOrderApp.getInstance().logout();

    }

    @OnClick(R.id.fab)
    public void menuProductos(){

        if (orden.getEstatusDescr().equals("CANCELADA")||orden.getEstatusDescr().equals("CERRADA")){
            Snackbar.make(container, R.string.orden_valida_status_addproductos, Snackbar.LENGTH_SHORT).show();
        }else {

            Intent intent = new Intent(this, MenusActivity.class);
            startActivity(intent);
        }
    }

}
