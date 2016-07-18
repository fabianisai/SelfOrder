package com.fabianisai.android.selforder.addProductos.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.addProductos.entities.Producto;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fabianisai on 7/17/16.
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {
    List<Producto> productos;
    private OnProductosClickListener clickListener;

    public ProductosAdapter(ArrayList<Producto> productos, OnProductosClickListener clickListener) {
        this.productos = productos;
        this.clickListener = clickListener;
    }

    public void setItems(List<Producto> newItems) {
        productos.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.setClickListener(producto, clickListener);

        String nombre=producto.getNombre();
        holder.txtProductoNombre.setText(nombre);
        String descr = producto.getDescripcion();
        holder.txtProductoDescripcion.setText(descr);
        Double precio=producto.getPrecio();
        holder.txtProductoPrecio.setText(String.valueOf(precio));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.productoImage)
        ImageView productoImage;
        @Bind(R.id.productoNombre)
        TextView txtProductoNombre;
        @Bind(R.id.productoDescripcion)
        TextView txtProductoDescripcion;
        @Bind(R.id.productoPrecio)
        TextView txtProductoPrecio;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            ButterKnife.bind(this,view);
        }

        public void setClickListener(final Producto producto, final OnProductosClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProductosClick(producto);
                }
            });
        }

    }
}
