package com.fabianisai.android.selforder.menus.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.menus.entities.Menu;
import com.fabianisai.android.selforder.menus.mvp_clean.MenusPresenter;
import com.fabianisai.android.selforder.menus.mvp_clean.MenusPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MenusActivity extends AppCompatActivity implements MenusView,OnMenusClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.recyclerViewMenus)
    RecyclerView recyclerViewMenus;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.container)
    CoordinatorLayout container;

    private MenusPresenter presenter;
    private MenusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        presenter= new MenusPresenterImpl(this);
      //  presenter.onCreate();
        presenter.getMenus();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  sharedPreferences=SelfOrderApp.getSharedPreferences();
      //  Integer negocioId=sharedPreferences.getInt(SelfOrderApp.getNegocioKey(),-1);

      //  if(negocioId!=-1&&negocioId!=null){
       //     navigationToOrden();
      //  }
    }

    private void setupAdapter() {
       // ImageLoader loader=new GlideImageLoader(this.getApplicationContext());
        adapter= new MenusAdapter(new ArrayList<Menu>(),this);
    }

    private void setupRecyclerView() {
        recyclerViewMenus.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMenus.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void showList() {
        recyclerViewMenus.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerViewMenus.setVisibility(View.GONE);
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
    public void onMenusError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setMenus(List<Menu> items) {
        adapter.setItems(items);
    }

    @Override
    public void onMenusClick(Menu menu) {

    }
}
