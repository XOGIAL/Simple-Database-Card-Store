package com.example.tm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity implements ProductAdapter.OnProductListener {
int userid;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menu_home:
                intent = new Intent(this, HomeActivity.class);
                break;
            case R.id.menu_store:
                intent = new Intent(this, StoreActivity.class);
                break;
            case R.id.menu_profile:
                intent = new Intent(this, ProfileActivity.class);
                break;
            case R.id.menu_logout:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        if(intent != null){
            intent.putExtra("userid", userid);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    String product_json;
    ArrayList<Product> product_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        DBHandler dbHandler = new DBHandler(this);

        userid = intent.getIntExtra("userid",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);



        product_list = dbHandler.getAllProducts();

       if(product_list == null){
            Product product1 = new Product();
            product1.setId(1);
            product1.setName("Exploding Kitten");
            product1.setMin_player(2);
            product1.setMax_player(5);
            product1.setPrice(250000);
            product1.setLongitude(106.265139);
            product1.setLatitude(-6.912035);
//        product_list.add(product1);
            dbHandler.insertProduct(product1);


            Product product2 = new Product();
            product2.setId(2);
            product2.setName("Card Against Humanity");
            product2.setMin_player(2);
            product2.setMax_player(4);
            product2.setPrice(182500);
            product2.setLongitude(108.126810);
            product2.setLatitude(-7.586037);
//        product_list.add(product2);
            dbHandler.insertProduct(product2);
           product_list = dbHandler.getAllProducts();
        }







        RecyclerView rv_product = findViewById(R.id.rv_product);
        ProductAdapter product_adapter = new ProductAdapter();
        product_adapter.setData(product_list, this);
        rv_product.setAdapter(product_adapter);
        rv_product.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnProductClick(int position) {
        Intent intent = getIntent();

        userid = intent.getIntExtra("userid",-1);




        Intent intent1 = new Intent(this, ProductDetailActivity.class);

        intent1.putExtra("position", product_list.get(position).getId());
        intent1.putExtra("userid",userid);



        startActivity(intent1);
    }
}