package com.example.tm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

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
            intent.putExtra("userid",userid);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    User user = new User();
    int userid;


    ArrayList<Transaction> transaction_history = new ArrayList<Transaction>();
    ArrayList<User> user_list = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHandler dbHandler = new DBHandler(this);

        String username;
        String wallet;




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",-1);

        Cursor cursor = dbHandler.getuserdata(userid);
        cursor.moveToFirst();
        username = cursor.getString(cursor.getColumnIndex(DBHelper.field_user_name));
        wallet = cursor.getString(cursor.getColumnIndex(DBHelper.field_user_wallet));







        Type type = new TypeToken<ArrayList<User>>() {}.getType();




        String greeting = "Welcome " + username +"!";
        TextView tv_greeting = findViewById(R.id.tv_greeting);
        tv_greeting.setText(username);

        String wallet_text = "Wallet: Rp." + wallet;
        TextView tv_wallet = findViewById(R.id.tv_wallet);
        tv_wallet.setText(wallet_text);

        transaction_history = dbHandler.getTransaction(userid);

        TextView tv_no_transaction = findViewById(R.id.tv_no_transaction);
        tv_no_transaction.setVisibility(View.GONE);


        if(transaction_history.isEmpty()){
            tv_no_transaction.setVisibility(View.VISIBLE);
        }
        else{
            RecyclerView rv_transaction = findViewById(R.id.rv_transaction);
            TransactionAdapter transaction_adapter = new TransactionAdapter();
            transaction_adapter.setData(transaction_history, this);
            rv_transaction.setAdapter(transaction_adapter);
            rv_transaction.setLayoutManager(new LinearLayoutManager(this));
        }

    }
}