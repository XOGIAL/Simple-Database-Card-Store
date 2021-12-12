package com.example.tm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class ProductDetailActivity extends AppCompatActivity {
    DBHandler dbhandler;

    Product product = new Product();
    ArrayList<Transaction> transaction_list = new ArrayList<Transaction>();
    int position;
    ArrayList<User> user_list = new ArrayList<User>();
    int userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        dbhandler = new DBHandler(this);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",-1);

        User user = dbhandler.getUser(userid);



        position = intent.getIntExtra("position",-1);

        dbhandler = new DBHandler(this);

        Product product = dbhandler.getProduct(position);




        TextView tv_wallet = findViewById(R.id.tv_wallet_detail);
        String wallet_txt = "Wallet: " + user.getWallet();
        tv_wallet.setText(wallet_txt);


        Type type1 = new TypeToken<Product> () {}.getType();


        TextView tv_product_name = findViewById(R.id.tv_product_name_detail);
        TextView tv_min_player = findViewById(R.id.tv_product_min);
        TextView tv_max_player = findViewById(R.id.tv_product_max);
        TextView tv_price = findViewById(R.id.tv_product_price_detail);

        tv_product_name.setText(product.getName());

        String min = "Minimum player: " + product.getMin_player();
        tv_min_player.setText(min);

        String max = "Maximum player: " + product.getMax_player();
        tv_max_player.setText(max);

        String price = "Price: " + product.getPrice();
        tv_price.setText(price);

        Button btn_buy = findViewById(R.id.btn_buy);

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getWallet() < product.getPrice()){
                    Toast.makeText(getApplicationContext(), "You don't have enough money", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);

                    user.setWallet(user.getWallet() - product.getPrice());

                    Transaction transaction = new Transaction();
                    transaction.setProduct_name(product.getName());
                    transaction.setProduct_price(product.getPrice());
                    transaction.setTransaction_date(Calendar.getInstance().getTime().toString());



                    dbhandler.insertTransaction(transaction,product.getId(),userid);

                    dbhandler.updateWallet(userid,user.getWallet());


                    intent.putExtra("userid",userid);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}