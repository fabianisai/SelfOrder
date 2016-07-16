package com.fabianisai.android.selforder.orden.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.lib.glide.ImageLoader;
import com.fabianisai.android.selforder.orden.entities.OrdenProducto;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by fabianisai on 7/13/16.
 */

public class OrdenProductoAdapter extends RecyclerView.Adapter<OrdenProductoAdapter.ViewHolder> {

    List<OrdenProducto> ordenProductos;
    private ImageLoader imageLoader;

    public OrdenProductoAdapter(ArrayList<OrdenProducto> productos, ImageLoader imageLoader) {
        this.ordenProductos=productos;
        this.imageLoader=imageLoader;
    }

    public void setItems(List<OrdenProducto> newItems) {
        ordenProductos.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orden_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrdenProducto ordenProducto = ordenProductos.get(position);

        String nombre = ordenProducto.getProductoNombre();
        holder.txtProductoNombre.setText(nombre);

        String estatus = ordenProducto.getEstatus();
        holder.txtProductoEstatus.setText(estatus);

        String cantidad=String.valueOf(ordenProducto.getCantidad());
        holder.txtProductoCantidad.setText(cantidad);

        String precio=String.valueOf(ordenProducto.getPrecio());
        holder.txtProductoPrecio.setText(precio);
     //   String logo=negocio.getLogo();
       // if(logo!=null) {
       //     imageLoader.load(holder.imgAvatar, logo);
      //  }

    }

    @Override
    public int getItemCount() {
        return ordenProductos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgOrdenProducto)
        CircleImageView imgOrdenProducto;
        @Bind(R.id.txtProductoNombre)
        TextView txtProductoNombre;
        @Bind(R.id.txtProductoEstatus)
        TextView txtProductoEstatus;
        @Bind(R.id.txtProductoPrecio)
        TextView txtProductoPrecio;
        @Bind(R.id.txtProductoCantidad)
        TextView txtProductoCantidad;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
