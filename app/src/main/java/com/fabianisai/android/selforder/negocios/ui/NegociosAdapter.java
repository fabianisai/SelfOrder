package com.fabianisai.android.selforder.negocios.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabianisai.android.selforder.lib.glide.ImageLoader;
import com.fabianisai.android.selforder.negocios.entities.Negocio;
import com.fabianisai.android.selforder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fabianisai on 7/10/16.
 */

public class NegociosAdapter extends RecyclerView.Adapter<NegociosAdapter.ViewHolder> {

    List<Negocio> negocios;
    private OnNegocioClickListener clickListener;
    private ImageLoader imageLoader;

    public NegociosAdapter(ArrayList<Negocio> negocios, OnNegocioClickListener clickListener,ImageLoader imageLoader) {
        this.negocios=negocios;
        this.clickListener=clickListener;
        this.imageLoader=imageLoader;
    }

    public void setItems(List<Negocio> newItems) {
        negocios.addAll(newItems);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_negocio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Negocio negocio = negocios.get(position);
        holder.setClickListener(negocio, clickListener);

        String nombre = negocio.getNombre();
        holder.txtNegocio.setText(nombre);
        String logo=negocio.getLogo();
        if(logo!=null) {
            imageLoader.load(holder.imgAvatar, logo);
        }

    }

    @Override
    public int getItemCount() {
        return negocios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtNegocio)
        TextView txtNegocio;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            ButterKnife.bind(this,view);
        }

        public void setClickListener(final Negocio negocio, final OnNegocioClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNegocioClick(negocio);
                }
            });
        }
    }
}
