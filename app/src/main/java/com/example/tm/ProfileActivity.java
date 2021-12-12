package com.example.tm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    int userid;
    DBHandler dbHandler;
    User user;
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

    ArrayList<User> user_list = new ArrayList<User>();


    RadioButton selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbHandler = new DBHandler(this);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid",-1);

        user = new User();

        user = dbHandler.getUser(userid);


        TextView tv_username = findViewById(R.id.tv_username);
        TextView tv_phone = findViewById(R.id.tv_phone);
        TextView tv_gender = findViewById(R.id.tv_gender);
        TextView tv_dob = findViewById(R.id.tv_dob);
        TextView tv_wallet = findViewById(R.id.tv_wallet_profile);

        SharedPreferences shared_preferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_preferences.edit();





        String username = "Name: " + user.getUsername();
        String phone = "Phone number: " + user.getPhone_number();
        String gender = "Gender: " + user.getGender();
        String dob = "Date of Birth: " + user.getDob();
        String wallet = "Wallet: Rp." + user.getWallet();

        tv_username.setText(username);
        tv_phone.setText(phone);
        tv_gender.setText(gender);
        tv_dob.setText(dob);
        tv_wallet.setText(wallet);

        RadioGroup rg_topUp = findViewById(R.id.rg_topUp);

        rg_topUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selected = radioGroup.findViewById(i);
            }
        });

        Button btn_confirm = findViewById(R.id.btn_confirm);
        EditText et_password = findViewById(R.id.et_password_profile);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString();

                if(selected == null){
                    Toast.makeText(getApplicationContext(), "Please choose how much to top up", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input your password", Toast.LENGTH_SHORT).show();
                }
                else if(!dbHandler.passwordChecker(userid,password)){
                    Toast.makeText(getApplicationContext(), "The password is incorrect", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);

                    String selected1 = selected.getText().toString();

                    if(selected1.equals("Rp.250.000")){
                        dbHandler.updateWallet(userid, user.getWallet()+250000);
                    }
                    else if(selected1.equals("Rp.500.000")){
                        dbHandler.updateWallet(userid, user.getWallet()+500000);
                    }
                    else {
                        dbHandler.updateWallet(userid, user.getWallet()+1000000);
                    }

                    intent.putExtra("userid",userid);



                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}