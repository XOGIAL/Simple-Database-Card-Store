package com.example.tm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter <ProductAdapter.ViewHolder>{

    ArrayList<Product> list = new ArrayList<Product>();
    OnProductListener onProductListener1;

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_list, parent, false);
        return new ViewHolder(view, onProductListener1);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        String name = "Name: " + list.get(position).getName();
        String min_player = "Min Player: " + list.get(position).getMin_player();
        String max_player = "Max Player: " + list.get(position).getMax_player();
        String price = "Price: " + list.get(position).getPrice();

        holder.tv_product_name.setText(name);
        holder.tv_min_player.setText(min_player);
        holder.tv_max_player.setText(max_player);
        holder.tv_product_price.setText(price);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(ArrayList<Product> list, OnProductListener onProductListener){
        this.list = list;
        this.onProductListener1 = onProductListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_product_name, tv_min_player, tv_max_player, tv_product_price;
        OnProductListener onProductListener;

        public ViewHolder(@NonNull View itemView, OnProductListener onProductListener) {
            super(itemView);

            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_min_player = itemView.findViewById(R.id.tv_min_player);
            tv_max_player = itemView.findViewById(R.id.tv_max_player);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            this.onProductListener = onProductListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onProductListener.OnProductClick(getAdapterPosition());

        }
    }

    public interface OnProductListener{
        void OnProductClick(int position);
    }
}
