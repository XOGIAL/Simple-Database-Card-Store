package com.example.tm;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    ArrayList<Transaction> list = new ArrayList<Transaction>();
    User user = new User();
    Context context;
    ArrayList<User> user_list = new ArrayList<User>();
    DBHandler dbHandler;



    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        dbHandler = new DBHandler(this.context);
        View view = LayoutInflater.from(ctx).inflate(R.layout.transaction_history, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {


        String product_name = "Name: " + list.get(position).getProduct_name();
        String transaction_date = "Date: " + list.get(position).getTransaction_date();
        String product_price = "Price: " + list.get(position).getProduct_price();
        holder.tv_name.setText(product_name);
        holder.tv_date.setText(transaction_date);
        holder.tv_price.setText(product_price);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbHandler.deleteTransaction(list.get(position).getId());

                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(ArrayList<Transaction> list, Context context){
        this.list = list;
        this.context = context;
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_date, tv_price;
        Button btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_delete = itemView.findViewById(R.id.btn_delete);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
