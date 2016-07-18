package com.fabianisai.android.selforder.addProductos.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.addProductos.entities.Producto;
import com.fabianisai.android.selforder.addProductos.mvp_clean.AddProductosPresenter;
import com.fabianisai.android.selforder.addProductos.mvp_clean.AddProductosPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductosFragment extends DialogFragment implements AddProductosView,OnProductosClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listProducto)
    RecyclerView RecyclerViewProducto;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
  //  @Bind(R.id.fragment_productos)
  //  FrameLayout fragmentProductos;

    private ProductosAdapter adapter;
    private AddProductosPresenter presenter;
    private Integer menuId;
    public AddProductosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setHasOptionsMenu(true);

     //   ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

      //  toolbar.setTitle("ok");
       // if (actionBar != null) {
      //      actionBar.setDisplayHomeAsUpEnabled(true);
      //      actionBar.setHomeAsUpIndicator(R.mipmap.ic_clear_black);
      //  }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_productos, container, false);
        ButterKnife.bind(this, view);

       // setCancelable(true);
        String menuNombre=getArguments().getString("menuName");
        menuId=getArguments().getInt("menuId");

        toolbar.setTitle(menuNombre);
        setupAdapter();
        RecyclerViewProducto.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewProducto.setAdapter(adapter);

        presenter= new AddProductosPresenterImpl(this);
        presenter.getProductos(menuId);

        return view;
    }

    private void setupAdapter() {
        // ImageLoader loader=new GlideImageLoader(this.getApplicationContext());
        adapter= new ProductosAdapter(new ArrayList<Producto>(),this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showList() {
        RecyclerViewProducto.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        RecyclerViewProducto.setVisibility(View.GONE);
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
    public void onProductosError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProductos(List<Producto> items) {
        adapter.setItems(items);
    }

    @Override
    public void onAddProducto(String nameProducto) {
        Toast.makeText(getActivity(), "Se Agrego "+nameProducto, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductosClick(Producto producto) {
        presenter.addProducto(producto);
        //Toast.makeText(getActivity(), producto.getNombre(), Toast.LENGTH_SHORT).show();
    }
}
