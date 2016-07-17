package com.fabianisai.android.selforder.menus.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabianisai.android.selforder.R;
import com.fabianisai.android.selforder.menus.entities.Menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fabianisai on 7/16/16.
 */

public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.ViewHolder> {
    List<Menu> menus;
    private OnMenusClickListener clickListener;

    public MenusAdapter(ArrayList<Menu> menus, OnMenusClickListener clickListener) {
        this.menus=menus;
        this.clickListener=clickListener;
    }

    public void setItems(List<Menu> newItems) {
        menus.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Menu menu = menus.get(position);
        holder.setClickListener(menu, clickListener);

        String nombre = menu.getDescripcion();
        holder.txtNombreMenu.setText(nombre);


    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNombreMenu)
        TextView txtNombreMenu;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            ButterKnife.bind(this,view);
        }

        public void setClickListener(final Menu menu, final OnMenusClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMenusClick(menu);
                }
            });
        }
    }
}
